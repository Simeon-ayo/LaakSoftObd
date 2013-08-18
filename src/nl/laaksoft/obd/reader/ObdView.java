package nl.laaksoft.obd.reader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class ObdView extends View
{
    private Paint myPaint;
    private Rect bounds;
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
        myPaint = new Paint();
        bounds = new Rect();
        area = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        MainActivity mainact = (MainActivity) getContext();
        String text;

        myPaint.setStrokeWidth(1);
        myPaint.setAntiAlias(true);
        myPaint.setTextSize(25);

        canvas.getClipBounds(bounds);
        int xc = bounds.centerX();
        int yc = bounds.centerY();
        int rad = (int) (Math.min(xc, yc) * 0.9);

        /* draw speed */
        area.set(0.1f * rad, 0.1f * rad, 0.9f * rad, 0.9f * rad);

        myPaint.setStyle(Paint.Style.FILL);
        myPaint.setColor(Color.GRAY);
        float speed = (float) mainact.m_ObdData.m_VehicleSpeed;
        canvas.drawArc(area, 0f, 225.0f * speed / 140.0f, true, myPaint);

        myPaint.setStrokeWidth(5);
        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setColor(Color.WHITE);
        canvas.drawArc(area, 0f, 225f, true, myPaint);

        myPaint.setStyle(Paint.Style.FILL);
        text = String.format("%.0f kph", speed);
        canvas.drawText(text, 0.5f * rad, 0.4f * rad, myPaint);

        /* draw rpm */
        area.set(1.1f * rad, 0.1f * rad, 1.9f * rad, 0.9f * rad);

        myPaint.setStyle(Paint.Style.FILL);
        myPaint.setColor(Color.GRAY);
        float rpm = (float) mainact.m_ObdData.m_EngineRpm;
        canvas.drawArc(area, 0f, 225.0f * rpm / 5000.0f, true, myPaint);

        myPaint.setStrokeWidth(5);
        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setColor(Color.WHITE);
        canvas.drawArc(area, 0f, 225f, true, myPaint);

        myPaint.setStyle(Paint.Style.FILL);
        text = String.format("%.0f rpm", rpm);
        canvas.drawText(text, 1.5f * rad, 0.4f * rad, myPaint);

        /* draw engine load */
        area.set(0.1f * rad, 1.1f * rad, 0.9f * rad, 1.9f * rad);

        myPaint.setStyle(Paint.Style.FILL);
        myPaint.setColor(Color.GRAY);
        float load = (float) mainact.m_ObdData.m_EngineLoad;
        canvas.drawArc(area, 0f, 225.0f * load / 100.0f, true, myPaint);

        myPaint.setStrokeWidth(5);
        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setColor(Color.WHITE);
        canvas.drawArc(area, 0f, 225f, true, myPaint);

        myPaint.setStyle(Paint.Style.FILL);
        text = String.format("%.0f %%", load);
        canvas.drawText(text, 0.5f * rad, 1.4f * rad, myPaint);

        /* draw cooling */
        area.set(1.1f * rad, 1.1f * rad, 1.9f * rad, 1.9f * rad);

        myPaint.setStyle(Paint.Style.FILL);
        myPaint.setColor(Color.GRAY);
        float temp = (float) mainact.m_ObdData.m_CoolingTemperature;
        canvas.drawArc(area, 0f, 225.0f * temp / 100.0f, true, myPaint);

        myPaint.setStrokeWidth(5);
        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setColor(Color.WHITE);
        canvas.drawArc(area, 0f, 225f, true, myPaint);

        myPaint.setStyle(Paint.Style.FILL);
        text = String.format("%.0f C", temp);
        canvas.drawText(text, 1.5f * rad, 1.4f * rad, myPaint);

        /* draw rail pressure */
        area.set(0.1f * rad, 2.1f * rad, 0.9f * rad, 2.9f * rad);

        myPaint.setStyle(Paint.Style.FILL);
        myPaint.setColor(Color.GRAY);
        float railp = (float) mainact.m_ObdData.m_RailPressure;
        canvas.drawArc(area, 0f, 225.0f * railp / 600000.0f, true, myPaint);

        myPaint.setStrokeWidth(5);
        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setColor(Color.WHITE);
        canvas.drawArc(area, 0f, 225f, true, myPaint);

        myPaint.setStyle(Paint.Style.FILL);
        text = String.format("%.1f MPa", railp / 1000.0);
        canvas.drawText(text, 0.5f * rad, 2.4f * rad, myPaint);

        /* draw maf rate */
        area.set(1.1f * rad, 2.1f * rad, 1.9f * rad, 2.9f * rad);

        myPaint.setStyle(Paint.Style.FILL);
        myPaint.setColor(Color.GRAY);
        float mafr = (float) mainact.m_ObdData.m_MafRate;
        canvas.drawArc(area, 0f, 225.0f * mafr / 655.0f, true, myPaint);

        myPaint.setStrokeWidth(5);
        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setColor(Color.WHITE);
        canvas.drawArc(area, 0f, 225f, true, myPaint);

        myPaint.setStyle(Paint.Style.FILL);
        text = String.format("%.0f gps", mafr);
        canvas.drawText(text, 1.5f * rad, 2.4f * rad, myPaint);
    }
}
