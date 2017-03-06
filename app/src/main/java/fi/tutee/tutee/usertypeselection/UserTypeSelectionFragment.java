package fi.tutee.tutee.usertypeselection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;

import fi.tutee.tutee.R;
import fi.tutee.tutee.pickauthentication.AuthenticationActivity;

public class UserTypeSelectionFragment extends Fragment implements UserTypeSelectionContract.View {
    private Button tutorButton;
    private Button tuteeButton;

    public final static String IS_TUTOR = "fi.tutee.tutee";

    private UserTypeSelectionContract.Presenter presenter;


    public static UserTypeSelectionFragment newInstance() {
        Bundle arguments = new Bundle();
        UserTypeSelectionFragment fragment = new UserTypeSelectionFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.start();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.content_user_type_selection, container, false);

        tutorButton = (Button) root.findViewById(R.id.userTypeSelectionTutor);
        tuteeButton = (Button) root.findViewById(R.id.userTypeSelectionTutee);

        tutorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToAuthentication(true);
            }
        });

        tuteeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToAuthentication(false);
            }
        });

        return root;
    }

    private void moveToAuthentication(Boolean isTutor) {
        Intent intent = new Intent(getContext(), AuthenticationActivity.class);
        intent.putExtra(IS_TUTOR, isTutor);
        startActivity(intent);
    }

    public void setPresenter(UserTypeSelectionContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
