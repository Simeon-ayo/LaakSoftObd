package nl.laaksoft.obd;

import nl.laaksoft.obd.reader.R;
import preferences.MainPrefsActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
public class MainActivity extends Activity implements OnTouchListener,
        OnSharedPreferenceChangeListener
{
    private static final String TAG = "OBD";
    private IObdConnection m_Obd;
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
        m_WakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "My Tag");

        m_View = new ObdView(this);
        setContentView(m_View);

        m_Obd = new ObdConnection();
        m_ObdData = new VehicleData();

        m_Handler = new Handler();

        m_View.setOnTouchListener(this);

        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        m_ObdData.p_gain = app_preferences.getInt("P", 5);
        m_ObdData.i_gain = app_preferences.getInt("I", 0);

        app_preferences.registerOnSharedPreferenceChangeListener(this);

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

            // fall back to simulation
            m_Obd = new ObdConnectionSim();
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
    public boolean onTouch(View v, MotionEvent ev)
    {
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_UP:
                return true;
            case MotionEvent.ACTION_MOVE:
                // if (ev.getHistorySize() > 0) // Android 2.3+
                // {
                // double dx = ev.getX() - ev.getHistoricalX(ev.getHistorySize()
                // - 1);
                //
                // m_ObdData.m_MaxSpeed += dx / 5.0;
                //
                // m_ObdData.m_MaxSpeed = Math.max(m_ObdData.m_MaxSpeed, 0);
                // m_ObdData.m_MaxSpeed = Math.min(m_ObdData.m_MaxSpeed, 140);
                // }
                return true;
        }
        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.miPreferences:
                Intent intent = new Intent(this, MainPrefsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key)
    {
        if (key.equals("P"))
        {
            Integer p = prefs.getInt(key, 0);
            m_ObdData.p_gain = p;
            Log.d(TAG, "P: " + p.toString());
        }
        if (key.equals("I"))
        {
            Integer i = prefs.getInt(key, 0);
            m_ObdData.i_gain = i;
            Log.d(TAG, "I: " + i.toString());
        }
    }
}