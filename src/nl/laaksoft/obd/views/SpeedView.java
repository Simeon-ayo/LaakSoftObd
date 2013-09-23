package nl.laaksoft.obd.views;

import nl.laaksoft.obd.connection.VehicleData;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;

public class SpeedView extends BarView {
	private VehicleData m_ObdData;
	private RectF m_Area;

	public SpeedView(Context context) {
		super(context);
	}

	public SpeedView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SpeedView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setmOdbData(VehicleData mOdbData) {
		this.m_ObdData = mOdbData;
		invalidate();
	}

	protected void onDraw(Canvas canvas) {
		if (m_ObdData == null) {
			m_ObdData = new VehicleData();
		}
		if (m_Area == null) {
			m_Area = new RectF();
		}

		drawSpeedBar(canvas);
	}

	private void drawSpeedBar(Canvas canvas) {
		canvas.drawRect(0, 0, getWidth(), getHeight(), paintBlackBrush);

		float speed = (float) m_ObdData.m_VehicleSpeed;

		/*********************************************************************/
		/** speed bar **/
		/*********************************************************************/
		float xc = getWidth() / 2.0f;
		float yc = getHeight() / 2.0f;
		float h = yc * 0.8f;
		float w = xc * 0.8f;

		m_Area.top = yc - h;
		m_Area.bottom = yc + h;
		m_Area.left = xc - w;
		m_Area.right = xc + xc;

		canvas.drawRect(m_Area, paintGrayBrush);

		canvas.save();
		canvas.clipRect(m_Area);

		for (int i = 0; i <= 140; i += 10) {
			float relvel = i - speed;

			String text = String.format(m_Locale, "%d", i);

			canvas.drawText(text, xc + w * 0.8f, yc - relvel * 10f + 10,
					paintMediumTextWhite);

			canvas.drawLine(xc + w, yc - relvel * 10f, xc + xc, yc - relvel
					* 10f, paintLinesWhite);
		}

		canvas.restore();

		m_Area.top = yc - w * 0.8f;
		m_Area.bottom = yc + w * 0.8f;
		m_Area.left = xc - w;
		m_Area.right = xc + w;

		canvas.drawLine(xc + w, yc, xc + xc, yc, paintLinesWhite);
		canvas.drawRect(m_Area, paintBlackBrush);
		canvas.drawRect(m_Area, paintLinesWhite);
		String text = String.format(m_Locale, "%.0f", speed);
		canvas.drawText(text, xc + w * 0.8f, yc + 15, paintLargeTextWhite);

	}
}
