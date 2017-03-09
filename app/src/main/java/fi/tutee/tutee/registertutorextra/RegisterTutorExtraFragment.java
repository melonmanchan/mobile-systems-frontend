package fi.tutee.tutee.registertutorextra;

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
/**
 * Created by lehtone1 on 09/03/17.
 */

public class RegisterTutorExtraFragment extends Fragment implements RegisterTutorExtraContract.View {
    private EditText registerTutorExtraDescription;
    private Button registerTutorExtraBtn;


    private RegisterTutorExtraContract.Presenter presenter;

    public RegisterTutorExtraFragment() {
            }

    public static RegisterTutorExtraFragment newInstance() {
            Bundle arguments = new Bundle();
            RegisterTutorExtraFragment fragment = new RegisterTutorExtraFragment();
            fragment.setArguments(arguments);
            return fragment;
            }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

            View root = inflater.inflate(R.layout.content_register_tutor_extra, container, false);

            registerTutorExtraDescription = (EditText) root.findViewById(R.id.registerTutorExtraDescription);
            registerTutorExtraBtn = (Button) root.findViewById(R.id.registerTutorExtraButton);


            registerTutorExtraBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        String description = registerTutorExtraDescription.getText().toString();

                    /**
                        if (!TextUtils.isEmpty(description) {
                        presenter.register(firstName, lastName, email, password, "TUTOR");
                        }
                     **/
                        }

                        });

                        return root;

                        }


    @Override
    public void setPresenter(RegisterTutorExtraContract.Presenter presenter) {
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

