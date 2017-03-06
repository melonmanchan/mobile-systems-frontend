package fi.tutee.tutee;

import android.app.Application;
import android.content.Context;

/**
 * Created by mat on 06/03/2017.
 */

public class TuteeApplication extends Application{

    public static TuteeApplication instance = null;

    public static Context getInstance() {
        if (instance == null) {
            instance = new TuteeApplication();
        }

        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
