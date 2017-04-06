package fi.tutee.tutee.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import fi.tutee.tutee.R;
import fi.tutee.tutee.adapters.SubjectsSearchListAdapter;
import fi.tutee.tutee.data.entities.Subject;
import fi.tutee.tutee.selecttutor.SelectTutorActivity;

public class HomeSearchFragment extends HomeBaseFragment {
    private ArrayList<Subject> subjects;

    private ListView list;
    public final static String SUBJECT_TYPE = "fi.tutee.tutee.SUBJECT_TYPE";
    public final static String SUBJECT_ID = "fi.tutee.tutee.SUBJECT_ID";

    public HomeSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.content_home_search, container, false);
        list = (ListView) root.findViewById(R.id.subjectListView);

        this.presenter.getSubjects();

        // Inflate the layout for this fragment
        return root;
    }

    public void setSubjects(final ArrayList<Subject> subjects) {
        this.subjects = subjects;

        SubjectsSearchListAdapter adapter = new SubjectsSearchListAdapter(getContext(), R.layout.search_list_item, subjects);

        adapter.setListener(new SubjectsSearchListAdapter.OnSubjectSelectedListener() {
            @Override
            public void onSelected(Subject subject) {
                Intent intent = new Intent(getContext(), SelectTutorActivity.class);
                intent.putExtra(SUBJECT_TYPE, subject.getType());
                intent.putExtra(SUBJECT_ID, subject.getId());
                startActivity(intent);

            }
        });

        list.setAdapter(adapter);
    }
}
