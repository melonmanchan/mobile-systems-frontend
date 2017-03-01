package fi.tutee.tutee;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.Button;

public class UserTypeSelectionActivity extends AppCompatActivity {
    Button tutorButton;
    Button tuteeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type_selection);

        tutorButton = (Button) findViewById(R.id.userTypeSelectionTutor);
        tuteeButton = (Button) findViewById(R.id.userTypeSelectionTutee);

        tutorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("tutorbuttonclick");
            }
        });

        tuteeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("tuteebuttonclick");
            }
        });
    }

}
