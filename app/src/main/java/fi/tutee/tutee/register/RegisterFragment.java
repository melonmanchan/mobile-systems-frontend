package fi.tutee.tutee.register;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import fi.tutee.tutee.R;
import fi.tutee.tutee.components.AvatarPickerFragment;
import fi.tutee.tutee.data.entities.APIError;
import fi.tutee.tutee.home.HomeActivity;
import fi.tutee.tutee.registertutorextra.RegisterExtraActivity;
import fi.tutee.tutee.usertypeselection.UserTypeSelectionFragment;
import fi.tutee.tutee.utils.ActivityUtils;

public class RegisterFragment extends Fragment implements RegisterContract.View {
    private AvatarPickerFragment avatarPickerFragment;
    private EditText registerEmail;
    private EditText registerFirstname;
    private EditText registerLastname;
    private TextInputEditText registerPassword;
    private Button registerBtn;

    private boolean setAvatar = false;
    private String profileAvatarUri;

    private boolean isTutor;

    private RegisterContract.Presenter presenter;

    public RegisterFragment() {}

    public static RegisterFragment newInstance(boolean isTutor) {
        Bundle arguments = new Bundle();
        arguments.putBoolean(UserTypeSelectionFragment.IS_TUTOR, isTutor);
        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.content_register, container, false);

        this.isTutor = getArguments().getBoolean(UserTypeSelectionFragment.IS_TUTOR, false);

        registerEmail = (EditText) root.findViewById(R.id.registerEmail);
        registerFirstname = (EditText) root.findViewById(R.id.registerFirstname);
        registerLastname = (EditText) root.findViewById(R.id.registerLastname);
        registerPassword = (TextInputEditText) root.findViewById(R.id.registerPassword);
        registerBtn = (Button) root.findViewById(R.id.registerButton);

        AvatarPickerFragment avatarPickerFragment = (AvatarPickerFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.register_avatar_picker);

        if (avatarPickerFragment == null) {
            avatarPickerFragment = AvatarPickerFragment.newInstance(null);
            ActivityUtils.addFragmentToActivity(getActivity().getSupportFragmentManager(),
                    avatarPickerFragment, R.id.register_avatar_picker);

            avatarPickerFragment.setListener(new AvatarPickerFragment.OnAvatarChangedListener() {
                @Override
                public void avatarChanged(String avatarUri) {
                    profileAvatarUri = avatarUri;
                    setAvatar = true;
                }
            });

            this.avatarPickerFragment = avatarPickerFragment;
        }

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = registerEmail.getText().toString();
                String password = registerPassword.getText().toString();
                String firstName = registerFirstname.getText().toString();
                String lastName = registerLastname.getText().toString();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)
                        && !TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName)) {
                    registerBtn.setEnabled(false);
                    ActivityUtils.hideKeyboard(getActivity());
                    presenter.register(firstName, lastName, email, password, isTutor ? "TUTOR" : "TUTEE");
                }
            }
        });

        return root;
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onRegisterSuccess() {
        if (this.setAvatar) {
            presenter.setAvatar(profileAvatarUri);
            return;
        }

        if (this.isTutor) {
            Intent intent = new Intent(getContext(), RegisterExtraActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getContext(), HomeActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onRegisterFail(ArrayList<APIError> errors) {
        String errorMessage = "Something went wrong!";

        registerBtn.setEnabled(true);

        if (errors != null && errors.size() > 0) {
            errorMessage = errors.get(0).getMessage();
        }

        Snackbar.make(getView(), errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onAvatarSuccess() {
        this.setAvatar = false;
        this.onRegisterSuccess();
    }

    @Override
    public void onAvatarFailed(ArrayList<APIError> errors) {
        String errorMessage = "Something went wrong!";

        registerBtn.setEnabled(true);

        if (errors != null && errors.size() > 0) {
            errorMessage = errors.get(0).getMessage();
        }

        Snackbar.make(getView(), errorMessage, Snackbar.LENGTH_LONG).show();
    }
}
