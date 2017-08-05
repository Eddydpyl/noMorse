package dpyl.eddy.nomorse;

import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

public class MessageListenerService extends NotificationListenerService {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
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
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {

    }

}
