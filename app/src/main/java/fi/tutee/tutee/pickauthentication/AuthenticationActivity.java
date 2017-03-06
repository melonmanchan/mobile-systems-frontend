package fi.tutee.tutee.pickauthentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;

import fi.tutee.tutee.R;
import fi.tutee.tutee.TuteeApplication;
import fi.tutee.tutee.usertypeselection.UserTypeSelectionActivity;
import fi.tutee.tutee.usertypeselection.UserTypeSelectionFragment;
import fi.tutee.tutee.utils.ActivityUtils;

public class AuthenticationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        Intent intent = getIntent();
        String isTutor = intent.getStringExtra(UserTypeSelectionFragment.IS_TUTOR);

        TuteeApplication app = (TuteeApplication)  getApplication();

        AuthenticationFragment authenticationFragment = (AuthenticationFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (authenticationFragment == null) {
            authenticationFragment = AuthenticationFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    authenticationFragment, R.id.contentFrame);
        }

        new AuthenticationPresenter(
                app.repository,
                authenticationFragment
        );

    }
}
