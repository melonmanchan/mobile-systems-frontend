package fi.tutee.tutee.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;

import fi.tutee.tutee.R;

/**
 * Created by lehtone1 on 16/04/17.
 */

public class HomeTuteeScheduleFragment extends HomeBaseFragment {

    private ArrayList<Object> datesAndEvents;

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


        presenter.getReservedTimes();


        return root;
    }


    public void setReservedTimes(ArrayList<WeekViewEvent> events) {
       //TODO:
    }







}
