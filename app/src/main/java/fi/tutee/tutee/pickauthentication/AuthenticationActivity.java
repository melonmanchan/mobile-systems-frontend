package fi.tutee.tutee.pickauthentication;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import fi.tutee.tutee.R;
import fi.tutee.tutee.TuteeApplication;
import fi.tutee.tutee.utils.ActivityUtils;

public class AuthenticationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_title_only);

        TextView title = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.action_bar_title);
        title.setText("Welcome");

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
