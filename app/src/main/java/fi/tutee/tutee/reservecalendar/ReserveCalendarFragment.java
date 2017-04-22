package fi.tutee.tutee.reservecalendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;

import fi.tutee.tutee.R;
import fi.tutee.tutee.adapters.EventListAdapter;
import fi.tutee.tutee.tutorselectdetails.TutorSelectDetailsFragment;


/**
 * Created by lehtone1 on 16/04/17.
 */

public class ReserveCalendarFragment  extends Fragment implements ReserveCalendarContract.View {

    private ReserveCalendarContract.Presenter presenter;

    private RecyclerView eventList;


    private int tutorID;
    private LinearLayoutManager mLayoutManager;

    public ReserveCalendarFragment() {}

    public static ReserveCalendarFragment newInstance(int tutordID) {
        Bundle arguments = new Bundle();
        ReserveCalendarFragment fragment = new ReserveCalendarFragment();
        arguments.putInt(TutorSelectDetailsFragment.TUTOR_ID, tutordID);
        fragment.setArguments(arguments);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.content_reserve_calendar, container, false);
        tutorID = getArguments().getInt(TutorSelectDetailsFragment.TUTOR_ID);

        eventList = (RecyclerView) root.findViewById(R.id.event_list);
        mLayoutManager = new LinearLayoutManager(root.getContext());
        EventListAdapter adapter = new EventListAdapter();
        eventList.setAdapter(adapter);
        eventList.setLayoutManager(mLayoutManager);

        presenter.getFreeTimes(tutorID);


        return root;

    }


    @Override
    public void setPresenter(ReserveCalendarContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setFreeTimes(ArrayList<WeekViewEvent> events) {
        EventListAdapter adapter = (EventListAdapter) eventList.getAdapter();
        adapter.setEvents(events);

    }

}
