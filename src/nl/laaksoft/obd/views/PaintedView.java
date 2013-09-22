package nl.laaksoft.obd.views;

import java.util.Locale;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.view.View;

public class PaintedView extends View {
	protected Locale mLocale;

	protected Paint paintLinesWhite;
	protected Paint paintLinesWarning;
	protected Paint paintLinesDanger;
	protected Paint paintLinesGreenThick;

	protected Paint paintPieNormal;
	protected Paint paintPieWarning;
	protected Paint paintPieDanger;
	protected Paint paintBlackBrush;
	protected Paint paintGrayBrush;

	protected Paint paintLargeTextWhite;
	protected Paint paintLargeTextAmber;
	protected Paint paintLargeTextRed;
	protected Paint paintLargeTextBlue;
	protected Paint paintLargeTextBlueL;

	protected Paint paintSmallTextWhite;
	protected Paint paintSmallTextBlue;
	protected Paint paintSmallTextGreen;

	public PaintedView(Context context) {
		super(context);
		Init(context);
	}

	public PaintedView(Context context, AttributeSet attrs) {
		super(context, attrs);
		Init(context);
	}

	public PaintedView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		Init(context);
	}

	private void Init(Context context) {
		mLocale = getResources().getConfiguration().locale;
				
		paintLinesWhite = new Paint();
		paintLinesWhite.setAntiAlias(true);
		paintLinesWhite.setStyle(Paint.Style.STROKE);
		paintLinesWhite.setColor(Color.WHITE);
		paintLinesWhite.setStrokeWidth(1);
		paintLinesWhite.setStrokeCap(Paint.Cap.ROUND);

		paintLinesWarning = new Paint();
		paintLinesWarning.setAntiAlias(true);
		paintLinesWarning.setStyle(Paint.Style.STROKE);
		paintLinesWarning.setColor(Color.rgb(192, 160, 0));
		paintLinesWarning.setStrokeWidth(1);
		paintLinesWarning.setStrokeCap(Paint.Cap.ROUND);

		paintLinesDanger = new Paint();
		paintLinesDanger.setAntiAlias(true);
		paintLinesDanger.setStyle(Paint.Style.STROKE);
		paintLinesDanger.setColor(Color.rgb(192, 0, 0));
		paintLinesDanger.setStrokeWidth(1);
		paintLinesDanger.setStrokeCap(Paint.Cap.ROUND);

		paintLinesGreenThick = new Paint();
		paintLinesGreenThick.setAntiAlias(true);
		paintLinesGreenThick.setStyle(Paint.Style.STROKE);
		paintLinesGreenThick.setColor(Color.rgb(64, 192, 64));
		paintLinesGreenThick.setStrokeWidth(1);
		paintLinesGreenThick.setStrokeCap(Paint.Cap.ROUND);

		paintPieNormal = new Paint();
		paintPieNormal.setAntiAlias(true);
		paintPieNormal.setStyle(Paint.Style.FILL);
		paintPieNormal.setColor(Color.rgb(128, 128, 140));

		paintPieWarning = new Paint();
		paintPieWarning.setAntiAlias(true);
		paintPieWarning.setStyle(Paint.Style.FILL);
		paintPieWarning.setColor(Color.rgb(192, 160, 0));

		paintPieDanger = new Paint();
		paintPieDanger.setAntiAlias(true);
		paintPieDanger.setStyle(Paint.Style.FILL);
		paintPieDanger.setColor(Color.rgb(192, 0, 0));

		paintBlackBrush = new Paint();
		paintBlackBrush.setAntiAlias(true);
		paintBlackBrush.setStyle(Paint.Style.FILL);
		paintBlackBrush.setColor(Color.rgb(0, 0, 0));
		
		paintGrayBrush = new Paint();
		paintGrayBrush.setAntiAlias(true);
		paintGrayBrush.setStyle(Paint.Style.FILL);
		paintGrayBrush.setColor(Color.rgb(64, 64, 92));

		paintLargeTextWhite = new Paint();
		paintLargeTextWhite.setAntiAlias(true);
		paintLargeTextWhite.setTextSize(0.3f);
		paintLargeTextWhite.setTextAlign(Align.RIGHT);
		paintLargeTextWhite.setStyle(Paint.Style.FILL);
		paintLargeTextWhite.setColor(Color.WHITE);
		paintLargeTextWhite.setTextSize(10);

		paintLargeTextAmber = new Paint();
		paintLargeTextAmber.setAntiAlias(true);
		paintLargeTextAmber.setTextSize(0.3f);
		paintLargeTextAmber.setTextAlign(Align.RIGHT);
		paintLargeTextAmber.setStyle(Paint.Style.FILL);
		paintLargeTextAmber.setColor(Color.rgb(192, 160, 0));
		paintLargeTextAmber.setTextSize(10);

		paintLargeTextRed = new Paint();
		paintLargeTextRed.setAntiAlias(true);
		paintLargeTextRed.setTextSize(0.3f);
		paintLargeTextRed.setTextAlign(Align.RIGHT);
		paintLargeTextRed.setStyle(Paint.Style.FILL);
		paintLargeTextRed.setColor(Color.rgb(192, 0, 0));
		paintLargeTextRed.setTextSize(10);

		paintSmallTextWhite = new Paint();
		paintSmallTextWhite.setAntiAlias(true);
		paintSmallTextWhite.setTextSize(0.2f);
		paintSmallTextWhite.setTextAlign(Align.CENTER);
		paintSmallTextWhite.setStyle(Paint.Style.FILL);
		paintSmallTextWhite.setColor(Color.WHITE);
		paintSmallTextWhite.setTextSize(10);

		paintSmallTextBlue = new Paint();
		paintSmallTextBlue.setAntiAlias(true);
		paintSmallTextBlue.setTextSize(0.2f);
		paintSmallTextBlue.setTextAlign(Align.CENTER);
		paintSmallTextBlue.setStyle(Paint.Style.FILL);
		paintSmallTextBlue.setColor(Color.rgb(64, 160, 255));
		paintSmallTextBlue.setTextSize(10);

		paintSmallTextGreen = new Paint();
		paintSmallTextGreen.setAntiAlias(true);
		paintSmallTextGreen.setTextSize(0.3f);
		paintSmallTextGreen.setTextAlign(Align.RIGHT);
		paintSmallTextGreen.setStyle(Paint.Style.FILL);
		paintSmallTextGreen.setColor(Color.rgb(64, 192, 64));
		paintSmallTextGreen.setTextSize(10);

		paintLargeTextBlue = new Paint();
		paintLargeTextBlue.setAntiAlias(true);
		paintLargeTextBlue.setTextSize(0.3f);
		paintLargeTextBlue.setTextAlign(Align.RIGHT);
		paintLargeTextBlue.setStyle(Paint.Style.FILL);
		paintLargeTextBlue.setColor(Color.rgb(64, 160, 255));
		paintLargeTextBlue.setTextSize(10);

		paintLargeTextBlueL = new Paint();
		paintLargeTextBlueL.setAntiAlias(true);
		paintLargeTextBlueL.setTextSize(40);
		paintLargeTextBlueL.setTextAlign(Align.LEFT);
		paintLargeTextBlueL.setStyle(Paint.Style.FILL);
		paintLargeTextBlueL.setColor(Color.rgb(64, 160, 255));
		paintLargeTextBlueL.setTextSize(10);
	}
}
