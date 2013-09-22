package nl.laaksoft.obd.views;

import nl.laaksoft.obd.VehicleData;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;

public class SpeedView extends BarView {
	private VehicleData mObdData;
	private RectF mArea;

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
		this.mObdData = mOdbData;
		invalidate();
	}

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
		/** speed bar **/
		/*********************************************************************/
		float xc = getWidth() / 2.0f;
		float yc = getHeight() / 2.0f;
		float h = yc * 0.8f;
		float w = xc * 0.8f;

		mArea.top = yc - h;
		mArea.bottom = yc + h;
		mArea.left = xc - w;
		mArea.right = xc + w;

		canvas.drawRect(mArea, paintGrayBrush);

	}
}
