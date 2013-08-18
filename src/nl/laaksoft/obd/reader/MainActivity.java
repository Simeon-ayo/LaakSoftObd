/*
 * TODO put header
 */
package nl.laaksoft.obd.reader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/******************************************************************************/
/**
 * The main activity.
 */
public class MainActivity extends Activity
{
    private static final String TAG = "MainActivity";
    private ObdConnection m_Obd;
    private ObdData m_ObdData;

    /**************************************************************************/
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        m_Obd = new ObdConnection();
        m_ObdData = new ObdData();

        try
        {
            m_Obd.startObdConnection();
            m_Obd.updateData(m_ObdData);
            Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_LONG).show();
        }
        catch (Exception e)
        {
            Log.e(TAG, "No connection: " + e.getMessage());
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            m_Obd.stopObdConnection();
        }
    }

    /**************************************************************************/
    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        m_Obd.stopObdConnection();
    }

    /**************************************************************************/
    @Override
    protected void onPause()
    {
        super.onPause();

        m_Obd.stopObdConnection();
    }
}