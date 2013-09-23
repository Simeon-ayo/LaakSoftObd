package nl.laaksoft.obd;

import nl.laaksoft.obd.connection.IObdConnection;
import nl.laaksoft.obd.connection.ObdConnection;
import nl.laaksoft.obd.connection.ObdConnectionSim;
import nl.laaksoft.obd.connection.VehicleData;
import nl.laaksoft.obd.views.FlowView;
import nl.laaksoft.obd.views.RpmView;
import nl.laaksoft.obd.views.SpeedView;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/******************************************************************************/
/**
 * The main activity.
 */
public class MainActivity extends Activity {

	private static final String TAG = "OBD";

	private Handler m_Handler;
	private WakeLock m_WakeLock;

	private IObdConnection m_Obd;
	public VehicleData m_ObdData;

	private FlowView m_FlowView;
	private RpmView m_RpmView;
	private SpeedView m_SpeedView;

	/**************************************************************************/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		m_WakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK
				| PowerManager.ACQUIRE_CAUSES_WAKEUP
				| PowerManager.ON_AFTER_RELEASE, "My Tag");

		setContentView(R.layout.main);

		m_FlowView = (FlowView) findViewById(R.id.vwFlowView);
		m_RpmView = (RpmView) findViewById(R.id.vwRpmView);
		m_SpeedView = (SpeedView) findViewById(R.id.vwSpeedView);

		m_Obd = new ObdConnection();
		m_ObdData = new VehicleData();

		m_Handler = new Handler();
	}

	/**************************************************************************/
	@Override
	public void onStart() {
		Log.d(TAG, "onStart");
		super.onStart();

		try {
			m_Obd.startObdConnection();
			Toast.makeText(getApplicationContext(), "Connected",
					Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			Log.e(TAG, "No connection: " + e.getMessage());
			Toast.makeText(getApplicationContext(), e.getMessage(),
					Toast.LENGTH_LONG).show();
			m_Obd.stopObdConnection();

			// fall back to simulation
			m_Obd = new ObdConnectionSim();
		}
	}

	/**************************************************************************/
	@Override
	protected void onResume() {
		Log.d(TAG, "onResume");
		super.onResume();

		m_WakeLock.acquire();

		m_Updater.run();
	}

	/**************************************************************************/
	Runnable m_Updater = new Runnable() {
		@Override
		public void run() {
			m_Obd.updateData(m_ObdData);
			m_ObdData.calculate();

			m_FlowView.setmOdbData(m_ObdData);
			m_RpmView.setmOdbData(m_ObdData);
			m_SpeedView.setmOdbData(m_ObdData);

			// Do it all again in 100 ms
			m_Handler.postDelayed(m_Updater, 100);
		}
	};

	/**************************************************************************/
	@Override
	protected void onPause() {
		Log.d(TAG, "onPause");
		super.onPause();

		m_Handler.removeCallbacks(m_Updater);
		m_WakeLock.release();
	}

	/**************************************************************************/
	@Override
	protected void onStop() {
		Log.d(TAG, "onStop");
		super.onStop();

		m_Obd.stopObdConnection();
	}
}