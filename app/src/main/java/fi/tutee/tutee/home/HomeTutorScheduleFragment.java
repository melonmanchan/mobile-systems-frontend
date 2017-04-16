package fi.tutee.tutee.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fi.tutee.tutee.R;

public class HomeTutorScheduleFragment extends HomeBaseFragment implements MonthLoader.MonthChangeListener, WeekView.EmptyViewClickListener {
    public WeekView mWeekView;
    public TabLayout days;
    private Calendar day;
    private ArrayList<WeekViewEvent> mNewEvents;

    public HomeTutorScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.content_home_tutor_schedule, container, false);
        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) root.findViewById(R.id.weekView);
        days = (TabLayout) root.findViewById(R.id.days);
        day = Calendar.getInstance();
        mNewEvents = new ArrayList<WeekViewEvent>();

        mWeekView.setMonthChangeListener(this);
        mWeekView.setEmptyViewClickListener(this);

        days.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                SimpleDateFormat format = new SimpleDateFormat("d/M", Locale.getDefault());
                String date = tab.getText().toString();

                Date selected = format.parse(date, new ParsePosition(0));

                Calendar newCalendarInstance = Calendar.getInstance();
                newCalendarInstance.setTime(selected);
                mWeekView.goToDate(newCalendarInstance);


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        // Set an action when any event is clicked.
        //mWeekView.setOnEventClickListener(mEventClickListener);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.

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
                return String.format("%1$02d:00", hour);
            }
        });
    }



    private ArrayList<WeekViewEvent> getNewEvents(int year, int month) {

        // Get the starting point and ending point of the given month. We need this to find the
        // events of the given month.
        Calendar startOfMonth = Calendar.getInstance();
        startOfMonth.set(Calendar.YEAR, year);
        startOfMonth.set(Calendar.MONTH, month - 1);
        startOfMonth.set(Calendar.DAY_OF_MONTH, 1);
        startOfMonth.set(Calendar.HOUR_OF_DAY, 0);
        startOfMonth.set(Calendar.MINUTE, 0);
        startOfMonth.set(Calendar.SECOND, 0);
        startOfMonth.set(Calendar.MILLISECOND, 0);
        Calendar endOfMonth = (Calendar) startOfMonth.clone();
        endOfMonth.set(Calendar.DAY_OF_MONTH, endOfMonth.getMaximum(Calendar.DAY_OF_MONTH));
        endOfMonth.set(Calendar.HOUR_OF_DAY, 23);
        endOfMonth.set(Calendar.MINUTE, 59);
        endOfMonth.set(Calendar.SECOND, 59);

        // Find the events that were added by tapping on empty view and that occurs in the given
        // time frame.
        ArrayList<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        for (WeekViewEvent event : mNewEvents) {
            if (event.getEndTime().getTimeInMillis() > startOfMonth.getTimeInMillis() &&
                    event.getStartTime().getTimeInMillis() < endOfMonth.getTimeInMillis()) {
                events.add(event);
            }
        }
        return events;
    }




    @Override
    public void onEmptyViewClicked(Calendar time) {
        // Set the new event with duration one hour.
        time.set(Calendar.MINUTE, 0);

        Calendar endTime = (Calendar) time.clone();
        endTime.add(Calendar.HOUR, 1);

        // Create a new event.
        WeekViewEvent event = new WeekViewEvent(20, null, time, endTime);
        //event.setColor(100);
        if (!mNewEvents.contains(event)) {
            mNewEvents.add(event);
            mWeekView.notifyDatasetChanged();
        } else {
            mNewEvents.remove(event);
            mWeekView.notifyDatasetChanged();
        }



        // Refresh the week view. onMonthChange will be called again.

    }

    @Override
    public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        ArrayList<WeekViewEvent> newEvents = getNewEvents(newYear, newMonth);
        events.addAll(newEvents);
        return events;
    }
}
