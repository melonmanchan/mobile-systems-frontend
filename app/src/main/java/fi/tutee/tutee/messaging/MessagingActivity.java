package fi.tutee.tutee.messaging;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import fi.tutee.tutee.R;
import fi.tutee.tutee.TuteeApplication;
import fi.tutee.tutee.home.HomeMessagesFragment;
import fi.tutee.tutee.utils.ActivityUtils;

/**
 * Created by lehtone1 on 12/04/17.
 */

public class MessagingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_title_only);

        setTitle("Insert username");

        TuteeApplication app = (TuteeApplication)  getApplication();

        MessagingFragment messagingFragment = (MessagingFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (messagingFragment == null) {
            messagingFragment = messagingFragment.newInstance(
                    getIntent().getIntExtra(HomeMessagesFragment.USER_ID, -1)
            );

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    messagingFragment, R.id.contentFrame);
        }

        new MessagingPresenter(
                app.repository,
                messagingFragment
        );
    }

    public void setTitle(String string) {
        TextView title = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.action_bar_title);
        title.setText(string);

    }
}
