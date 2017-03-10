package fi.tutee.tutee.usertypeselection;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.Window;

import fi.tutee.tutee.R;
import fi.tutee.tutee.TuteeApplication;
import fi.tutee.tutee.utils.ActivityUtils;

public class UserTypeSelectionActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type_selection);

        ActionBar actionbar = getSupportActionBar();
        actionbar.show();

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
