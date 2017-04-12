package fi.tutee.tutee.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fi.tutee.tutee.R;
import fi.tutee.tutee.data.entities.AuthResponse;
import fi.tutee.tutee.data.entities.User;
import fi.tutee.tutee.home.HomeActivity;
import fi.tutee.tutee.pickauthentication.AuthenticationActivity;
import fi.tutee.tutee.registertutorextra.RegisterExtraActivity;

/**
 * Created by emmilinkola on 12/04/17.
 */

public class SplashFragment extends Fragment implements SplashContract.View  {
    private SplashContract.Presenter presenter;
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    public static SplashFragment newInstance() {
        Bundle arguments = new Bundle();
        SplashFragment fragment = new SplashFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.content_splash, container, false);

        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void setPresenter(SplashContract.Presenter presenter)  {this.presenter = presenter;}

    public void checkAutoLogin(Context context) {
    }

    @Override
    public void goToNextActivity() {
        AuthResponse response = presenter.getAutoLoginInfo();
        Intent intent;

        if (response != null && response.isValid()) {
            User savedUser = response.getUser();

            if (savedUser.needsToFillProfile()) {
                intent = new Intent(getContext(), RegisterExtraActivity.class);
            } else {
                intent = new Intent(getContext(), HomeActivity.class);
            }
        } else {
            intent = new Intent(getContext(), AuthenticationActivity.class);
        }

        startActivity(intent);
    }
}
