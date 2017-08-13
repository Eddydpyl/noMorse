package dpyl.eddy.nomorse.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import dpyl.eddy.nomorse.R;

public class WhatsAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whats_app);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getFragmentManager().beginTransaction().replace(R.id.content, new MyPreferenceFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_help, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_help) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.content_help_whats_app_message).setTitle(R.string.content_help_whats_app_title);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            }); builder.create().show();
            return true;
        } return super.onOptionsItemSelected(item);
    }

    public static class MyPreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.whats_app_preferences);
            for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); i++) {
                PreferenceCategory preferenceCategory = (PreferenceCategory) getPreferenceScreen().getPreference(i);
                for (int j = 0; j < preferenceCategory.getPreferenceCount(); j++) {
                    Preference preference = preferenceCategory.getPreference(j);
                    setPreferenceSummary(preference);
                }
            }
        }

        private void setPreferenceSummary(Preference preference){
            if (preference instanceof RingtonePreference) {
                final RingtonePreference ringtonePreference = (RingtonePreference) preference;
                preference.setSummary(getRingtoneTitle(null, ringtonePreference));
                ringtonePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object o) {
                        preference.setSummary(getRingtoneTitle((String) o, ringtonePreference));
                        return true;
                    }
                });
            } else if (preference instanceof ListPreference) {
                final ListPreference listPreference = (ListPreference) preference;
                final String[] entries = getResources().getStringArray(R.array.whats_app_vibration_entries);
                preference.setSummary(listPreference.getEntry());
                listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object o) {
                        preference.setSummary(entries[Integer.valueOf((String) o)]);
                        return true;
                    }
                });
            }
        }

        private String getRingtoneTitle(String s, RingtonePreference ringtonePreference){
            Context context = getActivity();
            if (s == null) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                s = sharedPreferences.getString(ringtonePreference.getKey(), null);
            }
            if (s != null && s.isEmpty()){
                return context.getString(R.string.content_notification_none);
            }
            Uri uri = s == null ? RingtoneManager.getDefaultUri(ringtonePreference.getRingtoneType()) : Uri.parse(s);
            Ringtone ringtone = RingtoneManager.getRingtone(context, uri);
            return ringtone.getTitle(context);
        }
    }
}
