package preferences;

import nl.laaksoft.obd.reader.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class MainPrefsActivity extends PreferenceActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.mainpreferences);
    }
}
