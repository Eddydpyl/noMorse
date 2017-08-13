package dpyl.eddy.nomorse.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Vibrator;
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
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        whatsAppController = WhatsAppController.getInstante();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        whatsAppController = null;
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
                whatsAppController = WhatsAppController.getInstante();
                if (sbn.getTag()!= null) {
                    WhatsAppNotification wan = new WhatsAppNotification(sbn.getTag(), sbn.getPostTime());
                    if (whatsAppController.notify(wan)) {
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                        if (whatsAppController.isGroup(wan)) {
                            String ringtone = sharedPreferences.getString(getString(R.string.pref_whats_app_group_notification_tone), Settings.System.DEFAULT_NOTIFICATION_URI.toString());
                            int mode = Integer.valueOf(sharedPreferences.getString(getString(R.string.pref_whats_app_group_vibrate), "1"));
                            if (!ringtone.isEmpty()) {
                                try {
                                    playSound(Uri.parse(ringtone));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } if (mode > 0) vibrate(mode);
                        } else {
                            String ringtone = sharedPreferences.getString(getString(R.string.pref_whats_app_message_notification_tone), Settings.System.DEFAULT_NOTIFICATION_URI.toString());
                            int mode = Integer.valueOf(sharedPreferences.getString(getString(R.string.pref_whats_app_message_vibrate), "1"));
                            if (!ringtone.isEmpty()) {
                                try {
                                    playSound(Uri.parse(ringtone));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } if (mode > 0) vibrate(mode);
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
        mediaPlayer.reset();
        mediaPlayer.setDataSource(this, uri);
        final AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION) != 0) {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
            mediaPlayer.prepare();
            mediaPlayer.start();
        }
    }

    private void vibrate(int mode){
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        switch (mode) {
            case 1 : {
                long[] defaultPattern = {0, 400, 200, 400};
                vibrator.vibrate(defaultPattern, -1);
                break;
            }
            case 2 : {
                long[] shortPattern = {0, 300, 200, 300};
                vibrator.vibrate(shortPattern, -1);
                break;
            }
            case 3 : {
                long[] longPattern = {0, 800, 200, 800};
                vibrator.vibrate(longPattern, -1);
                break;
            }
            default: {
                long[] defaultPattern = {0, 400, 200, 400};
                vibrator.vibrate(defaultPattern, -1);
                break;
            }
        }
    }

}
