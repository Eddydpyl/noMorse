package dpyl.eddy.nomorse.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import java.io.IOException;

import dpyl.eddy.nomorse.Constants;
import dpyl.eddy.nomorse.R;
import dpyl.eddy.nomorse.Utility;
import dpyl.eddy.nomorse.model.WhatsAppNotification;

public class MessageListenerService extends NotificationListenerService {

    private WhatsAppController whatsAppController;
    private MediaPlayer mediaPlayer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        whatsAppController = WhatsAppController.init();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null) mediaPlayer.release();
        mediaPlayer = null;
        super.onDestroy();
    }

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
    }

    @Override
    public void onListenerDisconnected() {
        super.onListenerDisconnected();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if (sbn.getPackageName().equals(Constants.WHATSAPP_PACKAGE)) {
            // Notification comes from WhatsApp
            if(Utility.isAppInstalled(this, Constants.WHATSAPP_PACKAGE)){
                if (sbn.getTag()!= null) {
                    WhatsAppNotification wan = new WhatsAppNotification(sbn.getTag(), sbn.getPostTime());
                    if (whatsAppController.notify(wan)) {
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                        if (whatsAppController.isGroup(wan)) {
                            String ringtone = sharedPreferences.getString(getString(R.string.pref_whats_app_group_notification_tone), Settings.System.DEFAULT_RINGTONE_URI.toString());
                            if (!ringtone.isEmpty()) {
                                try {
                                    playSound(Uri.parse(ringtone));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                int mode = sharedPreferences.getInt(getString(R.string.pref_whats_app_group_vibrate), 1);
                                vibrate(mode);
                            }
                        } else {
                            String ringtone = sharedPreferences.getString(getString(R.string.pref_whats_app_message_notification_tone), Settings.System.DEFAULT_RINGTONE_URI.toString());
                            if (!ringtone.isEmpty()) {
                                try {
                                    playSound(Uri.parse(ringtone));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                int mode = sharedPreferences.getInt(getString(R.string.pref_whats_app_message_vibrate), 1);
                                vibrate(mode);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {}

    private void playSound(Uri uri) throws IOException {
        if (mediaPlayer == null) mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(this, uri);
        final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
            mediaPlayer.prepare();
            mediaPlayer.start();
        }
    }

    private void vibrate(int mode){
        // TODO
    }

}
