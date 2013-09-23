package nl.laaksoft.obd.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

public class DialView extends PaintedView {
	private static final String TAG = "OBD";
	protected int m_Radius;

	public DialView(Context context) {
		super(context);
	}

	public DialView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DialView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		Log.d(TAG, "Size changed");

		int size = Math.min((int) (w / 1.3), h);
		m_Radius = (int) (size * 0.45);

		int lineWidth = 2;
		int textSizeNormal = 15;
		int textSizeLarge = 30;
		float scale = size / 200f;

		paintLinesWhite.setStrokeWidth(lineWidth * scale);
		paintLinesWarning.setStrokeWidth(lineWidth * scale);
		paintLinesDanger.setStrokeWidth(lineWidth * scale);
		paintLinesGreenThick.setStrokeWidth(lineWidth * scale);

		paintSmallTextWhite.setTextSize(textSizeNormal * scale);
		paintSmallTextBlue.setTextSize(textSizeNormal * scale);
		paintSmallTextGreen.setTextSize(textSizeNormal * scale);

		paintLargeTextWhite.setTextSize(textSizeLarge * scale);
		paintLargeTextAmber.setTextSize(textSizeLarge * scale);
		paintLargeTextRed.setTextSize(textSizeLarge * scale);
		paintLargeTextBlue.setTextSize(textSizeLarge * scale);
		paintLargeTextBlueL.setTextSize(textSizeLarge * scale);
	}
}
