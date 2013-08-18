/*
 * TODO put header
 */
package nl.laaksoft.obd.reader;

import java.text.DecimalFormat;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/******************************************************************************/
/**
 * The main activity.
 */
public class MainActivity extends Activity
{
    private static final String TAG = "MainActivity";
    private Button btnQuery;
    private ObdConnection obd;
    private TextView textView00;
    private TextView textView01;
    private TextView textView02;
    private TextView textView03;
    private TextView textView04;
    private TextView textView05;
    private TextView textView06;
    private TextView textView07;

    /**************************************************************************/
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        btnQuery = (Button) findViewById(R.id.btnQuery);
        btnQuery.setOnClickListener(onClickQuery);

        textView00 = (TextView) findViewById(R.id.textView00);
        textView01 = (TextView) findViewById(R.id.TextView01);
        textView02 = (TextView) findViewById(R.id.TextView02);
        textView03 = (TextView) findViewById(R.id.TextView03);
        textView04 = (TextView) findViewById(R.id.TextView04);
        textView05 = (TextView) findViewById(R.id.TextView05);
        textView06 = (TextView) findViewById(R.id.TextView06);
        textView07 = (TextView) findViewById(R.id.TextView07);

        obd = new ObdConnection();
        try
        {
            obd.startObdConnection();
        }
        catch (Exception e)
        {
            Log.e(TAG, "No connection: " + e.getMessage());
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            obd.stopObdConnection();
        }
    }

    /**************************************************************************/
    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        obd.stopObdConnection();
    }

    /**************************************************************************/
    @Override
    protected void onPause()
    {
        super.onPause();

        obd.stopObdConnection();
    }

    /**************************************************************************/
    OnClickListener onClickQuery = new OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            double value = 0;
            String text = "";

            value = obd.getEngineLoad();
            text = new DecimalFormat("#").format(value);
            textView00.setText("1:4 Enginge load = " + text + " %");

            value = obd.getCoolingTemperature();
            text = new DecimalFormat("#").format(value);
            textView01.setText("1:5 Cooling water = " + text + " C");

            value = obd.getIntakePressure();
            text = new DecimalFormat("#").format(value);
            textView02.setText("1:B Intake pressure = " + text + " kPa");

            value = obd.getEngineRpm();
            text = new DecimalFormat("#").format(value);
            textView03.setText("1:C Engine speed = " + text + " rpm");

            value = obd.getVehicleSpeed();
            text = new DecimalFormat("#").format(value);
            textView04.setText("1:D Vehicle speed = " + text + " km/h");

            value = obd.getIntakeTemperature();
            text = new DecimalFormat("#").format(value);
            textView05.setText("1:F Intake temperature = " + text + " C");

            value = obd.getMafRate();
            text = new DecimalFormat("#").format(value);
            textView06.setText("1:10 MAF rate = " + text + " g/s");

            value = obd.getRailPressure();
            text = new DecimalFormat("#").format(value);
            textView07.setText("1:23 Rail pressure = " + text + " kPa");
        }
    };
}