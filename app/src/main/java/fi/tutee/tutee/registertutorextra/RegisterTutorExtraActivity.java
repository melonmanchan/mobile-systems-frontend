package fi.tutee.tutee.registertutorextra;

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
import fi.tutee.tutee.utils.ActivityUtils;

/**
 * Created by lehtone1 on 09/03/17.
 */

public class RegisterTutorExtraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_tutor_extra);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_title_only);

        TextView title = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.action_bar_title);
        title.setText("Register");

        TuteeApplication app = (TuteeApplication)  getApplication();

        RegisterTutorExtraFragment registerTutorExtraFragment = (RegisterTutorExtraFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (registerTutorExtraFragment == null) {
            registerTutorExtraFragment = RegisterTutorExtraFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    registerTutorExtraFragment, R.id.contentFrame);
        }

        new RegisterTutorExtraPresenter(
                app.repository,
                registerTutorExtraFragment
        );
    }

}
