package fi.tutee.tutee.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;

import fi.tutee.tutee.R;
import fi.tutee.tutee.adapters.EventListAdapter;
import fi.tutee.tutee.data.entities.TimesResponse;
import fi.tutee.tutee.data.entities.User;

/**
 * Created by lehtone1 on 16/04/17.
 */

public class HomeTuteeScheduleFragment extends HomeBaseFragment {

    private RecyclerView eventList;
    private LinearLayoutManager mLayoutManager;
    private TextView emptyView;

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
        mLayoutManager = new LinearLayoutManager(root.getContext());
        eventList.setLayoutManager(mLayoutManager);
        EventListAdapter adapter = new EventListAdapter();
        eventList.setAdapter(adapter);
        emptyView = (TextView) root.findViewById(R.id.empty_view);

        presenter.getTimes();

        return root;
    }

    public void setTimes(TimesResponse events) {
        EventListAdapter adapter = (EventListAdapter) eventList.getAdapter();
        adapter.setEvents(events.getReservedEvents());

        if (events.getReservedEvents().isEmpty()) {
            eventList.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            eventList.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        presenter.getTimes();
        super.onResume();
    }

    public void setTutorNames(ArrayList<User> tutors) {
        EventListAdapter adapter = (EventListAdapter) eventList.getAdapter();
        adapter.setTutors(tutors);
    }
}
