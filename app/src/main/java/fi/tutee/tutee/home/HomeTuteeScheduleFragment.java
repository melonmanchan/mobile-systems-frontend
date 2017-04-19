package fi.tutee.tutee.home;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;

import fi.tutee.tutee.R;
import fi.tutee.tutee.adapters.EventListAdapter;
import fi.tutee.tutee.data.entities.TimesResponse;

/**
 * Created by lehtone1 on 16/04/17.
 */

public class HomeTuteeScheduleFragment extends HomeBaseFragment {

    private RecyclerView eventList;

    public HomeTuteeScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.content_home_tutee_schedule, container, false);

        eventList = (RecyclerView) root.findViewById(R.id.event_list);

        presenter.getTimes();


        return root;
    }


    public void setTimes(TimesResponse events) {
        EventListAdapter adapter = new EventListAdapter(events.getReservedEvents());
        eventList.setAdapter(adapter);
    }







}
