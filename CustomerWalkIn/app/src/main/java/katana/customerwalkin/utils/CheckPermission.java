package katana.customerwalkin.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class CheckPermission {

    public static final int LOCATION_PERMISSION_REQ_CODE = 100;
    public static final int REQUEST_EXTERNAL_STORAGE = 101;
    public static final int READ_PHONE_STATE_PERMISSION = 103;
    public static final int CAMERA = 1010;

    public static boolean checkLocationPermission(Activity activity) {
        return gainPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_PERMISSION_REQ_CODE);
    }

    public static boolean checkStoragePermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return gainPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE, REQUEST_EXTERNAL_STORAGE);
        } else {
            try {
                return gainPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE, REQUEST_EXTERNAL_STORAGE);
            } catch (Exception e) {
                return gainPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        REQUEST_EXTERNAL_STORAGE);
            }

        }
    }

    public static boolean gainPermission(Activity activity, String permission, final String permission2, final int requestCode) {
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity, new String[]{permission, permission2}, requestCode);
            return false;
        }
        return true;
    }

    public static boolean gainPermission(Activity activity, String permission, final int requestCode) {
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
            return false;
        }
        return true;
    }

    public static boolean checkReadPhoneStatePermission(Activity activity) {
        return gainPermission(activity, Manifest.permission.READ_PHONE_STATE, READ_PHONE_STATE_PERMISSION);
    }

}
