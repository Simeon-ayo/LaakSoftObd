package nl.laaksoft.obd.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

public class BarView extends PaintedView {
	private static final String TAG = "OBD";

	public BarView(Context context) {
		super(context);
	}

	public BarView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		Log.d(TAG, "Size changed");

		int size = Math.max(w, h);

		int lineWidth = 2;
		int textSizeNormal = 15;
		int textSizeMedium = 22;
		int textSizeLarge = 30;
		float scale = size / 600f;

		paintLinesWhite.setStrokeWidth(lineWidth * scale);
		paintLinesWarning.setStrokeWidth(lineWidth * scale);
		paintLinesDanger.setStrokeWidth(lineWidth * scale);
		paintLinesGreenThick.setStrokeWidth(lineWidth * scale);

		paintSmallTextWhite.setTextSize(textSizeNormal * scale);
		paintSmallTextBlue.setTextSize(textSizeNormal * scale);
		paintSmallTextGreen.setTextSize(textSizeNormal * scale);

		paintMediumTextWhite.setTextSize(textSizeMedium * scale);
		paintLargeTextWhite.setTextSize(textSizeLarge * scale);
		paintLargeTextAmber.setTextSize(textSizeLarge * scale);
		paintLargeTextRed.setTextSize(textSizeLarge * scale);
		paintLargeTextBlue.setTextSize(textSizeLarge * scale);
		paintLargeTextBlueL.setTextSize(textSizeLarge * scale);
	}
}
