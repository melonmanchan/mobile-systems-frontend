package fi.tutee.tutee.home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fi.tutee.tutee.R;
import fi.tutee.tutee.adapters.SubjectsListAdapter;

public class HomeSearchFragment extends HomeBaseFragment {
    private List<Map<String, String>> subjectsList = new ArrayList<Map<String,String>>();
    private ListAdapter adapter;

    public HomeSearchFragment() {
        // Required empty public constructor
    }

    private void initList() {
        // We populate the subjects
        subjectsList.add(subjectForDisplay("subject", "Mathematics"));
        subjectsList.add(subjectForDisplay("subject", "Biology"));
        subjectsList.add(subjectForDisplay("subject", "History"));
        subjectsList.add(subjectForDisplay("subject", "Geography"));
        subjectsList.add(subjectForDisplay("subject", "Religion"));
        subjectsList.add(subjectForDisplay("subject", "Physics"));
        subjectsList.add(subjectForDisplay("subject", "Chemistry"));
    }

    private HashMap<String, String> subjectForDisplay(String key, String name) {
        HashMap<String, String> subject = new HashMap<String, String>();
        subject.put(key, name);
        return subject;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initList();

        View root = inflater.inflate(R.layout.content_home_search, container, false);
        ListView list = (ListView) root.findViewById(R.id.subjectListView);

        // This is a simple adapter that accepts as parameter
        // Context
        // Data list
        // The row layout that is used during the row creation
        // The keys used to retrieve the data
        // The View id used to show the data. The key number and the view id must match
        adapter = new SubjectsListAdapter(getContext(), subjectsList, R.layout.search_list_item,
                new String[] {"subject"}, new int[] {R.id.searchlistitem});


        list.setAdapter(adapter);

        // React to user clicks on item
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView parentView, View view, int position, long id) {

                // We know the View is a TextView so we can cast it
                RelativeLayout clickedLayout = (RelativeLayout) view;
                TextView clickedView = (TextView) clickedLayout.findViewById(R.id.searchlistitem);

                Toast.makeText(getActivity(), "Item with id ["+id+"] - Position ["+position+"] - Subject ["+clickedView.getText()+"]",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Inflate the layout for this fragment
        return root;
    }
}
