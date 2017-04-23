package fi.tutee.tutee.reservecalendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;

import fi.tutee.tutee.R;
import fi.tutee.tutee.adapters.EventListAdapter;
import fi.tutee.tutee.data.entities.APIError;
import fi.tutee.tutee.tutorselectdetails.TutorSelectDetailsFragment;


/**
 * Created by lehtone1 on 16/04/17.
 */

public class ReserveCalendarFragment  extends Fragment implements ReserveCalendarContract.View, EventListAdapter.onEventSelectedListener {

    private ReserveCalendarContract.Presenter presenter;

    private ArrayList<WeekViewEvent> reservedTimes = new ArrayList<>();
    private RecyclerView eventList;
    private TextView emptyView;
    private FloatingActionButton openReserveModal;

    private int tutorID;
    private boolean paired;
    private LinearLayoutManager mLayoutManager;

    public ReserveCalendarFragment() {}

    public static ReserveCalendarFragment newInstance(int tutordID, boolean paired) {
        Bundle arguments = new Bundle();
        ReserveCalendarFragment fragment = new ReserveCalendarFragment();
        arguments.putInt(TutorSelectDetailsFragment.TUTOR_ID, tutordID);
        arguments.putBoolean(TutorSelectDetailsFragment.ALREADY_PAIRED, paired);
        fragment.setArguments(arguments);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.content_reserve_calendar, container, false);
        tutorID = getArguments().getInt(TutorSelectDetailsFragment.TUTOR_ID);
        paired = getArguments().getBoolean(TutorSelectDetailsFragment.ALREADY_PAIRED);

        eventList = (RecyclerView) root.findViewById(R.id.event_list);
        mLayoutManager = new LinearLayoutManager(root.getContext());
        EventListAdapter adapter = new EventListAdapter(null);

        if (paired) {
            adapter.setListener(this);
        }

        adapter.setAlreadyPaired(paired);

        eventList.setAdapter(adapter);
        eventList.setLayoutManager(mLayoutManager);

        emptyView = (TextView) root.findViewById(R.id.empty_view);
        emptyView.setText("This tutor has no available times.");

        openReserveModal = (FloatingActionButton) root.findViewById(R.id.events_open_order_modal);

        openReserveModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        openReserveModal.hide();

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

        if (events.isEmpty()) {
            eventList.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            eventList.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onReserveTimeSuccess() {
        presenter.getFreeTimes(tutorID);
    }

    @Override
    public void onReserveTimeFail(ArrayList<APIError> errors) {
        String errorMessage = "Something went wrong!";

        if (errors != null && errors.size() > 0) {
            errorMessage = errors.get(0).getMessage();
        }

        Snackbar.make(getView(), errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onSelected(int position) {
        EventListAdapter adapter = (EventListAdapter) eventList.getAdapter();
        WeekViewEvent event = (WeekViewEvent) adapter.getItem(position);

        if (reservedTimes.contains((event))) {
            reservedTimes.remove(event);
            adapter.setEventUnselected(event.getID());
        } else {
            reservedTimes.add(event);
            adapter.setEventSelected(event.getID());
        }

        if (reservedTimes.size() == 0) {
            openReserveModal.hide();
        } else {
            openReserveModal.show();
        }

        adapter.notifyDataSetChanged();
        //this.presenter.reserveTime(event);
    }

    @Override
    public void onDestroy() {
        EventListAdapter adapter = (EventListAdapter) eventList.getAdapter();
        adapter.detachListener();
        super.onDestroy();
    }
}
