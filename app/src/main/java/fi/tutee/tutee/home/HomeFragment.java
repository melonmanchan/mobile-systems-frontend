package fi.tutee.tutee.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by mat on 12/03/2017.
 */

public class HomeFragment extends Fragment implements HomeContract.View {
    private HomeContract.Presenter presenter;

    public static HomeFragment newInstance() {
        Bundle arguments = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
