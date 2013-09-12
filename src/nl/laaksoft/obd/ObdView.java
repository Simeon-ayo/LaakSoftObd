package nl.laaksoft.obd;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ObdView extends View
{
    private static final String TAG = "OBD";

    private RectF area;
    private Rect bounds;

    private Paint paintLinesWhite;
    private Paint paintLinesWarning;
    private Paint paintLinesDanger;
    private Paint paintLinesGreenThick;

    private Paint paintSmallTextWhite;
    private Paint paintLargeTextWhite;
    private Paint paintLargeTextAmber;

    private Paint paintSmallTextBlue;
    private Paint paintLargeTextBlue;

    private Paint paintPieNormal;
    private Paint paintPieWarning;
    private Paint paintPieDanger;

    private Paint paintSmallTextGreen;

    private Paint paintLargeTextRed;

    private Locale locale;
    private SimpleDateFormat sdf;

    private Paint paintLargeTextBlueL;

    public ObdView(Context context)
    {
        super(context);
        Init(context.getApplicationContext());
    }

    public ObdView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        Init(context.getApplicationContext());
    }

    public ObdView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        Init(context.getApplicationContext());
    }

    private void Init(Context appcontext)
    {
        Log.d(TAG, "View init");

        locale = getResources().getConfiguration().locale;
        sdf = new SimpleDateFormat("HHmm", locale);

        area = new RectF();
        bounds = new Rect();

        paintLinesWhite = new Paint();
        paintLinesWarning = new Paint();
        paintLinesDanger = new Paint();
        paintLinesGreenThick = new Paint();
        paintLargeTextWhite = new Paint();
        paintSmallTextWhite = new Paint();
        paintLargeTextAmber = new Paint();
        paintPieNormal = new Paint();
        paintPieWarning = new Paint();
        paintPieDanger = new Paint();
        paintSmallTextBlue = new Paint();
        paintLargeTextBlue = new Paint();
        paintLargeTextBlueL = new Paint();
        paintSmallTextGreen = new Paint();
        paintLargeTextRed = new Paint();

        paintLinesWhite.setAntiAlias(true);
        paintLinesWhite.setStyle(Paint.Style.STROKE);
        paintLinesWhite.setColor(Color.WHITE);
        paintLinesWhite.setStrokeWidth(0.02f);

        paintLinesWarning.setAntiAlias(true);
        paintLinesWarning.setStyle(Paint.Style.STROKE);
        paintLinesWarning.setColor(Color.rgb(192, 160, 0));
        paintLinesWarning.setStrokeWidth(0.03f);
        paintLinesWarning.setStrokeCap(Paint.Cap.ROUND);

        paintLinesDanger.setAntiAlias(true);
        paintLinesDanger.setStyle(Paint.Style.STROKE);
        paintLinesDanger.setColor(Color.rgb(192, 0, 0));
        paintLinesDanger.setStrokeWidth(0.03f);
        paintLinesDanger.setStrokeCap(Paint.Cap.ROUND);

        paintLinesGreenThick.setAntiAlias(true);
        paintLinesGreenThick.setStyle(Paint.Style.STROKE);
        paintLinesGreenThick.setColor(Color.rgb(64, 192, 64));
        paintLinesGreenThick.setStrokeWidth(0.1f);
        // paintLinesGreenThick.setStrokeCap(Paint.Cap.ROUND);

        paintPieNormal.setAntiAlias(true);
        paintPieNormal.setStyle(Paint.Style.FILL);
        paintPieNormal.setColor(Color.rgb(128, 128, 140));

        paintPieWarning.setAntiAlias(true);
        paintPieWarning.setStyle(Paint.Style.FILL);
        paintPieWarning.setColor(Color.rgb(192, 160, 0));

        paintPieDanger.setAntiAlias(true);
        paintPieDanger.setStyle(Paint.Style.FILL);
        paintPieDanger.setColor(Color.rgb(192, 0, 0));

        paintLargeTextWhite.setAntiAlias(true);
        paintLargeTextWhite.setTextSize(0.3f);
        paintLargeTextWhite.setTextAlign(Align.RIGHT);
        paintLargeTextWhite.setStyle(Paint.Style.FILL);
        paintLargeTextWhite.setColor(Color.WHITE);

        paintLargeTextAmber.setAntiAlias(true);
        paintLargeTextAmber.setTextSize(0.3f);
        paintLargeTextAmber.setTextAlign(Align.RIGHT);
        paintLargeTextAmber.setStyle(Paint.Style.FILL);
        paintLargeTextAmber.setColor(Color.rgb(192, 160, 0));

        paintLargeTextRed.setAntiAlias(true);
        paintLargeTextRed.setTextSize(0.3f);
        paintLargeTextRed.setTextAlign(Align.RIGHT);
        paintLargeTextRed.setStyle(Paint.Style.FILL);
        paintLargeTextRed.setColor(Color.rgb(192, 0, 0));

        paintSmallTextWhite.setAntiAlias(true);
        paintSmallTextWhite.setTextSize(0.2f);
        paintSmallTextWhite.setTextAlign(Align.CENTER);
        paintSmallTextWhite.setStyle(Paint.Style.FILL);
        paintSmallTextWhite.setColor(Color.WHITE);

        paintSmallTextBlue.setAntiAlias(true);
        paintSmallTextBlue.setTextSize(0.2f);
        paintSmallTextBlue.setTextAlign(Align.CENTER);
        paintSmallTextBlue.setStyle(Paint.Style.FILL);
        paintSmallTextBlue.setColor(Color.rgb(64, 160, 255));

        paintSmallTextGreen.setAntiAlias(true);
        paintSmallTextGreen.setTextSize(0.3f);
        paintSmallTextGreen.setTextAlign(Align.RIGHT);
        paintSmallTextGreen.setStyle(Paint.Style.FILL);
        paintSmallTextGreen.setColor(Color.rgb(64, 192, 64));

        paintLargeTextBlue.setAntiAlias(true);
        paintLargeTextBlue.setTextSize(0.3f);
        paintLargeTextBlue.setTextAlign(Align.RIGHT);
        paintLargeTextBlue.setStyle(Paint.Style.FILL);
        paintLargeTextBlue.setColor(Color.rgb(64, 160, 255));

        paintLargeTextBlueL.setAntiAlias(true);
        paintLargeTextBlueL.setTextSize(0.3f);
        paintLargeTextBlueL.setTextAlign(Align.LEFT);
        paintLargeTextBlueL.setStyle(Paint.Style.FILL);
        paintLargeTextBlueL.setColor(Color.rgb(64, 160, 255));
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        MainActivity mainact = (MainActivity) getContext();
        String text;
        Paint myPaint;

        canvas.getClipBounds(bounds);
        int w = bounds.width();
        int h = bounds.height();
        int rad = (int) (Math.min(w, h / 3) * 0.45);

        /*********************************************************************/
        /** time **/
        /*********************************************************************/
        canvas.setMatrix(null);
        canvas.translate(0.00f * w, 0.04f * h);
        canvas.scale(rad, rad);

        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date dat = new Date();
        final String utcTime = sdf.format(dat);

        // draw dial label
        long frac = dat.getTime() % 60000;
        String dec = String.format(locale, "%d", frac / 6000);
        canvas.drawText(utcTime + "." + dec + "z", 0f, 0f, paintLargeTextBlueL);

        /*********************************************************************/
        /** flow dial **/
        /*********************************************************************/

        float load = (float) mainact.m_ObdData.m_EngineLoad;
        canvas.setMatrix(null);
        canvas.translate(0.35f * w, 0.20f * h);
        canvas.scale(rad, rad);
        area.set(-1, -1, 1, 1);

        // draw load pie
        myPaint = paintPieNormal;
        if (load >= 98)
            myPaint = paintPieDanger;
        else if (load >= 90)
            myPaint = paintPieWarning;
        canvas.drawArc(area, 0f, 225.0f * load / 100.0f, true, myPaint);

        // draw load text
        area.set(-1, -1, 1, 1);
        text = String.format(locale, "%.0f", load);
        canvas.drawText(text, 0.9f, -0.2f, paintLargeTextWhite);

        // draw load marker ticks
        canvas.save();
        for (int i = 20; i <= 100; i += 20)
        {
            canvas.rotate(225 / 5.0f);
            canvas.drawLine(0.85f, 0, 1.0f, 0, paintLinesWhite);
        }
        canvas.restore();

        // draw dial contour
        canvas.drawArc(area, 0f, 225f * 90.0f / 100.0f, false, paintLinesWhite);
        canvas.drawArc(area, 225f * 90.0f / 100.0f, 225f * 9.0f / 100.0f, false, paintLinesWarning);
        canvas.drawArc(area, 225f * 99.0f / 100.0f, 225f * 1.0f / 100.0f, false, paintLinesDanger);

        canvas.save();
        canvas.rotate(225f * 90.0f / 100.0f);
        canvas.drawLine(1.0f, 0, 1.15f, 0, paintLinesWarning);
        canvas.restore();
        canvas.save();
        canvas.rotate(225f * 99.0f / 100.0f);
        canvas.drawLine(1.0f, 0, 1.15f, 0, paintLinesDanger);
        canvas.restore();

        // draw text border
        canvas.drawRect(0.1f, -0.5f, 1.0f, -0.1f, paintLinesWhite);

        // draw load marker numbers
        for (int i = 20; i <= 100; i += 20)
        {
            float xp = (float) (0.65 * Math.cos(i * 225 / 100.0 * Math.PI / 180.0));
            float yp = 0.05f + (float) (0.65 * Math.sin(i * 225 / 100.0 * Math.PI / 180.0));
            text = String.format(locale, "%d", i);
            canvas.drawText(text, xp, yp, paintSmallTextWhite);
        }

        // draw dial label
        canvas.drawText("load", -1.2f, 0.95f, paintSmallTextBlue);

        /*********************************************************************/
        /** Rpm dial **/
        /*********************************************************************/

        float rpm = (float) mainact.m_ObdData.m_EngineRpm;
        canvas.setMatrix(null);
        canvas.translate(0.35f * w, 0.50f * h);
        canvas.scale(rad, rad);
        area.set(-1, -1, 1, 1);

        float maxrpm = (float) (double) (mainact.m_ObdData.m_GearMaxRpm
                .get(mainact.m_ObdData.m_CurrentGear));

        // draw rpm pie
        myPaint = paintPieNormal;
        if (rpm > 3500)
            myPaint = paintPieDanger;
        else if (rpm > maxrpm)
            myPaint = paintPieWarning;
        canvas.drawArc(area, 0f, 225.0f * rpm / 5000.0f, true, myPaint);

        // draw speed text
        text = String.format(locale, "%.0f", rpm);
        canvas.drawText(text, 0.9f, -0.2f, paintLargeTextWhite);

        // draw optimum gear
        myPaint = paintLargeTextBlue;
        if (mainact.m_ObdData.m_OptimumGear != mainact.m_ObdData.m_CurrentGear)
        {
            myPaint = paintLargeTextAmber;
        }
        text = mainact.m_ObdData.m_GearString.get(mainact.m_ObdData.m_OptimumGear);
        canvas.drawText(text, 0.9f, -0.6f, myPaint);

        // draw rpm marker ticks
        canvas.save();
        for (int i = 1000; i <= 5000; i += 1000)
        {
            canvas.rotate(225 / 5.0f);
            canvas.drawLine(0.85f, 0, 1.0f, 0, paintLinesWhite);
        }
        canvas.restore();

        // draw dial contour
        canvas.drawArc(area, 0f, 225f * maxrpm / 5000f, false, paintLinesWhite);
        canvas.drawArc(area, 225f * maxrpm / 5000f, 225f * (5000 - maxrpm) / 5000f, false,
                paintLinesWarning);
        canvas.drawArc(area, 225f * 3500f / 5000f, 225f * 1500f / 5000f, false, paintLinesDanger);

        canvas.save();
        canvas.rotate(225f * 3500f / 5000f);
        canvas.drawLine(1.0f, 0, 1.15f, 0, paintLinesDanger);
        canvas.restore();

        canvas.save();
        canvas.rotate(225f * maxrpm / 5000f);
        canvas.drawLine(1.0f, 0, 1.15f, 0, paintLinesWarning);
        canvas.restore();

        // draw text border
        canvas.drawRect(0.1f, -0.5f, 1.0f, -0.1f, paintLinesWhite);

        // draw rpm marker numbers
        for (int i = 1; i <= 5; i += 1)
        {
            float xp = (float) (0.7 * Math.cos(i * 225 / 5.0 * Math.PI / 180.0));
            float yp = 0.05f + (float) (0.7 * Math.sin(i * 225 / 5.0 * Math.PI / 180.0));
            text = String.format(locale, "%d", i);
            canvas.drawText(text, xp, yp, paintSmallTextWhite);
        }

        // draw dial label
        canvas.drawText("rpm", -1.2f, 0.95f, paintSmallTextBlue);

        /*********************************************************************/
        /** Speed dial **/
        /*********************************************************************/

        float speed = (float) mainact.m_ObdData.m_VehicleSpeed;
        canvas.setMatrix(null);
        canvas.translate(0.35f * w, 0.8f * h);
        canvas.scale(rad, rad);
        area.set(-1, -1, 1, 1);

        // draw speed pie
        myPaint = paintPieNormal;
        if (speed > mainact.m_ObdData.m_MaxSpeed + 6)
            myPaint = paintPieDanger;
        else if (speed > mainact.m_ObdData.m_MaxSpeed + 4)
            myPaint = paintPieWarning;
        canvas.drawArc(area, 0f, 225.0f * speed / 140.0f, true, myPaint);

        // draw speed text
        myPaint = paintLargeTextWhite;
        if (mainact.m_ObdData.m_VehicleSpeed > mainact.m_ObdData.m_MaxSpeed + 6)
        {
            myPaint = paintLargeTextRed;
        }
        else if (mainact.m_ObdData.m_VehicleSpeed > mainact.m_ObdData.m_MaxSpeed + 4)
        {
            myPaint = paintLargeTextAmber;
        }

        text = String.format(locale, "%.1f", speed);
        canvas.drawText(text, 0.9f, -0.2f, myPaint);

        // draw max speed text
        text = String.format(locale, "%.1f", mainact.m_ObdData.m_MaxSpeed);
        canvas.drawText(text, 0.9f, -0.6f, paintSmallTextGreen);

        // draw text border
        canvas.drawRect(0.1f, -0.5f, 1.0f, -0.1f, paintLinesWhite);

        // draw kph marker ticks
        canvas.save();
        for (int i = 10; i <= 140; i += 20)
        {
            canvas.rotate(225 / 7.0f);
            canvas.drawLine(0.85f, 0, 1.0f, 0, paintLinesWhite);
        }
        canvas.restore();

        // draw dial contour
        canvas.drawArc(area, 0f, 225f * (float) (mainact.m_ObdData.m_MaxSpeed + 4) / 140.0f, false,
                paintLinesWhite);

        canvas.drawArc(area, 225f * ((float) mainact.m_ObdData.m_MaxSpeed + 4) / 140.0f,
                225f * 2.0f / 140.0f, false, paintLinesWarning);

        canvas.drawArc(area, 225f * (float) (mainact.m_ObdData.m_MaxSpeed + 6) / 140.0f,
                225f * (135.0f - (float) mainact.m_ObdData.m_MaxSpeed - 6) / 140.0f, false,
                paintLinesDanger);

        // draw kph limit
        canvas.save();
        canvas.rotate(225 / 7.0f * (float) (mainact.m_ObdData.m_MaxSpeed + 4) / 20.0f);
        canvas.drawLine(1.0f, 0, 1.15f, 0, paintLinesWarning);
        canvas.restore();
        canvas.save();
        canvas.rotate(225 / 7.0f * (float) (mainact.m_ObdData.m_MaxSpeed + 6) / 20.0f);
        canvas.drawLine(1.0f, 0, 1.2f, 0, paintLinesDanger);
        canvas.restore();

        // draw kph marker numbers
        for (int i = 2; i <= 14; i += 2)
        {
            float xp = (float) (0.65 * Math.cos(i * 225 / 14.0 * Math.PI / 180.0));
            float yp = 0.05f + (float) (0.65 * Math.sin(i * 225 / 14.0 * Math.PI / 180.0));
            text = String.format(locale, "%d", i);
            canvas.drawText(text, xp, yp, paintSmallTextWhite);
        }

        // draw dial label
        canvas.drawText("kph", -1.2f, 0.95f, paintSmallTextBlue);

        /*********************************************************************/
        /** Speed strip **/
        /*********************************************************************/

        canvas.setMatrix(null);
        canvas.translate(0.85f * w, 0.5f * h);
        canvas.scale(rad, rad);

        {
            myPaint = paintPieNormal;
            if (speed > mainact.m_ObdData.m_MaxSpeed + 6)
                myPaint = paintPieDanger;
            else if (speed > mainact.m_ObdData.m_MaxSpeed + 4)
                myPaint = paintPieWarning;

            float y = (speed - (float) mainact.m_ObdData.m_MaxSpeed) * -6.0f / 20.0f;

            y = Math.min(3, Math.max(-3, y));
            area.set(-0.2f, y, 0.2f, 3);
            canvas.drawRect(area, myPaint);
        }

        area.set(-0.2f, -3f, 0.2f, 3f);
        canvas.drawRect(area, paintLinesWhite);

        // draw kph markers
        {
            float y;
            y = 0 * -6.0f / 20.0f;
            canvas.drawLine(-0.3f, y, -0.2f, y, paintLinesWhite);
            canvas.drawLine(0.3f, y, 0.2f, y, paintLinesWhite);
            y = 4 * -6.0f / 20.0f;
            canvas.drawLine(-0.35f, y, -0.2f, y, paintLinesWarning);
            canvas.drawLine(0.35f, y, 0.2f, y, paintLinesWarning);
            y = 6 * -6.0f / 20.0f;
            canvas.drawLine(-0.4f, y, -0.2f, y, paintLinesDanger);
            canvas.drawLine(0.4f, y, 0.2f, y, paintLinesDanger);
        }
    }
}
