/*
 * TODO put header
 */
package nl.laaksoft.obd.reader;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
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
    public ObdData m_ObdData;
    private ObdView m_View;
    private Handler m_Handler;

    /**************************************************************************/
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        // WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);

        m_View = new ObdView(this);
        setContentView(m_View);

        m_Obd = new ObdConnection();
        m_ObdData = new ObdData();

        try
        {
            m_Obd.startObdConnection();
            Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_LONG).show();
        }
        catch (Exception e)
        {
            Log.e(TAG, "No connection: " + e.getMessage());
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            m_Obd.stopObdConnection();
        }

        m_Handler = new Handler();
        m_Updater.run();
    }

    /**************************************************************************/
    Runnable m_Updater = new Runnable()
    {
        @Override
        public void run()
        {
            Log.e(TAG, "update");
            m_Obd.updateData(m_ObdData);
            m_View.invalidate();
            m_Handler.postDelayed(m_Updater, 100);
        }
    };

    /**************************************************************************/
    @Override
    protected void onPause()
    {
        m_Handler.removeCallbacks(m_Updater);
        m_Obd.stopObdConnection();

        super.onPause();
    }
}