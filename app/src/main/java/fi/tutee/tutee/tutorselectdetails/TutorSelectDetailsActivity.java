package fi.tutee.tutee.tutorselectdetails;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fi.tutee.tutee.R;
import fi.tutee.tutee.TuteeApplication;
import fi.tutee.tutee.selecttutor.SelectTutorFragment;
import fi.tutee.tutee.utils.ActivityUtils;

public class TutorSelectDetailsActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tutor_select_details);

        TuteeApplication app = (TuteeApplication)  getApplication();

        TutorSelectDetailsFragment tutorSelectDetailsFragment = (TutorSelectDetailsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (tutorSelectDetailsFragment == null) {
            int tutorID = getIntent().getIntExtra(SelectTutorFragment.SELECTED_TUTOR_ID, 0);

            tutorSelectDetailsFragment = TutorSelectDetailsFragment.newInstance(tutorID);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    tutorSelectDetailsFragment, R.id.contentFrame);
        }

        new TutorSelectDetailsPresenter(
                app.repository,
                tutorSelectDetailsFragment
        );
    }
}
