package fi.tutee.tutee.register;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import fi.tutee.tutee.R;
import fi.tutee.tutee.TuteeApplication;
import fi.tutee.tutee.usertypeselection.UserTypeSelectionFragment;
import fi.tutee.tutee.utils.ActivityUtils;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_title_only);

        TextView title = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.action_bar_title);
        title.setText("Register");

        TuteeApplication app = (TuteeApplication)  getApplication();

        RegisterFragment registerFragment = (RegisterFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (registerFragment == null) {
            registerFragment = RegisterFragment.newInstance(getIntent().getBooleanExtra(UserTypeSelectionFragment.IS_TUTOR, false));
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    registerFragment, R.id.contentFrame);
        }

        new RegisterPresenter(
                app.repository,
                registerFragment
        );
    }

}
