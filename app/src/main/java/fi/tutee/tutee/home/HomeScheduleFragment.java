package fi.tutee.tutee.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import fi.tutee.tutee.R;

public class HomeScheduleFragment extends HomeBaseFragment{
    public WeekView mWeekView;
    public TabLayout days;
    private Calendar day;

    public HomeScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.content_home_schedule, container, false);
        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) root.findViewById(R.id.weekView);
        days = (TabLayout) root.findViewById(R.id.days);
        day = Calendar.getInstance();




        // Set an action when any event is clicked.
        //mWeekView.setOnEventClickListener(mEventClickListener);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(mMonthChangeListener);

        // Set long press listener for events.
        //mWeekView.setEventLongPressListener(mEventLongPressListener)


        // Inflate the layout for this fragment

        //Calendar date = Calendar.getInstance();
       // date.add(Calendar.DAY_OF_WEEK, 100);
        // mWeekView.goToDate(date);
        setupDateTimeInterpreter();
        showMoreDates();
        return root;
    }

    MonthLoader.MonthChangeListener mMonthChangeListener = new MonthLoader.MonthChangeListener() {
        @Override
        public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
            // Populate the week view with some events.
            List<WeekViewEvent> events = getEvents(newYear, newMonth);
            return events;
        }
    };

    private List<WeekViewEvent> getEvents(int newYear, int newMonth) {

        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

        return events;
    }

    private void showMoreDates() {

        for (int i = 0; i < 20; i++) {
            String weekday = Integer.toString(day.get(Calendar.DAY_OF_MONTH));
            String month = Integer.toString(day.get(Calendar.MONTH) +1);
            days.addTab(days.newTab().setText(weekday + "/" + month));
            day.add(Calendar.DAY_OF_MONTH, 1);

        }


    }

    private void setupDateTimeInterpreter() {
        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat format = new SimpleDateFormat(" EEE d/M", Locale.getDefault());

                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657

                return format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                return Integer.toString(hour);
            }
        });
    }




}
