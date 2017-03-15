package fi.tutee.tutee.profile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fi.tutee.tutee.R;
import fi.tutee.tutee.TuteeApplication;
import fi.tutee.tutee.usertypeselection.UserTypeSelectionFragment;
import fi.tutee.tutee.utils.ActivityUtils;

/**
 * Created by emmilinkola on 15/03/17.
 */

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TuteeApplication app = (TuteeApplication) getApplication();

        ProfileFragment profileFragment = (ProfileFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (profileFragment == null) {
            profileFragment = ProfileFragment.newInstance(getIntent().getBooleanExtra(UserTypeSelectionFragment.IS_TUTOR, false));
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    profileFragment, R.id.contentFrame);
        }

        new ProfilePresenter(
                app.repository,
                profileFragment
        );
    }

}
