package fi.tutee.tutee.splash;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fi.tutee.tutee.R;
import fi.tutee.tutee.pickauthentication.AuthenticationActivity;
import fi.tutee.tutee.usertypeselection.UserTypeSelectionActivity;
import fi.tutee.tutee.utils.ActivityUtils;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SplashFragment splashFragment = (SplashFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (splashFragment == null) {
            splashFragment = SplashFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    splashFragment, R.id.contentFrame);
        }

        /* New Handler to start usertype select
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, AuthenticationActivity.class);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }


}
