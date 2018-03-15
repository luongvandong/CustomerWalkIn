package katana.customerwalkin;

import android.app.Application;

/**
 * ka
 * 26/09/2017
 */

public class WalkInApplication extends Application {

    public static volatile WalkInApplication instance;

    public static synchronized WalkInApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
