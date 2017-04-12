package fi.tutee.tutee.splash;

import android.os.Handler;

import fi.tutee.tutee.data.entities.AuthResponse;
import fi.tutee.tutee.data.source.TuteeRepository;

public class SplashPresenter implements SplashContract.Presenter {
    private static int SPLASH_DISPLAY_LENGTH = 2000;
    private final SplashContract.View view;
    private final TuteeRepository repository;

    public SplashPresenter( TuteeRepository repository, SplashContract.View view) {
        this.repository = repository;
        this.view = view;

        view.setPresenter(this);
    }
    @Override
    public void start() {
        /* New Handler to start usertype select
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                view.goToNextActivity();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public AuthResponse getAutoLoginInfo() {
        AuthResponse authResponse = repository.fetchPersistedUserInfo();
        return authResponse;
    }
}
