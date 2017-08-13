package dpyl.eddy.nomorse;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;

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

    public static void enableListener(final Activity activity) {
        Set<String> packages = NotificationManagerCompat.getEnabledListenerPackages(activity);
        if (!packages.contains(activity.getPackageName())) {
            // Request permissions needed for the MessageListenerService
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage(R.string.content_request_permission_message).setTitle(R.string.content_request_permission_title);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                    activity.startActivity(intent);
                    dialog.dismiss();
                }
            }); builder.create().show();
        }
    }

}
