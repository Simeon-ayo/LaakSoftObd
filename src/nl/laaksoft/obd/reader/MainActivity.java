/*
 * TODO put header
 */
package nl.laaksoft.obd.reader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import nl.laaksoft.obd.reader.R;

/**
 * The main activity.
 */
public class MainActivity extends Activity
{
    private static final String TAG = "MainActivity";
    private BluetoothSocket m_Sock;
    private TextView tvMain;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        tvMain = (TextView) findViewById(R.id.tvMain);
        tvMain.setText("Started");

        try
        {
            startObdConnection();
        }
        catch (Exception e)
        {
            Log.e(TAG, "There was an error while establishing connection. -> " + e.getMessage());
            Toast.makeText(getApplicationContext(), "No Bluetooth", Toast.LENGTH_LONG).show();
            tvMain.setText("Unavailable");
        }
    }

    public void startObdConnection() throws Exception
    {
        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        final UUID SPP = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

        tvMain.setText("StartObdConnection");

        if (mBluetoothAdapter == null || mBluetoothAdapter.isEnabled())
        {
            throw new Exception("No bluetooth adapter enabled");
        }

        BluetoothDevice dev = null;

        /* Let's roll */
        tvMain.setText("Getting Adapter");
        dev = mBluetoothAdapter.getRemoteDevice("OBD2");

        tvMain.setText("Getting Socket");
        m_Sock = dev.createRfcommSocketToServiceRecord(SPP);

        tvMain.setText("Connecting");
        m_Sock.connect();

        sendObdCommand("AT Z"); // reset
        sendObdCommand("AT E0"); // echo off
        sendObdCommand("AT L0"); // linefeed off
        sendObdCommand("AT S0 62"); // timeout in 4ms quants
        sendObdCommand("AT SP 0"); // select protocol automatic

        String ans = sendObdCommand("01 46"); // get temperature
        tvMain.setText("Temperature " + ans);
    }

    private String sendObdCommand(String cmd) throws IOException
    {
        tvMain.setText("Sending: " + cmd);

        InputStream stdin = m_Sock.getInputStream();
        OutputStream stdout = m_Sock.getOutputStream();

        String cmdcr = cmd + '\r';
        stdout.write(cmdcr.getBytes());
        stdout.flush();

        StringBuilder res = new StringBuilder();

        // read until '>' arrives
        while (true)
        {
            tvMain.setText("Reading char");
            byte b = (byte) (stdin.read());
            if (b == '>')
                break;

            tvMain.setText("Read char " + b);
            res.append(b);
        }

        tvMain.setText("Read answer " + res.toString());

        return res.toString();
    }
}