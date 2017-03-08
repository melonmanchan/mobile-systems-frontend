package fi.tutee.tutee.register;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fi.tutee.tutee.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class RegisterFragment extends Fragment implements RegisterContract.View {

    private RegisterContract.Presenter presenter;

    public RegisterFragment() {
    }

    public static RegisterFragment newInstance() {
        Bundle arguments = new Bundle();
        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.content_register, container, false);

        return root;
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
