/*
 * TODO put header
 */
package nl.laaksoft.obd.reader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import nl.laaksoft.obd.reader.R;

/******************************************************************************/
/**
 * The main activity.
 */
public class MainActivity extends Activity
{
    private static final String TAG = "MainActivity";
    private BluetoothSocket m_Socket = null;
    private TextView tvMain1, tvMain2;
    private EditText edtPrimary, edtSecondary;
    private TextView tvSent, tvReceived;
    private Button btnQuery;

    /**************************************************************************/
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        tvMain1 = (TextView) findViewById(R.id.tvMain1);
        tvMain2 = (TextView) findViewById(R.id.tvMain2);
        edtPrimary = (EditText) findViewById(R.id.edtPrimary);
        edtSecondary = (EditText) findViewById(R.id.edtSecondary);
        tvSent = (TextView) findViewById(R.id.lblSent);
        tvReceived = (TextView) findViewById(R.id.tvReceived);

        btnQuery = (Button) findViewById(R.id.btnQuery);
        btnQuery.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                int primary = Integer.parseInt(edtPrimary.getText().toString());
                int secondary = Integer.parseInt(edtSecondary.getText().toString());

                String cmd;

                cmd = Integer.toHexString(0x100 | primary).substring(1) + " "
                        + Integer.toHexString(0x100 | secondary).substring(1);

                try
                {
                    sendObdCommand(cmd);
                }
                catch (IOException e)
                {
                    Log.e(TAG, "No connection: " + e.getMessage());
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG)
                            .show();
                    tvMain1.setText(e.getMessage());
                }
            }
        });

        try
        {
            startObdConnection();
        }
        catch (Exception e)
        {
            Log.e(TAG, "No connection: " + e.getMessage());
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            tvMain1.setText(e.getMessage());
            stopObdConnection();
        }
    }

    /**************************************************************************/
    public void startObdConnection() throws Exception
    {
        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        final UUID SPP = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled())
        {
            throw new Exception("Bluetooth disabled");
        }

        String bd_address = "";

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0)
        {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices)
                if (device.getName().equals("OBDII"))
                    bd_address = device.getAddress();
        }

        if (bd_address.equals(""))
        {
            throw new Exception("Not paired with OBDII");
        }

        /* The OBD is known, see if it's nearby */
        BluetoothDevice dev = mBluetoothAdapter.getRemoteDevice(bd_address);
        try
        {
            m_Socket = dev.createRfcommSocketToServiceRecord(SPP);
            m_Socket.connect();
        }
        catch (IOException e)
        {
            throw new Exception("OBDII not in range");
        }

        String ans;

        ans = sendObdCommand("AT Z"); // reset
        ans = sendObdCommand("AT E0"); // echo off
        ans = sendObdCommand("AT L0"); // linefeed off
        ans = sendObdCommand("AT ST 62"); // timeout in 4ms quants
        ans = sendObdCommand("AT SP 0"); // select protocol automatic
        ans = sendObdCommand("01 05"); // dummy data to do auto-discover

        ans = sendObdCommand("AT RV"); // get voltage
        tvMain1.setText("Voltage " + ans);

        ans = sendObdCommand("01 05"); // get cooling temperature
        tvMain2.setText("Temperature " + ans);
    }

    /**************************************************************************/
    public void stopObdConnection()
    {
        if (m_Socket != null)
        {
            try
            {
                m_Socket.close();
            }
            catch (IOException e)
            {
                // ignore
            }
        }

    }

    /**************************************************************************/
    private String sendObdCommand(String cmd) throws IOException
    {
        tvSent.setText(cmd);
        tvMain1.setText("Sending: " + cmd);

        InputStream stdin = m_Socket.getInputStream();
        OutputStream stdout = m_Socket.getOutputStream();

        String cmdcr = cmd + '\r';
        stdout.write(cmdcr.getBytes());
        stdout.flush();

        StringBuilder res = new StringBuilder();

        // read until next prompt '>' arrives
        while (true)
        {
            tvMain1.setText("Reading char");
            byte b = (byte) (stdin.read());
            if (b == '>')
                break;

            tvMain1.setText("Read char " + b);
            res.append(b);
        }

        String ans = res.toString();

        tvMain1.setText("Read answer " + ans);

        tvReceived.setText(ans);

        return ans;
    }
}