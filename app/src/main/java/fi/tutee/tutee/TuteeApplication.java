package fi.tutee.tutee;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

import fi.tutee.tutee.data.entities.AuthResponse;
import fi.tutee.tutee.data.source.TuteeRepository;
import fi.tutee.tutee.data.source.local.TuteeLocalDataSource;
import fi.tutee.tutee.data.source.remote.TuteeRemoteDataSource;

/**
 * Created by mat on 06/03/2017.
 */

public class TuteeApplication extends Application {

    public static TuteeApplication instance = null;

    public static TuteeRepository repository;

    public static Context getInstance() {
        if (instance == null) {
            instance = new TuteeApplication();
        }

        return instance;
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
    }

    private void initStetho(final Context context) {
        Stetho.initialize(Stetho.newInitializerBuilder(context)
        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(context))
        .build());
    }
}
