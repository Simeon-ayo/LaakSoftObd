package nl.laaksoft.obd;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ObdView extends View
{
    private static final String TAG = "OBD";

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

    private int m_Width;
    private int m_Height;
    private int m_Radius;
    private RectF area;

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
        paintLinesWhite.setStrokeCap(Paint.Cap.ROUND);

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
        paintLargeTextBlueL.setTextSize(40);
        paintLargeTextBlueL.setTextAlign(Align.LEFT);
        paintLargeTextBlueL.setStyle(Paint.Style.FILL);
        paintLargeTextBlueL.setColor(Color.rgb(64, 160, 255));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        Log.d(TAG, "Size changed");

        m_Width = w;
        m_Height = h;
        m_Radius = (int) (Math.min(w, h / 3) * 0.45);

        // TODO resize fonts and lines
        paintLargeTextBlue.setTextSize(m_Radius * 0.3f);
        paintLargeTextBlueL.setTextSize(m_Radius * 0.3f);
        paintLargeTextWhite.setTextSize(m_Radius * 0.3f);
        paintLargeTextAmber.setTextSize(m_Radius * 0.3f);
        paintLargeTextRed.setTextSize(m_Radius * 0.3f);
        paintSmallTextWhite.setTextSize(m_Radius * 0.15f);
        paintSmallTextBlue.setTextSize(m_Radius * 0.15f);
        paintSmallTextGreen.setTextSize(m_Radius * 0.3f);

        paintLinesWhite.setStrokeWidth(m_Radius * 0.02f);
        paintLinesWarning.setStrokeWidth(m_Radius * 0.02f);
        paintLinesDanger.setStrokeWidth(m_Radius * 0.02f);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        MainActivity mainact = (MainActivity) getContext();

        drawTime(canvas);
        drawSpeedDial(canvas, mainact);
        drawSpeedStrip(canvas, mainact);
    }

    private void drawTime(Canvas canvas)
    {
        /*********************************************************************/
        /** time **/
        /*********************************************************************/
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date dat = new Date();
        final String utcTime = sdf.format(dat);

        // draw dial label
        long frac = dat.getTime() % 60000;
        String dec = String.format(locale, "%d", frac / 6000);
        canvas.drawText(utcTime + "." + dec + "z", 0f, m_Radius * 0.3f, paintLargeTextBlueL);
    }

    private float drawSpeedDial(Canvas canvas, MainActivity mainact)
    {
        /*********************************************************************/
        /** Speed dial **/
        /*********************************************************************/
        String text;
        Paint myPaint;
        float xc = m_Width / 2.0f;
        float yc = m_Height * 0.95f - m_Radius;
        float rad = m_Radius;

        float speed = (float) mainact.m_ObdData.m_VehicleSpeed;

        // draw speed pie
        myPaint = paintPieNormal;
        if (speed > mainact.m_ObdData.m_MaxSpeed + 6)
            myPaint = paintPieDanger;
        else if (speed > mainact.m_ObdData.m_MaxSpeed + 4)
            myPaint = paintPieWarning;
        area.set(xc - rad, yc - rad, xc + rad, yc + rad);
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
        canvas.drawText(text, xc + 0.9f * rad, yc - 0.2f * rad, myPaint);

        // draw max speed text
        text = String.format(locale, "%.1f", mainact.m_ObdData.m_MaxSpeed);
        canvas.drawText(text, xc + 0.9f * rad, yc - 0.6f * rad, paintSmallTextGreen);

        // draw text border
        canvas.drawRect(xc + rad * 0.1f, yc - 0.5f * rad, xc + 1.0f * rad, yc - 0.1f * rad,
                paintLinesWhite);

        // draw kph marker ticks
        for (int i = 0; i <= 140; i += 20)
        {
            double ang = (i / 140.0 * 225.0) * Math.PI / 180.0;
            canvas.drawLine(
                    //
                    (float) (xc + 0.85 * rad * Math.cos(ang)),
                    (float) (yc + 0.85 * rad * Math.sin(ang)),
                    (float) (xc + 1.00 * rad * Math.cos(ang)),
                    (float) (yc + 1.00 * rad * Math.sin(ang)), paintLinesWhite);
        }

        // draw dial contour
        canvas.drawArc(area, 0f, 225f * (float) (mainact.m_ObdData.m_MaxSpeed + 4) / 140.0f, false,
                paintLinesWhite);

        canvas.drawArc(area, 225f * ((float) mainact.m_ObdData.m_MaxSpeed + 4) / 140.0f,
                225f * 2.0f / 140.0f, false, paintLinesWarning);

        canvas.drawArc(area, 225f * (float) (mainact.m_ObdData.m_MaxSpeed + 6) / 140.0f,
                225f * (140.0f - (float) mainact.m_ObdData.m_MaxSpeed - 6) / 140.0f, false,
                paintLinesDanger);

        // draw kph limit
        {
            double ang = ((float) (mainact.m_ObdData.m_MaxSpeed + 4) / 140.0 * 225.0) * Math.PI
                    / 180.0;
            canvas.drawLine(
                    //
                    (float) (xc + 1.00 * rad * Math.cos(ang)),
                    (float) (yc + 1.00 * rad * Math.sin(ang)),
                    (float) (xc + 1.15 * rad * Math.cos(ang)),
                    (float) (yc + 1.15 * rad * Math.sin(ang)), paintLinesWarning);
        }

        {
            double ang = ((float) (mainact.m_ObdData.m_MaxSpeed + 6) / 140.0 * 225.0) * Math.PI
                    / 180.0;
            canvas.drawLine(
                    //
                    (float) (xc + 1.00 * rad * Math.cos(ang)),
                    (float) (yc + 1.00 * rad * Math.sin(ang)),
                    (float) (xc + 1.15 * rad * Math.cos(ang)),
                    (float) (yc + 1.15 * rad * Math.sin(ang)), paintLinesDanger);
        }

        // draw kph marker numbers
        for (int i = 2; i <= 14; i += 2)
        {
            float xp = (float) (xc + 0.65 * rad * Math.cos(i * 225 / 14.0 * Math.PI / 180.0));
            float yp = 0.07f * rad
                    + (float) (yc + 0.65 * rad * Math.sin(i * 225 / 14.0 * Math.PI / 180.0));
            text = String.format(locale, "%d", i);
            canvas.drawText(text, xp, yp, paintSmallTextWhite);
        }

        // draw dial label
        canvas.drawText("kph", xc - 1.2f * rad, yc + 0.95f * rad, paintSmallTextBlue);
        return speed;
    }

    private void drawSpeedStrip(Canvas canvas, MainActivity mainact)
    {
        /*********************************************************************/
        /** Speed strip **/
        /*********************************************************************/
        float xc = m_Width * 0.9f;
        float yc = m_Height * 0.5f;
        float xs = m_Width * 0.03f;
        float ys = m_Height * 0.45f;

        float speed = (float) mainact.m_ObdData.m_VehicleSpeed;

        Paint myPaint;
        {
            myPaint = paintPieNormal;
            if (speed > mainact.m_ObdData.m_MaxSpeed + 6)
                myPaint = paintPieDanger;
            else if (speed > mainact.m_ObdData.m_MaxSpeed + 4)
                myPaint = paintPieWarning;

            float y = (speed - (float) mainact.m_ObdData.m_MaxSpeed);

            y = Math.min(10, Math.max(-10, y));
            area.set(xc - xs, yc - ys * y / 10.0f, xc + xs, yc + ys);
            canvas.drawRect(area, myPaint);
        }

        area.set(xc - xs, yc - ys, xc + xs, yc + ys);
        canvas.drawRect(area, paintLinesWhite);

        // draw kph markers
        {
            float y;
            y = yc;
            canvas.drawLine(xc - 1.5f * xs, y, xc - 1.0f * xs, y, paintLinesWhite);
            canvas.drawLine(xc + 1.5f * xs, y, xc + 1.0f * xs, y, paintLinesWhite);

            y = yc - ys * 4.0f / 10.0f;
            canvas.drawLine(xc - 1.5f * xs, y, xc - 1.0f * xs, y, paintLinesWarning);
            canvas.drawLine(xc + 1.5f * xs, y, xc + 1.0f * xs, y, paintLinesWarning);

            y = yc - ys * 6.0f / 10.0f;
            canvas.drawLine(xc - 1.5f * xs, y, xc - 1.0f * xs, y, paintLinesDanger);
            canvas.drawLine(xc + 1.5f * xs, y, xc + 1.0f * xs, y, paintLinesDanger);
        }
    }
}
