package fi.tutee.tutee.selecttutor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import fi.tutee.tutee.R;
import fi.tutee.tutee.TuteeApplication;
import fi.tutee.tutee.home.HomeContract;
import fi.tutee.tutee.home.HomeFragment;
import fi.tutee.tutee.home.HomePresenter;
import fi.tutee.tutee.home.HomeSearchFragment;
import fi.tutee.tutee.utils.ActivityUtils;

/**
 * Created by mat on 12/03/2017.
 */

public class SelectTutorActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_tutor);


        TuteeApplication app = (TuteeApplication)  getApplication();

        SelectTutorFragment selectTutorFragment = (SelectTutorFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (selectTutorFragment == null) {
            selectTutorFragment = SelectTutorFragment.newInstance(
                    getIntent().getStringExtra(HomeSearchFragment.SUBJECT_TYPE),
                    getIntent().getIntExtra(HomeSearchFragment.SUBJECT_ID, 0));

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    selectTutorFragment, R.id.contentFrame);
        }


        new SelectTutorPresenter(
                app.repository,
                selectTutorFragment
        );
    }
}
