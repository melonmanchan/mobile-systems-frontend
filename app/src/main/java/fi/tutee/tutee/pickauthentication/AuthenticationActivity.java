package fi.tutee.tutee.pickauthentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;

import fi.tutee.tutee.R;
import fi.tutee.tutee.usertypeselection.UserTypeSelectionActivity;

public class AuthenticationActivity extends AppCompatActivity {
    Button loginBtn;
    Button registerBtn;
    Button loginWithGoogleBtn;
    Button loginWithFacebookBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        Intent intent = getIntent();
        String isTutor = intent.getStringExtra(UserTypeSelectionActivity.IS_TUTOR);

        loginBtn = (Button) findViewById(R.id.loginButton);
        registerBtn = (Button) findViewById(R.id.registerButton);
        loginWithGoogleBtn = (Button) findViewById(R.id.googleLoginButton);
        loginWithFacebookBtn = (Button) findViewById(R.id.facebookLoginButton);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("loginbuttonclick");
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("registerbuttonclick");
            }
        });

        loginWithGoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("google login buttonclick");
            }
        });

        loginWithFacebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("login with fb buttonclick");
            }
        });
    }
}
