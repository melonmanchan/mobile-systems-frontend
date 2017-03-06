package fi.tutee.tutee.pickauthentication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import fi.tutee.tutee.R;

/**
 * Created by mat on 06/03/2017.
 */

public class AuthenticationFragment  extends Fragment implements AuthenticationContract.View {
    private Button loginBtn;
    private Button registerBtn;
    private Button loginWithGoogleBtn;
    private Button loginWithFacebookBtn;

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
        registerBtn = (Button) root.findViewById(R.id.registerButton);
        loginWithGoogleBtn = (Button) root.findViewById(R.id.googleLoginButton);
        loginWithFacebookBtn = (Button) root.findViewById(R.id.facebookLoginButton);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.login("hello", "world");
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

        return root;
    }

    @Override
    public void setPresenter(AuthenticationContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
