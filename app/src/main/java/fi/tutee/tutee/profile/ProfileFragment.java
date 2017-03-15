package fi.tutee.tutee.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fi.tutee.tutee.R;
import fi.tutee.tutee.usertypeselection.UserTypeSelectionFragment;

/**
 * Created by emmilinkola on 15/03/17.
 */

public class ProfileFragment extends Fragment implements ProfileContract.View  {
    private ProfileContract.Presenter presenter;
    private boolean isTutor;

    public ProfileFragment() {}

    public static ProfileFragment newInstance(boolean isTutor) {
        Bundle arguments = new Bundle();
        arguments.putBoolean(UserTypeSelectionFragment.IS_TUTOR, isTutor);
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_profile, container, false);
    }


}
