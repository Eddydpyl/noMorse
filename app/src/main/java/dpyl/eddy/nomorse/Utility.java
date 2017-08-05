package dpyl.eddy.nomorse;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.NotificationManagerCompat;

import java.util.Set;

public class Utility {

    public static boolean isAppInstalled(Context context, String uri) {
        PackageManager packageManager = context.getPackageManager();
        boolean app_installed;
        try {
            packageManager.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public static void enableListener(Context context) {
        Set<String> packages = NotificationManagerCompat.getEnabledListenerPackages(context);
        if (!packages.contains(context.getPackageName())) {
            // Request permissions needed for the MessageListenerService
            Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            context.startActivity(intent);
        }
    }

}
