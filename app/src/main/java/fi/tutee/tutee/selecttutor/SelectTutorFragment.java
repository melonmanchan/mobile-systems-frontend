package fi.tutee.tutee.selecttutor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import fi.tutee.tutee.R;
import fi.tutee.tutee.adapters.TutorListAdapter;
import fi.tutee.tutee.data.entities.Subject;
import fi.tutee.tutee.data.entities.User;
import fi.tutee.tutee.home.HomeSearchFragment;

public class SelectTutorFragment extends Fragment implements SelectTutorContract.View {
    private ArrayList<User> tutors;
    private ListView list;
    private SelectTutorContract.Presenter presenter;

    private Subject subject;


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

        this.subject = new Subject();
        this.subject.setId(getArguments().getInt(HomeSearchFragment.SUBJECT_ID));
        this.subject.setType(getArguments().getString(HomeSearchFragment.SUBJECT_TYPE));


        View root = inflater.inflate(R.layout.content_select_tutor, container, false);
        list = (ListView) root.findViewById(R.id.tutorListView);

        //this.presenter.getTutorsBySubject(subject);

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
    public void setPresenter(SelectTutorContract.Presenter presenter)  {this.presenter = presenter;}
}
