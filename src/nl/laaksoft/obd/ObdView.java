package nl.laaksoft.obd;

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
    private Paint paintLinesGreen;

    private Paint paintSmallTextWhite;
    private Paint paintLargeTextWhite;
    private Paint paintLargeTextAmber;

    private Paint paintSmallTextBlue;
    private Paint paintLargeTextBlue;

    private Paint paintPieNormal;
    private Paint paintPieWarning;
    private Paint paintPieDanger;

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

        area = new RectF();
        bounds = new Rect();

        paintLinesWhite = new Paint();
        paintLinesGreen = new Paint();
        paintLargeTextWhite = new Paint();
        paintSmallTextWhite = new Paint();
        paintLargeTextAmber = new Paint();
        paintPieNormal = new Paint();
        paintPieWarning = new Paint();
        paintPieDanger = new Paint();
        paintSmallTextBlue = new Paint();
        paintLargeTextBlue = new Paint();

        paintLinesWhite.setAntiAlias(true);
        paintLinesWhite.setStyle(Paint.Style.STROKE);
        paintLinesWhite.setColor(Color.WHITE);
        paintLinesWhite.setStrokeWidth(0.02f);

        paintLinesGreen.setAntiAlias(true);
        paintLinesGreen.setStyle(Paint.Style.STROKE);
        paintLinesGreen.setColor(Color.GREEN);
        paintLinesGreen.setStrokeWidth(0.03f);

        paintPieNormal.setAntiAlias(true);
        paintPieNormal.setStyle(Paint.Style.FILL);
        paintPieNormal.setColor(Color.rgb(128, 128, 140));

        paintPieWarning.setAntiAlias(true);
        paintPieWarning.setStyle(Paint.Style.FILL);
        paintPieWarning.setColor(Color.rgb(160, 160, 0));

        paintPieDanger.setAntiAlias(true);
        paintPieDanger.setStyle(Paint.Style.FILL);
        paintPieDanger.setColor(Color.rgb(160, 0, 0));

        paintLargeTextWhite.setAntiAlias(true);
        paintLargeTextWhite.setTextSize(0.3f);
        paintLargeTextWhite.setTextAlign(Align.RIGHT);
        paintLargeTextWhite.setStyle(Paint.Style.FILL);
        paintLargeTextWhite.setColor(Color.WHITE);

        paintLargeTextAmber.setAntiAlias(true);
        paintLargeTextAmber.setTextSize(0.3f);
        paintLargeTextAmber.setTextAlign(Align.RIGHT);
        paintLargeTextAmber.setStyle(Paint.Style.FILL);
        paintLargeTextAmber.setColor(Color.rgb(255, 160, 0));

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

        paintLargeTextBlue.setAntiAlias(true);
        paintLargeTextBlue.setTextSize(0.3f);
        paintLargeTextBlue.setTextAlign(Align.RIGHT);
        paintLargeTextBlue.setStyle(Paint.Style.FILL);
        paintLargeTextBlue.setColor(Color.rgb(64, 160, 255));
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        MainActivity mainact = (MainActivity) getContext();
        String text;

        canvas.getClipBounds(bounds);
        int xc = bounds.centerX();
        int yc = bounds.centerY();
        int rad = (int) (Math.min(xc, yc / 2) * 0.8);

        /*********************************************************************/
        /** Speed dial **/
        /*********************************************************************/

        float speed = (float) mainact.m_ObdData.m_VehicleSpeed;
        canvas.setMatrix(null);
        canvas.translate(xc, yc / 2 - 20);
        canvas.scale(rad, rad);
        area.set(-1, -1, 1, 1);

        // draw speed pie
        canvas.drawArc(area, 0f, 225.0f * speed / 140.0f, true, paintPieNormal);

        // draw max speed tick
        canvas.save();
        canvas.rotate((float) (mainact.m_ObdData.m_MaxSpeed * 225 / 140.0f));
        canvas.drawLine(1.0f, 0f, 1.15f, -0.1f, paintLinesGreen);
        canvas.drawLine(1.0f, 0f, 1.15f, 0.1f, paintLinesGreen);
        canvas.restore();

        // draw speed text
        Paint myPaint = paintLargeTextWhite;
        if (mainact.m_ObdData.m_VehicleSpeed > mainact.m_ObdData.m_MaxSpeed)
        {
            myPaint = paintLargeTextAmber;
        }

        text = String.format("%.0f", speed);
        canvas.drawText(text, 0.8f, -0.2f, myPaint);

        // draw max speed text
        text = String.format("%.0f", mainact.m_ObdData.m_MaxSpeed);
        canvas.drawText(text, 0.8f, -0.6f, paintLargeTextBlue);

        // draw dial contour
        canvas.drawArc(area, 0f, 225f, true, paintLinesWhite);

        // draw text border
        canvas.drawRect(0.1f, -0.5f, 1.0f, -0.1f, paintLinesWhite);

        // draw kph marker ticks
        canvas.save();
        for (int i = 10; i < 140; i += 20)
        {
            canvas.rotate(225 / 7.0f);
            canvas.drawLine(0.8f, 0, 1.0f, 0, paintLinesWhite);
        }
        canvas.restore();

        // draw kph marker numbers
        for (int i = 2; i <= 12; i += 2)
        {
            float xp = (float) (0.7 * Math.cos(i * 225 / 14.0 * Math.PI / 180.0));
            float yp = (float) (0.7 * Math.sin(i * 225 / 14.0 * Math.PI / 180.0));
            text = String.format("%d", i);
            canvas.drawText(text, xp, yp, paintSmallTextWhite);
        }

        // draw dial label
        canvas.drawText("kph", -0.9f, 0.9f, paintSmallTextBlue);

        /*********************************************************************/
        /** Rpm dial **/
        /*********************************************************************/

        float rpm = (float) mainact.m_ObdData.m_EngineRpm;
        canvas.setMatrix(null);
        canvas.translate(xc, 3 * yc / 2 - 20);
        canvas.scale(rad, rad);
        area.set(-1, -1, 1, 1);

        // draw rpm pie
        canvas.drawArc(area, 0f, 225.0f * rpm / 5000.0f, true, paintPieNormal);

        // draw speed text
        text = String.format("%.0f", rpm);
        canvas.drawText(text, 0.9f, -0.2f, paintLargeTextWhite);

        // draw optimum gear
        myPaint = paintLargeTextBlue;
        if (mainact.m_ObdData.m_OptimumGear != mainact.m_ObdData.m_CurrentGear)
        {
            myPaint = paintLargeTextAmber;
        }
        text = mainact.m_ObdData.m_GearString.get(mainact.m_ObdData.m_OptimumGear);
        canvas.drawText(text, 0.9f, -0.6f, myPaint);

        // draw dial contour
        canvas.drawArc(area, 0f, 225f, true, paintLinesWhite);

        // draw text border
        canvas.drawRect(0.1f, -0.5f, 1.0f, -0.1f, paintLinesWhite);

        // draw rpm marker ticks
        canvas.save();
        for (int i = 1000; i < 5000; i += 1000)
        {
            canvas.rotate(225 / 5.0f);
            canvas.drawLine(0.8f, 0, 1.0f, 0, paintLinesWhite);
        }
        canvas.restore();

        // draw rpm marker numbers
        for (int i = 1; i <= 4; i += 1)
        {
            float xp = (float) (0.7 * Math.cos(i * 225 / 5.0 * Math.PI / 180.0));
            float yp = (float) (0.7 * Math.sin(i * 225 / 5.0 * Math.PI / 180.0));
            text = String.format("%d", i);
            canvas.drawText(text, xp, yp, paintSmallTextWhite);
        }

        // draw dial label
        canvas.drawText("rpm", -0.9f, 0.9f, paintSmallTextBlue);
    }
}
