package fi.tutee.tutee.profile;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import fi.tutee.tutee.R;
import fi.tutee.tutee.data.entities.User;
import fi.tutee.tutee.usertypeselection.UserTypeSelectionFragment;

/**
 * Created by emmilinkola on 15/03/17.
 */

public class ProfileFragment extends Fragment implements ProfileContract.View  {
    private ProfileContract.Presenter presenter;
    private boolean isTutor;
    private TextView firstname;
    private TextView lastname;
    private TextView email;
    private Button edit;
    private User user;

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
        View root = inflater.inflate(R.layout.content_profile, container, false);

        firstname = (EditText) root.findViewById(R.id.profile_firstname);
        firstname.setText(user.getFirstName());

        lastname = (EditText) root.findViewById(R.id.profile_lastname);
        lastname.setText(user.getLastName());

        email = (EditText) root.findViewById(R.id.profile_email);
        email.setText(user.getEmail());

        edit = (Button) root.findViewById(R.id.save_profile);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setEnabled(false);
                user.setFirstName(firstname.getText().toString());
                user.setLastName(lastname.getText().toString());
                presenter.updateUser(user);
            }
        });

        return root;
    }

    @Override
    public void setUser(User user) {
        this.user = user;

    }

    @Override
    public void onUpdateSuccess() {
        edit.setEnabled(true);
        String errorMessage = "Successfully updated!";
        Snackbar.make(getView(), errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onUpdateFailure() {
        edit.setEnabled(true);
        String errorMessage = "Something went wrong!";
        Snackbar.make(getView(), errorMessage, Snackbar.LENGTH_LONG).show();
    }


}
