package preferences;

import nl.laaksoft.obd.reader.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SeekBarPreference extends Preference implements OnSeekBarChangeListener
{
    private TextView tvValue;
    private final int DEFAULT_SEEKBAR_POSITION = 0;

    public SeekBarPreference(Context context)
    {
        super(context);
    }

    public SeekBarPreference(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public SeekBarPreference(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onBindView(View view)
    {
        tvValue = (TextView) view.findViewById(R.id.tvSbPref);
        SeekBar sbValue = (SeekBar) view.findViewById(R.id.sbSbPref);

        sbValue.setOnSeekBarChangeListener(this);

        SharedPreferences sp = getSharedPreferences();
        int value = sp.getInt(getKey(), DEFAULT_SEEKBAR_POSITION);
        sbValue.setProgress(value);
        tvValue.setText(Integer.valueOf(value).toString());

        super.onBindView(view);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
    {
        String tmp = Integer.valueOf(progress).toString();
        tvValue.setText(tmp);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar)
    {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar)
    {
        tvValue.setText(Integer.valueOf(seekBar.getProgress()).toString());
        persistInt(seekBar.getProgress());
    }
}
