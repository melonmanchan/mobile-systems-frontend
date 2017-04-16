package fi.tutee.tutee.registertutorextra;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import fi.tutee.tutee.R;
import fi.tutee.tutee.TuteeApplication;
import fi.tutee.tutee.reservecalendar.ReserveCalendarFragment;
import fi.tutee.tutee.reservecalendar.ReserveCalendarPresenter;
import fi.tutee.tutee.utils.ActivityUtils;

public class RegisterExtraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_extra);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_title_only);

        TextView title = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.action_bar_title);
        title.setText("Choose a time");

        TuteeApplication app = (TuteeApplication)  getApplication();

        ReserveCalendarFragment reserveCalendarFragment = (ReserveCalendarFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (reserveCalendarFragment == null) {
            reserveCalendarFragment = ReserveCalendarFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    reserveCalendarFragment, R.id.contentFrame);
        }

        new ReserveCalendarPresenter(
                app.repository,
                reserveCalendarFragment
        );
    }
}
