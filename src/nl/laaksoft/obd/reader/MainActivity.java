/*
 * TODO put header
 */
package nl.laaksoft.obd.reader;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import nl.laaksoft.obd.reader.R;

/**
 * The main activity.
 */
public class MainActivity extends Activity
{
    private static final String TAG = "MainActivity";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.e(TAG, "hai");

        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        try
        {
            startObdConnection();
        }
        catch (Exception e)
        {
            Log.e(TAG, "There was an error while establishing connection. -> " + e.getMessage());
            Toast.makeText(getApplicationContext(), "No Bluetooth", Toast.LENGTH_LONG).show();
        }
    }

    public void startObdConnection() throws Exception
    {
        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        final UUID SPP = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

        if (mBluetoothAdapter == null || mBluetoothAdapter.isEnabled())
        {
            throw new Exception("No bluetooth adapter enabled");
        }

        BluetoothDevice dev = null;
        BluetoothSocket sock = null;

        /* Let's roll */
        dev = mBluetoothAdapter.getRemoteDevice("OBD2");
        sock = dev.createRfcommSocketToServiceRecord(SPP);
        sock.connect();

        InputStream stdin = sock.getInputStream();
        OutputStream stdout = sock.getOutputStream();

        stdout.write("AT Z\r".getBytes());
        stdout.flush();

        StringBuilder res = new StringBuilder();

        // read until '>' arrives
        while (true)
        {
            byte b = (byte) (stdin.read());
            if (b == '>')
                break;

            res.append(b);
        }
    }
}