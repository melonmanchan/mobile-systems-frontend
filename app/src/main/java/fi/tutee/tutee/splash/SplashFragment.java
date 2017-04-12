package fi.tutee.tutee.splash;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fi.tutee.tutee.R;

/**
 * Created by emmilinkola on 12/04/17.
 */

public class SplashFragment extends Fragment implements SplashContract.View  {
    private SplashContract.Presenter presenter;

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
}
