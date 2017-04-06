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
import fi.tutee.tutee.home.HomeBaseFragment;
import fi.tutee.tutee.home.HomeSearchFragment;

public class SelectTutorFragment extends Fragment implements SelectTutorContract.View {
    private ArrayList<User> tutors;
    private ListView list;
    private SelectTutorContract.Presenter presenter;
    private String subject;


    public SelectTutorFragment() {
        // Required empty public constructor

    }

    public static SelectTutorFragment newInstance(String subject) {
        Bundle arguments = new Bundle();
        arguments.putString(HomeSearchFragment.SUBJECT, subject);
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

        this.subject = getArguments().getString(HomeSearchFragment.SUBJECT);


        View root = inflater.inflate(R.layout.content_select_tutor, container, false);
        list = (ListView) root.findViewById(R.id.tutorListView);

        this.presenter.getTutors();

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
