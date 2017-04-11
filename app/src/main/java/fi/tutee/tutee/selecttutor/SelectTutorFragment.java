package fi.tutee.tutee.selecttutor;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import fi.tutee.tutee.R;
import fi.tutee.tutee.adapters.TutorListAdapter;
import fi.tutee.tutee.data.entities.APIError;
import fi.tutee.tutee.data.entities.Subject;
import fi.tutee.tutee.data.entities.User;
import fi.tutee.tutee.home.HomeSearchFragment;

public class SelectTutorFragment extends Fragment implements SelectTutorContract.View {
    private ArrayList<User> tutors;
    private ListView list;
    private SelectTutorContract.Presenter presenter;

    public SelectTutorFragment() {
        // Required empty public constructor
    }

    public static SelectTutorFragment newInstance(String subject, int id) {
        Bundle arguments = new Bundle();
        arguments.putString(HomeSearchFragment.SUBJECT_TYPE, subject);
        arguments.putInt(HomeSearchFragment.SUBJECT_ID, id);
        SelectTutorFragment fragment = new SelectTutorFragment();
        fragment.setArguments(arguments);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.content_select_tutor, container, false);
        list = (ListView) root.findViewById(R.id.tutorListView);

        this.presenter.getTutorsBySubjectID(getArguments().getInt(HomeSearchFragment.SUBJECT_ID));

        // Inflate the layout for this fragment
        return root;
    }

    public void setTutors(ArrayList<User> tutors) {
        this.tutors = tutors;

        final TutorListAdapter adapter = new TutorListAdapter(getContext(), R.layout.small_profile_item, tutors);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //adapter.getItem(position);
            }
        });

        list.setAdapter(adapter);
    }

    @Override
    public void getTutorsFailed(ArrayList<APIError> errors) {
        String errorMessage = "Fetching tutors failed!";

        if (errors != null) {
            errorMessage = errors.get(0).getMessage();
        }

        Snackbar.make(getView(), errorMessage, Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void setPresenter(SelectTutorContract.Presenter presenter)  {this.presenter = presenter;}
}
