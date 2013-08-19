package nl.laaksoft.obd.reader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class ObdConnection
{
    private static final String TAG = "ObdConnection";
    private BluetoothSocket m_Socket = null;
    private boolean m_Connected = false;

    /**************************************************************************/
    public void startObdConnection() throws Exception
    {
        Log.e(TAG, "Start ODB connecting");

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
        }
        catch (IOException e)
        {
            throw new Exception("Could not create socket");
        }

        try
        {
            Log.e(TAG, "Connecting");
            m_Socket.connect();
            Log.e(TAG, "Connecting Done");
        }
        catch (IOException e)
        {
            throw new Exception("OBDII not in range");
        }

        sendObdCommand("AT Z"); // reset
        sendObdCommand("AT E0"); // echo off
        sendObdCommand("AT L0"); // linefeed off
        sendObdCommand("AT ST 62"); // timeout in 4ms quants
        sendObdCommand("AT SP 0"); // select protocol automatic
        sendObdCommand("01 05"); // dummy data to do auto-discover

        sendObdCommand("AT RV"); // get voltage
        sendObdCommand("01 05"); // get cooling temperature

        m_Connected = true;
    }

    /**************************************************************************/
    public void stopObdConnection()
    {
        m_Connected = false;
        if (m_Socket != null)
        {
            try
            {
                m_Socket.close();
                m_Socket = null;
            }
            catch (IOException e)
            {
                // ignore
            }
        }

    }

    /**************************************************************************/
    public void updateData(ObdData data)
    {
        data.m_VehicleSpeed = getVehicleSpeed();
        data.m_EngineRpm = getEngineRpm();
        //data.m_EngineLoad = getEngineLoad();

        // data.m_CoolingTemperature = getCoolingTemperature();
        // data.m_IntakePressure = getIntakePressure();
        // data.m_IntakeTemperature = getIntakeTemperature();
        // data.m_MafRate = getMafRate();
        // data.m_RailPressure = getRailPressure();
    }

    /**************************************************************************/
    private String sendObdCommand(String cmd) throws IOException
    {
        InputStream stdin = m_Socket.getInputStream();
        OutputStream stdout = m_Socket.getOutputStream();

        String cmdcr = cmd + '\r';
        stdout.write(cmdcr.getBytes());
        stdout.flush();

        String ans = "";

        // read until next prompt '>' arrives
        while (true)
        {
            char b = (char) (stdin.read());
            if (b == '>')
                break;

            ans += b;
        }
        return ans;
    }

    /**************************************************************************/
    private double getEngineLoad()
    {
        String ans;
        double load = 0;

        try
        {
            if (m_Connected)
                ans = sendObdCommand("01 04");
            // return is 41 04 XX, load is XX*100/255 %
            else
                ans = "41 04 82\r\r";

            ans = ans.substring(6, 6 + 2);
            load = Integer.parseInt(ans, 16) * 100.0 / 255.0;
        }
        catch (IOException e)
        {
            load = 0;
        }

        return load;
    }

    /**************************************************************************/
    private double getCoolingTemperature()
    {
        String ans;
        double temperature = 0;

        try
        {
            if (m_Connected)
                ans = sendObdCommand("01 05");
            // return is 41 05 XX, temp is XX-40 celcius
            else
                ans = "41 05 68\r\r";

            ans = ans.substring(6, 6 + 2);
            temperature = Integer.parseInt(ans, 16) - 40;
        }
        catch (IOException e)
        {
            temperature = 0;
        }

        return temperature;
    }

    /**************************************************************************/
    private double getIntakePressure()
    {
        String ans;
        double pressure = 0;

        try
        {
            if (m_Connected)
                ans = sendObdCommand("01 0B");
            // return is 41 0B XX, pressure is XX kPa
            else
                ans = "41 0B 72\r\r";

            ans = ans.substring(6, 6 + 2);
            pressure = Integer.parseInt(ans, 16);
        }
        catch (IOException e)
        {
            pressure = 0;
        }

        return pressure;
    }

    /**************************************************************************/
    private double getEngineRpm()
    {
        String ans, anst;
        double rpm = 0;

        try
        {
            if (m_Connected)
                ans = sendObdCommand("01 0C");
            // return is 41 0C XX YY, rpm is (XX*256 + YY)/4 rpm
            else
                ans = "41 0C 26 20\r\r";

            anst = ans.substring(6, 6 + 2);
            rpm = Integer.parseInt(anst, 16) * 256;

            anst = ans.substring(9, 9 + 2);
            rpm += Integer.parseInt(anst, 16);

            rpm /= 4;
        }
        catch (IOException e)
        {
            rpm = 0;
        }

        return rpm;
    }

    /**************************************************************************/
    private double getVehicleSpeed()
    {
        String ans;
        double speed = 0;

        try
        {
            if (m_Connected)
                ans = sendObdCommand("01 0D");
            // return is 41 0D XX, speed is XX km/h
            else
                ans = "41 0D 10\r\r";

            ans = ans.substring(6, 6 + 2);
            speed = Integer.parseInt(ans, 16);
        }
        catch (IOException e)
        {
            speed = 0;
        }

        return speed;
    }

    /**************************************************************************/
    private double getIntakeTemperature()
    {
        String ans;
        double temperature = 0;

        try
        {
            if (m_Connected)
                ans = sendObdCommand("01 0F");
            // return is 41 0F XX, temp is XX-40 celcius
            else
                ans = "41 0F 72\r\r";

            ans = ans.substring(6, 6 + 2);
            temperature = Integer.parseInt(ans, 16) - 40;
        }
        catch (IOException e)
        {
            temperature = 0;
        }

        return temperature;
    }

    /**************************************************************************/
    private double getMafRate()
    {
        String ans, anst;
        double rate = 0;

        try
        {
            if (m_Connected)
                ans = sendObdCommand("01 10");
            // return is 41 10 XX YY, rate = (XX*256+YY) / 100 g/s
            else
                ans = "41 10 10 20\r\r";

            anst = ans.substring(6, 6 + 2);
            rate = Integer.parseInt(anst, 16) * 256;

            anst = ans.substring(9, 9 + 2);
            rate += Integer.parseInt(anst, 16);

            rate /= 100;
        }
        catch (IOException e)
        {
            rate = 0;
        }

        return rate;
    }

    /**************************************************************************/
    private double getRailPressure()
    {
        String ans, anst;
        double pressure = 0;

        try
        {
            if (m_Connected)
                ans = sendObdCommand("01 23");
            // return is 41 23 XX YY , pressure = (XX*256 + YY) * 10 kPa
            else
                ans = "41 23 10 20\r\r";

            anst = ans.substring(6, 6 + 2);
            pressure = Integer.parseInt(anst, 16) * 256;

            anst = ans.substring(9, 9 + 2);
            pressure += Integer.parseInt(anst, 16);

            pressure *= 10;
        }
        catch (IOException e)
        {
            pressure = 0;
        }

        return pressure;
    }
}
