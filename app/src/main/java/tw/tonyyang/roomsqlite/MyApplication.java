package tw.tonyyang.roomsqlite;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by tonyyang on 2017/12/29.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
