package fi.tutee.tutee;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;

import fi.tutee.tutee.data.source.TuteeRepository;
import fi.tutee.tutee.data.source.local.TuteeLocalDataSource;
import fi.tutee.tutee.data.source.remote.TuteeRemoteDataSource;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class TuteeApplication extends MultiDexApplication {

    public static TuteeApplication instance = null;

    public static TuteeRepository repository;

    public static Context getInstance() {
        if (instance == null) {
            instance = new TuteeApplication();
        }

        return instance;
    }

    @Override
    public void attachBaseContext(Context base) {
        MultiDex.install(base);
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        repository = TuteeRepository.getInstance(
                TuteeRemoteDataSource.getInstance(this),
                TuteeLocalDataSource.getInstance(this)
        );

        repository.fetchPersistedUserInfo();

        initStetho(this);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().build());
    }

    private void initStetho(final Context context) {
        Stetho.initialize(Stetho.newInitializerBuilder(context)
        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(context))
        .build());
    }

    public void setDeviceToken(String deviceToken) {
        if (repository != null) {
            repository.setDeviceToken(deviceToken);
        }
    }
}
