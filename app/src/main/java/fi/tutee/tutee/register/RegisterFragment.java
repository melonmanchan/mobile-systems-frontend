package fi.tutee.tutee.register;

import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import fi.tutee.tutee.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class RegisterFragment extends Fragment implements RegisterContract.View {
    private EditText registerEmail;
    private EditText registerFirstname;
    private EditText registerLastname;
    private TextInputEditText registerPassword;
    private Button registerBtn;

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

        registerEmail = (EditText) root.findViewById(R.id.registerEmail);
        registerFirstname = (EditText) root.findViewById(R.id.registerFirstname);
        registerLastname = (EditText) root.findViewById(R.id.registerLastname);
        registerPassword = (TextInputEditText) root.findViewById(R.id.registerPassword);
        registerBtn = (Button) root.findViewById(R.id.registerButton);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = registerEmail.getText().toString();
                String password = registerPassword.getText().toString();
                String firstName = registerFirstname.getText().toString();
                String lastName = registerLastname.getText().toString();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    presenter.register(firstName, lastName, email, password, "TUTOR");
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
        System.out.println("registersuccess");
    }

    @Override
    public void onRegisterFail() {
        System.out.println("registerfail");

    }
}
