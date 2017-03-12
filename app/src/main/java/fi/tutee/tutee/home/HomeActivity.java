package fi.tutee.tutee.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fi.tutee.tutee.R;
import fi.tutee.tutee.TuteeApplication;
import fi.tutee.tutee.utils.ActivityUtils;

/**
 * Created by mat on 12/03/2017.
 */

public class HomeActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TuteeApplication app = (TuteeApplication)  getApplication();

        HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (homeFragment == null) {
            homeFragment = HomeFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    homeFragment, R.id.contentFrame);
        }

        new HomePresenter(
                app.repository,
                homeFragment
        );
    }
}
