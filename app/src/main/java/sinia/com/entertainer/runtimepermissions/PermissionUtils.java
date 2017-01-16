package sinia.com.entertainer.runtimepermissions;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import static android.R.attr.permission;

/**
 * Created by 忧郁的眼神 on 2017/1/16 0016.
 */

public class PermissionUtils {

    /**
     * 获取单个权限
     *
     * @param context
     * @param permission 权限名
     */
    public static void requestSinglePermission(Context context, String permission) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, 1);
        }
    }

    /**
     * 获取多个权限
     *
     * @param context
     * @param permissions 权限数组
     */
    public static void requestMorePermission(Context context, String[] permissions) {
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(context, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, permissions, 1);
            }
        }
    }
}
