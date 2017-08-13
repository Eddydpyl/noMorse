package dpyl.eddy.nomorse.view;

import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.preference.RingtonePreference;
import android.util.AttributeSet;

// Simple hack that uses the default ringtone for RingtonePreference when showDefault == true && ringtoneType == all

public class WhatsAppRingtonePreference extends RingtonePreference {

    public WhatsAppRingtonePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WhatsAppRingtonePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WhatsAppRingtonePreference(Context context) {
        super(context);
    }

    protected void onPrepareRingtonePickerIntent(Intent ringtonePickerIntent) {
        ringtonePickerIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, onRestoreRingtone());
        ringtonePickerIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, getShowDefault());
        if (getShowDefault() && getRingtoneType() == RingtoneManager.TYPE_ALL)
            ringtonePickerIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        ringtonePickerIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, getShowSilent());
        ringtonePickerIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, getRingtoneType());
        ringtonePickerIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, getTitle());
    }
}
