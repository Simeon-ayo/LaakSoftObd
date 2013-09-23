package nl.laaksoft.obd.views;

import nl.laaksoft.obd.connection.VehicleData;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

public class RpmView extends DialView {
	private VehicleData m_ObdData;
	private RectF m_Area;

	public RpmView(Context context) {
		super(context);
	}

	public RpmView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RpmView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setmOdbData(VehicleData mOdbData) {
		this.m_ObdData = mOdbData;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {

		if (m_ObdData == null) {
			m_ObdData = new VehicleData();
		}
		if (m_Area == null) {
			m_Area = new RectF();
		}

		drawRpmDial(canvas);
	}

	private void drawRpmDial(Canvas canvas) {
		canvas.drawRect(0, 0, getWidth(), getHeight(), paintBlackBrush);

		/*********************************************************************/
		/** Rpm dial **/
		/*********************************************************************/
		String text;
		Paint myPaint;
		float xc = getWidth() / 2.0f;
		float yc = getHeight() / 2.0f;
		float rad = m_Radius;

		float rpm = (float) m_ObdData.m_EngineRpm;
		float maxrpm = (float) (double) (m_ObdData.m_GearMaxRpm
				.get(m_ObdData.m_CurrentGear));

		// draw rpm pie
		myPaint = paintPieNormal;
		if (rpm > 3500)
			myPaint = paintPieDanger;
		else if (rpm > maxrpm)
			myPaint = paintPieWarning;
		m_Area.set(xc - rad, yc - rad, xc + rad, yc + rad);
		canvas.drawArc(m_Area, 0f, 225.0f * rpm / 5000.0f, true, myPaint);

		// draw speed text
		text = String.format(m_Locale, "%.0f", rpm);
		canvas.drawText(text, xc + 0.9f * rad, yc - 0.2f * rad,
				paintLargeTextWhite);

		// draw optimum gear
		myPaint = paintLargeTextBlue;
		if (m_ObdData.m_OptimumGear != m_ObdData.m_CurrentGear) {
			myPaint = paintLargeTextAmber;
		}
		text = m_ObdData.m_GearString.get(m_ObdData.m_OptimumGear);
		canvas.drawText(text, xc + 0.9f * rad, yc - 0.6f * rad, myPaint);

		// draw rpm marker ticks
		for (int i = 1000; i <= 5000; i += 1000) {
			double ang = (i / 5000.0 * 225.0) * Math.PI / 180.0;
			canvas.drawLine(
					//
					(float) (xc + 0.85 * rad * Math.cos(ang)),
					(float) (yc + 0.85 * rad * Math.sin(ang)),
					(float) (xc + 1.00 * rad * Math.cos(ang)),
					(float) (yc + 1.00 * rad * Math.sin(ang)), paintLinesWhite);
		}

		// draw dial contour
		canvas.drawArc(m_Area, 0f, 225f * maxrpm / 5000f, false, paintLinesWhite);
		canvas.drawArc(m_Area, 225f * maxrpm / 5000f,
				225f * (3500 - maxrpm) / 5000f, false, paintLinesWarning);
		canvas.drawArc(m_Area, 225f * 3500f / 5000f, 225f * 1500f / 5000f,
				false, paintLinesDanger);

		{
			double ang = (maxrpm / 5000.0 * 225.0) * Math.PI / 180.0;
			canvas.drawLine(
					//
					(float) (xc + 1.00 * rad * Math.cos(ang)),
					(float) (yc + 1.00 * rad * Math.sin(ang)),
					(float) (xc + 1.15 * rad * Math.cos(ang)),
					(float) (yc + 1.15 * rad * Math.sin(ang)),
					paintLinesWarning);
		}

		{
			double ang = (3500.0 / 5000.0 * 225.0) * Math.PI / 180.0;
			canvas.drawLine(
					//
					(float) (xc + 1.00 * rad * Math.cos(ang)),
					(float) (yc + 1.00 * rad * Math.sin(ang)),
					(float) (xc + 1.15 * rad * Math.cos(ang)),
					(float) (yc + 1.15 * rad * Math.sin(ang)), paintLinesDanger);
		}

		// draw text border
		canvas.drawRect(xc + 0.1f * rad, yc - 0.5f * rad, xc + 1.0f * rad, yc
				- 0.1f * rad, paintLinesWhite);

		// draw rpm marker numbers
		for (int i = 1; i <= 5; i += 1) {
			float xp = (float) (xc + 0.7 * rad
					* Math.cos(i * 225 / 5.0 * Math.PI / 180.0));
			float yp = rad
					* 0.07f
					+ (float) (yc + 0.7 * rad
							* Math.sin(i * 225 / 5.0 * Math.PI / 180.0));
			text = String.format(m_Locale, "%d", i);
			canvas.drawText(text, xp, yp, paintSmallTextWhite);
		}

		// draw dial label
		canvas.drawText("rpm", xc - 1.1f * rad, yc + 0.95f * rad,
				paintSmallTextBlue);
	}
}
