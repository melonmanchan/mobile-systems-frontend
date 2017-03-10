package fi.tutee.tutee.registertutorextra;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;

import fi.tutee.tutee.R;
/**
 * Created by lehtone1 on 09/03/17.
 */

public class RegisterTutorExtraFragment extends Fragment implements RegisterTutorExtraContract.View {
    private EditText registerTutorExtraDescription;
    private Button registerTutorExtraBtn;
    private Button registerTutorExtraNewSkillBtn;
    private LinearLayout registerTutorExtraSkillsLayout;

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
            registerTutorExtraNewSkillBtn = (Button) root.findViewById(R.id.registerTutorExtraNewSkillButton);
            registerTutorExtraSkillsLayout = (LinearLayout) root.findViewById(R.id.content_register_tutor_extra_skills_layout);

            Spinner registerTutorExtraLevelSpin = (Spinner) root.findViewById(R.id.registerTutorExtraLevelSpinner);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                    R.array.level_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            registerTutorExtraLevelSpin.setAdapter(adapter);


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


            registerTutorExtraNewSkillBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    registerTutorExtraSkillsLayout.addView(createNewSkillLayout());
                }

            });



            return root;

    }


    private LinearLayout createNewSkillLayout() {
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        final LinearLayout linearLayout = new LinearLayout(this.getContext());
        linearLayout.setLayoutParams(lparams);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setPadding(0, 16, 0, 16);
        linearLayout.addView(createNewEditText());
        linearLayout.addView(createNewSpinner());
        return linearLayout;
    }

    private EditText createNewEditText() {
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(600,LinearLayout.LayoutParams.WRAP_CONTENT);
        final EditText editText = new EditText(this.getContext());
        editText.setHint("Skill");
        editText.setLayoutParams(lparams);
        return editText;
    }

    private Spinner createNewSpinner() {
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(300,LinearLayout.LayoutParams.WRAP_CONTENT);
        final Spinner spinner = new Spinner(this.getContext());
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.level_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setLayoutParams(lparams);

        return spinner;
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

