package dpyl.eddy.nomorse;

import android.content.Context;
import android.content.pm.PackageManager;

public class Utility {

    public static boolean appInstalled(Context context, String uri) {
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

}
