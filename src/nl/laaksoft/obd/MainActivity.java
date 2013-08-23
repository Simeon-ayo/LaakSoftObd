package nl.laaksoft.obd;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/******************************************************************************/
/**
 * The main activity.
 */
public class MainActivity extends Activity implements OnTouchListener
{
    private static final String TAG = "OBD";
    private ObdConnection m_Obd;
    public VehicleData m_ObdData;
    private ObdView m_View;
    private Handler m_Handler;
    private WakeLock m_WakeLock;

    /**************************************************************************/
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        m_WakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "My Tag");

        m_View = new ObdView(this);
        setContentView(m_View);

        m_Obd = new ObdConnection();
        m_ObdData = new VehicleData();

        m_Handler = new Handler();

        m_View.setOnTouchListener(this);
    }

    /**************************************************************************/
    @Override
    public void onStart()
    {
        Log.d(TAG, "onStart");
        super.onStart();

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

    }

    /**************************************************************************/
    @Override
    protected void onResume()
    {
        Log.d(TAG, "onResume");
        super.onResume();

        m_WakeLock.acquire();

        m_Updater.run();
    }

    /**************************************************************************/
    Runnable m_Updater = new Runnable()
    {
        @Override
        public void run()
        {
            m_Obd.updateData(m_ObdData);
            m_ObdData.calculate();
            m_View.invalidate();

            // Do it all again in 100 ms
            m_Handler.postDelayed(m_Updater, 100);
        }
    };

    /**************************************************************************/
    @Override
    protected void onPause()
    {
        Log.d(TAG, "onPause");
        super.onPause();

        m_Handler.removeCallbacks(m_Updater);
        m_WakeLock.release();
    }

    /**************************************************************************/
    @Override
    protected void onStop()
    {
        Log.d(TAG, "onStop");
        super.onStop();

        m_Obd.stopObdConnection();
    }

    /**************************************************************************/
    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            m_ObdData.m_MaxSpeed = m_ObdData.m_VehicleSpeed;
            return true;
        }
        return false;
    }
}