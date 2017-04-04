package fi.tutee.tutee.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fi.tutee.tutee.R;
import fi.tutee.tutee.adapters.SubjectsListAdapter;
import fi.tutee.tutee.data.entities.Subject;

public class HomeSearchFragment extends HomeBaseFragment {
    private ArrayList<Subject> subjects;

    private ListView list;

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

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;

        // This is a simple adapter that accepts as parameter
        // Context
        // Data list
        // The row layout that is used during the row creation
        // The keys used to retrieve the data
        // The View id used to show the data. The key number and the view id must match
        SubjectsListAdapter adapter = new SubjectsListAdapter(getContext(), R.layout.search_list_item, subjects);

        adapter.setListener(new SubjectsListAdapter.OnSubjectSelectedListener() {
            @Override
            public void onSelected(Subject user) {

            }
        });

        list.setAdapter(adapter);
    }
}
