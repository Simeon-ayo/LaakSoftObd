package nl.laaksoft.obd.reader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class ObdView extends View
{
    private Paint paintLines;
    private Rect bounds;
    private RectF area;
    private Paint paintSmallText;
    private Paint paintPie;
    private Paint paintLargeText;
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
        paintLines = new Paint();
        paintLargeText = new Paint();
        paintSmallText = new Paint();
        paintPie = new Paint();
        paintPieWarning = new Paint();
        paintPieDanger = new Paint();
        bounds = new Rect();
        area = new RectF();

        paintLines.setAntiAlias(true);
        paintLines.setStyle(Paint.Style.STROKE);
        paintLines.setColor(Color.WHITE);
        paintLines.setStrokeWidth(0.02f);

        paintPie.setAntiAlias(true);
        paintPie.setStyle(Paint.Style.FILL);
        paintPie.setColor(Color.rgb(0, 160, 0));

        paintPieWarning.setAntiAlias(true);
        paintPieWarning.setStyle(Paint.Style.FILL);
        paintPieWarning.setColor(Color.rgb(160, 160, 0));

        paintPieDanger.setAntiAlias(true);
        paintPieDanger.setStyle(Paint.Style.FILL);
        paintPieDanger.setColor(Color.rgb(160, 0, 0));

        paintLargeText.setAntiAlias(true);
        paintLargeText.setTextSize(0.3f);
        paintLargeText.setTextAlign(Align.RIGHT);
        paintLargeText.setStyle(Paint.Style.FILL);
        paintLargeText.setColor(Color.WHITE);

        paintSmallText.setAntiAlias(true);
        paintSmallText.setTextSize(0.2f);
        paintSmallText.setTextAlign(Align.CENTER);
        paintSmallText.setStyle(Paint.Style.FILL);
        paintSmallText.setColor(Color.WHITE);
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

        /**************************************/
        // draw speed dial
        canvas.setMatrix(null);
        canvas.translate(xc, yc / 2);
        canvas.scale(rad, rad);
        area.set(-1, -1, 1, 1);

        // draw speed pie
        float speed = (float) mainact.m_ObdData.m_VehicleSpeed;
        canvas.drawArc(area, 0f, 225.0f * speed / 140.0f, true, paintPie);

        // draw speed text
        text = String.format("%.0f", speed);
        canvas.drawText(text, 0.8f, -0.2f, paintLargeText);

        // draw boilerplate
        // draw dial
        canvas.drawArc(area, 0f, 225f, true, paintLines);

        // draw text border
        canvas.drawRect(0.1f, -0.5f, 1.0f, -0.1f, paintLines);

        // draw markers
        canvas.save();
        for (int i = 10; i < 140; i += 20)
        {
            canvas.rotate(225 / 7.0f);
            canvas.drawLine(0.8f, 0, 1.0f, 0, paintLines);
        }
        canvas.restore();

        for (int i = 2; i <= 12; i += 2)
        {
            float xp = (float) (0.7 * Math.cos(i * 225 / 14.0 * Math.PI / 180.0));
            float yp = (float) (0.7 * Math.sin(i * 225 / 14.0 * Math.PI / 180.0));
            text = String.format("%d", i);
            canvas.drawText(text, xp, yp, paintSmallText);
        }

        /**************************************/
        // draw rpm dial
        canvas.setMatrix(null);
        canvas.translate(xc, 3 * yc / 2);
        canvas.scale(rad, rad);
        area.set(-1, -1, 1, 1);

        float rpm = (float) mainact.m_ObdData.m_EngineRpm;
        Paint myPaint = paintPie;
        if (rpm > 3000 || rpm < 1200)
            myPaint = paintPieDanger;
        else if (rpm > 2500 || rpm < 1500)
            myPaint = paintPieWarning;
        else
            myPaint = paintPie;
        canvas.drawArc(area, 0f, 225.0f * rpm / 5000.0f, true, myPaint);

        // draw speed text
        text = String.format("%.0f", rpm);
        canvas.drawText(text, 0.9f, -0.2f, paintLargeText);

        // draw boilerplate

        // draw dial
        canvas.drawArc(area, 0f, 225f, true, paintLines);

        // draw text border
        canvas.drawRect(0.1f, -0.5f, 1.0f, -0.1f, paintLines);

        // draw markers
        canvas.save();
        for (int i = 1000; i < 5000; i += 1000)
        {
            canvas.rotate(225 / 5.0f);
            canvas.drawLine(0.8f, 0, 1.0f, 0, paintLines);
        }
        canvas.restore();

        for (int i = 1; i <= 4; i += 1)
        {
            float xp = (float) (0.7 * Math.cos(i * 225 / 5.0 * Math.PI / 180.0));
            float yp = (float) (0.7 * Math.sin(i * 225 / 5.0 * Math.PI / 180.0));
            text = String.format("%d", i);
            canvas.drawText(text, xp, yp, paintSmallText);
        }
    }
}
