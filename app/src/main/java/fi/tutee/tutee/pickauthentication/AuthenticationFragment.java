package fi.tutee.tutee.pickauthentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fi.tutee.tutee.R;
import fi.tutee.tutee.register.RegisterActivity;

/**
 * Created by mat on 06/03/2017.
 */

public class AuthenticationFragment  extends Fragment implements AuthenticationContract.View {
    private Button loginBtn;
    private Button registerNewAccountBtn;

    private EditText loginEmail;
    private TextInputEditText loginPassword;

    private AuthenticationContract.Presenter presenter;

    public static AuthenticationFragment newInstance() {
        Bundle arguments = new Bundle();
        AuthenticationFragment fragment = new AuthenticationFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.start();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                                    Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.content_authentication, container, false);

        loginBtn = (Button) root.findViewById(R.id.loginButton);
        registerNewAccountBtn = (Button) root.findViewById(R.id.registerNewAccountButton);

        loginEmail = (EditText) root.findViewById(R.id.loginEmail);
        loginPassword = (TextInputEditText) root.findViewById(R.id.loginPassword);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmail.getText().toString();
                String password = loginPassword.getText().toString();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    presenter.login(email, password);
                }
            }
        });

        registerNewAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("registerbuttonclick");
                Intent intent = new Intent(getContext(), RegisterActivity.class);
                startActivity(intent);

            }
        });

        return root;
    }

    @Override
    public void setPresenter(AuthenticationContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void loginSucceeded() {
        Toast.makeText(getContext(), "Login success", Toast.LENGTH_LONG).show();
    }

    @Override
    public void loginFailed() {
        Toast.makeText(getContext(), "Login failed", Toast.LENGTH_LONG).show();
    }
}
