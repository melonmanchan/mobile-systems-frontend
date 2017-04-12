package fi.tutee.tutee.tutorselectdetails;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fi.tutee.tutee.R;
import fi.tutee.tutee.data.entities.User;

/**
 * Created by mat on 12/04/2017.
 */

public class TutorSelectDetailsFragment extends Fragment implements  TutorSelectDetailsContract.View {
    private TutorSelectDetailsContract.Presenter presenter;

    public static String TUTOR_ID = "TUTOR_ID";

    public static TutorSelectDetailsFragment newInstance(int userID) {
        Bundle arguments = new Bundle(1);
        arguments.putInt(TUTOR_ID, userID);
        TutorSelectDetailsFragment fragment = new TutorSelectDetailsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void setPresenter(TutorSelectDetailsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.content_tutor_select_details, container, false);

        this.presenter.getTutorByID(getArguments().getInt(TUTOR_ID));

        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void setTutor(User user) {

    }
}
