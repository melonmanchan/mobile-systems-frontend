package fi.tutee.tutee.register;

import android.content.Intent;
import android.os.SystemClock;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import fi.tutee.tutee.R;
import fi.tutee.tutee.registertutorextra.RegisterTutorExtraActivity;
import fi.tutee.tutee.usertypeselection.UserTypeSelectionFragment;






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

        Spinner registerCountrySpin = (Spinner) root.findViewById(R.id.registerCountrySpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.countries_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        registerCountrySpin.setAdapter(adapter);

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

                //Intent intent = getActivity().getIntent();
                //System.out.println(UserTypeSelectionFragment.IS_TUTOR);
                //boolean isTutor = intent.getBooleanExtra(UserTypeSelectionFragment.IS_TUTOR, false);
                //System.out.println(isTutor);

                System.out.println("registerbuttonclick");
                Intent intent2 = new Intent(getContext(), RegisterTutorExtraActivity.class);
                startActivity(intent2);

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
