package nl.laaksoft.obd.views;

import nl.laaksoft.obd.VehicleData;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

public class FlowView extends DialView {
	private VehicleData mObdData;
	private RectF mArea;

	public FlowView(Context context) {
		super(context);
	}

	public FlowView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FlowView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setmOdbData(VehicleData mOdbData) {
		this.mObdData = mOdbData;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (mObdData == null) {
			mObdData = new VehicleData();
		}
		if (mArea == null) {
			mArea = new RectF();
		}

		drawFlowDial(canvas);
	}

	private void drawFlowDial(Canvas canvas) {
		canvas.drawRect(0, 0, getWidth(), getHeight(), paintBlackBrush);

		/*********************************************************************/
		/** flow dial **/
		/*********************************************************************/
		String text;
		Paint myPaint;
		float xc = getWidth() / 2.0f;
		float yc = getHeight() / 2.0f;
		float rad = mRadius;

		float load = (float) mObdData.m_EngineLoad;

		// draw load pie
		myPaint = paintPieNormal;
		if (load >= 98)
			myPaint = paintPieDanger;
		else if (load >= 90)
			myPaint = paintPieWarning;
		mArea.set(xc - rad, yc - rad, xc + rad, yc + rad);
		canvas.drawArc(mArea, 0f, 225.0f * load / 100.0f, true, myPaint);

		// draw load text
		text = String.format(mLocale, "%.0f", load);
		canvas.drawText(text, xc + 0.9f * rad, yc - 0.2f * rad,
				paintLargeTextWhite);

		// draw load marker ticks
		for (int i = 0; i <= 100; i += 20) {
			double ang = (i / 100.0 * 225.0) * Math.PI / 180.0;
			canvas.drawLine(
					//
					(float) (xc + 0.85 * rad * Math.cos(ang)),
					(float) (yc + 0.85 * rad * Math.sin(ang)),
					(float) (xc + 1.00 * rad * Math.cos(ang)),
					(float) (yc + 1.00 * rad * Math.sin(ang)), paintLinesWhite);
		}

		// draw dial contour
		canvas.drawArc(mArea, 0f, 225f * 90.0f / 100.0f, false, paintLinesWhite);
		canvas.drawArc(mArea, 225f * 90.0f / 100.0f, 225f * 9.0f / 100.0f,
				false, paintLinesWarning);
		canvas.drawArc(mArea, 225f * 99.0f / 100.0f, 225f * 1.0f / 100.0f,
				false, paintLinesDanger);

		{
			double ang = (90.0 / 100.0 * 225.0) * Math.PI / 180.0;
			canvas.drawLine(
					//
					(float) (xc + 1.00 * rad * Math.cos(ang)),
					(float) (yc + 1.00 * rad * Math.sin(ang)),
					(float) (xc + 1.15 * rad * Math.cos(ang)),
					(float) (yc + 1.15 * rad * Math.sin(ang)),
					paintLinesWarning);
		}

		{
			double ang = (99.0 / 100.0 * 225.0) * Math.PI / 180.0;
			canvas.drawLine(
					//
					(float) (xc + 1.00 * rad * Math.cos(ang)),
					(float) (yc + 1.00 * rad * Math.sin(ang)),
					(float) (xc + 1.15 * rad * Math.cos(ang)),
					(float) (yc + 1.15 * rad * Math.sin(ang)), paintLinesDanger);
		}

		// draw text border
		canvas.drawRect( //
				xc + 0.1f * rad, yc - 0.5f * rad, xc + 1.0f * rad, yc - 0.1f
						* rad, paintLinesWhite);

		// draw load marker numbers
		for (int i = 0; i <= 100; i += 20) {
			float xp = (float) (xc + 0.70 * rad
					* Math.cos(i * 225 / 100.0 * Math.PI / 180.0));
			float yp = rad
					* 0.07f
					+ (float) (yc + 0.70 * rad
							* Math.sin(i * 225 / 100.0 * Math.PI / 180.0));

			text = String.format(mLocale, "%d", i);
			canvas.drawText(text, xp, yp, paintSmallTextWhite);
		}

		// draw dial label
		canvas.drawText("load", xc - 1.1f * rad, yc + 0.95f * rad,
				paintSmallTextBlue);
	}
}
