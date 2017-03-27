package fi.tutee.tutee.home;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fi.tutee.tutee.R;
import fi.tutee.tutee.profile.ProfileActivity;

/**
 * Created by emmilinkola on 15/03/17.
 */

public class HomeSettingsFragment extends HomeBaseFragment {
    private static HomeSettingsFragment instance = null;

    private List<Map<String, String>> settingsList = new ArrayList<>();
    private SimpleAdapter adapter;

    public HomeSettingsFragment() {
        // Required empty public constructor
    }

    public static HomeSettingsFragment getInstance() {
        if (instance == null) {
            Bundle arguments = new Bundle();
            instance = new HomeSettingsFragment();
            instance.setArguments(arguments);
        }

        return instance;
    }

    private void initList() {
        settingsList.add(settingForDisplay("setting", "Profile"));
    }

    private HashMap<String, String> settingForDisplay(String key, String name) {
        HashMap<String, String> setting = new HashMap<String, String>();
        setting.put(key, name);
        return setting;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initList();

        View root = inflater.inflate(R.layout.content_home_settings, container, false);
        ListView list = (ListView) root.findViewById(R.id.settingsListView);

        // This is a simple adapter that accepts as parameter
        // Context
        // Data list
        // The row layout that is used during the row creation
        // The keys used to retrieve the data
        // The View id used to show the data. The key number and the view id must match
        adapter = new SimpleAdapter(getActivity(), settingsList, android.R.layout.simple_list_item_1,
                new String[] {"setting"}, new int[] {android.R.id.text1});

        list.setAdapter(adapter);

        // React to user clicks on item
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView parentView, View view, int position, long id) {

                // We know the View is a TextView so we can cast it
                TextView clickedView = (TextView) view;

                if (clickedView.getText() == "Profile") {
                    Intent intent = new Intent(getContext(), ProfileActivity.class);
                    startActivity(intent);
                }



            }
        });

        // Inflate the layout for this fragment
        return root;
    }

}
