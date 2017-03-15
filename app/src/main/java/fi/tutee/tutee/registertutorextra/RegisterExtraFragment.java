package fi.tutee.tutee.registertutorextra;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import java.nio.Buffer;
import java.util.ArrayList;

import fi.tutee.tutee.R;
import fi.tutee.tutee.data.entities.Skill;

/**
 * Created by lehtone1 on 09/03/17.
 */

public class RegisterExtraFragment extends Fragment implements RegisterExtraContract.View {
    private EditText registerTutorExtraDescription;
    private Button registerTutorExtraBtn;
    private Button registerTutorExtraNewSkillBtn;
    private LinearLayout registerTutorExtraSkillsLayout;


    private RegisterExtraContract.Presenter presenter;

    public RegisterExtraFragment() {
            }

    public static RegisterExtraFragment newInstance() {
            Bundle arguments = new Bundle();
            RegisterExtraFragment fragment = new RegisterExtraFragment();
            fragment.setArguments(arguments);
            return fragment;
            }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

            View root = inflater.inflate(R.layout.content_register_extra, container, false);

            registerTutorExtraDescription = (EditText) root.findViewById(R.id.registerTutorExtraDescription);
            registerTutorExtraBtn = (Button) root.findViewById(R.id.registerTutorExtraButton);
            registerTutorExtraNewSkillBtn = (Button) root.findViewById(R.id.registerTutorExtraNewSkillButton);
            registerTutorExtraSkillsLayout = (LinearLayout) root.findViewById(R.id.content_register_tutor_extra_skills_layout);





            registerTutorExtraBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        String description = (String) registerTutorExtraDescription.getText().toString();
                        ArrayList<Skill> skills = new ArrayList<Skill>();

                        for(int index = 0; index < (registerTutorExtraSkillsLayout.getChildCount()); ++index) {
                            LinearLayout skillWrapper = (LinearLayout) ((registerTutorExtraSkillsLayout.getChildAt(index)));

                            EditText skillDescription = (EditText) skillWrapper.getChildAt(0);

                            Spinner skillLevelSpinner = (Spinner) skillWrapper.getChildAt(1);

                            String skillText = (String) skillDescription.getText().toString();

                            String level = (String) skillLevelSpinner.getSelectedItem().toString();

                            skills.add(new Skill(skillText, level));
                        }

                        presenter.registerTutorExtra(description, skills);




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
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout newLayout = (LinearLayout) inflater.inflate(R.layout.content_one_skill_layout, null);
        return newLayout;

    }





    @Override
    public void setPresenter(RegisterExtraContract.Presenter presenter) {
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

