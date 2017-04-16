package fi.tutee.tutee.reservecalendar;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import fi.tutee.tutee.R;
import fi.tutee.tutee.TuteeApplication;
import fi.tutee.tutee.registertutorextra.RegisterExtraFragment;
import fi.tutee.tutee.registertutorextra.RegisterExtraPresenter;
import fi.tutee.tutee.utils.ActivityUtils;

/**
 * Created by lehtone1 on 16/04/17.
 */

public class ReserveCalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_calendar);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_title_only);

        TextView title = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.action_bar_title);
        title.setText("Fill your profile");

        TuteeApplication app = (TuteeApplication)  getApplication();

        RegisterExtraFragment registerExtraFragment = (RegisterExtraFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (registerExtraFragment == null) {
            registerExtraFragment = RegisterExtraFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    registerExtraFragment, R.id.contentFrame);
        }

        new RegisterExtraPresenter(
                app.repository,
                registerExtraFragment
        );
    }
}
