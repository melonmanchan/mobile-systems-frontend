package fi.tutee.tutee.usertypeselection;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.Window;
import android.widget.TextView;

import fi.tutee.tutee.R;
import fi.tutee.tutee.TuteeApplication;
import fi.tutee.tutee.utils.ActivityUtils;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class UserTypeSelectionActivity extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

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
