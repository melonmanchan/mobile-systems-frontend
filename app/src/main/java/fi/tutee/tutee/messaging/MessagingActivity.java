package fi.tutee.tutee.messaging;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.TextView;

import fi.tutee.tutee.R;
import fi.tutee.tutee.TuteeApplication;
import fi.tutee.tutee.home.HomeMessagesFragment;
import fi.tutee.tutee.utils.ActivityUtils;

public class MessagingActivity extends AppCompatActivity {
    private MessagingFragment messagingFragment;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_messaging, menu);

        if (messagingFragment != null) {
            boolean isTutor = messagingFragment.isOtherUserTutor();

            if (!isTutor) {
                menu.findItem(R.id.action_go_to_calendar).setVisible(false);
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        TuteeApplication app = (TuteeApplication)  getApplication();

        messagingFragment = (MessagingFragment) getSupportFragmentManager()
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
        ).start();
    }

    public void setTitle(String string) {
        TextView title = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.action_bar_title);
        title.setText(string);
    }
}
