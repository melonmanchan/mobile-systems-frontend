package fi.tutee.tutee.usertypeselection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fi.tutee.tutee.R;
import fi.tutee.tutee.TuteeApplication;
import fi.tutee.tutee.utils.ActivityUtils;

public class UserTypeSelectionActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type_selection);

        TuteeApplication app = (TuteeApplication)  getApplication();

        UserTypeSelectionFragment userTypeSelectionFragment = (UserTypeSelectionFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (userTypeSelectionFragment == null) {
            userTypeSelectionFragment = UserTypeSelectionFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    userTypeSelectionFragment, R.id.contentFrame);
        }

        new UserTypeSelectionPresenter(
                app.repository,
                userTypeSelectionFragment
        );

    }
}