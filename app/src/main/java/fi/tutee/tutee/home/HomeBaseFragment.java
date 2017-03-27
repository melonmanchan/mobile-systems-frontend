package fi.tutee.tutee.home;

import android.support.v4.app.Fragment;

public class HomeBaseFragment extends Fragment {
    protected HomeContract.Presenter presenter;

    public void setPresenter(HomeContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
