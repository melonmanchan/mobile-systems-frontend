package fi.tutee.tutee.home;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.google.android.gms.vision.text.Text;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fi.tutee.tutee.R;
import fi.tutee.tutee.adapters.EventListAdapter;
import fi.tutee.tutee.data.entities.TimesResponse;
import fi.tutee.tutee.data.entities.User;

public class HomeTutorScheduleFragment extends HomeBaseFragment implements MonthLoader.MonthChangeListener, WeekView.EmptyViewClickListener {
    public WeekView mWeekView;
    public TabLayout days;
    private Calendar day;
    private boolean snackShown = false;
    private ArrayList<WeekViewEvent> mNewEvents;
    private ArrayList<User> tutees;

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
        mNewEvents = new ArrayList<WeekViewEvent>();

        mWeekView.setMonthChangeListener(this);
        mWeekView.setEmptyViewClickListener(this);

        days.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab selectedTab) {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                View tab = selectedTab.getCustomView();
                String date = ((TextView) tab.findViewById(R.id.custom_date_tab_date)).getText().toString();

                date += "/" + Calendar.getInstance().get(Calendar.YEAR);

                Date selected = format.parse(date, new ParsePosition(0));

                Calendar newCalendarInstance = Calendar.getInstance();
                newCalendarInstance.setTime(selected);
                mWeekView.goToDate(newCalendarInstance);
                mWeekView.mCurrentOrigin.y = -1460;
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
        return root;
    }

    private void showTutorialSnack() {
        if (!snackShown) {
            Snackbar.make(getView(), "Tap the timeslots to mark times when you are available for tutoring!", Snackbar.LENGTH_LONG).show();
            snackShown = true;
        }
    }

    private int[] getEventCountForDate() {
        int[] events = new int[20];
        day = Calendar.getInstance();
        int day_day = day.get(Calendar.DAY_OF_YEAR);

        for (WeekViewEvent e: mNewEvents) {
            Calendar start = e.getStartTime();

            int event_day = start.get(Calendar.DAY_OF_YEAR);
            int diff = event_day - day_day;
            if (e.getTuteeID() != null && diff >= 0) {
                events[diff] += 1;
            }

        }

        return events;
    }

    private void showMoreDates(int evs[]) {
        day = Calendar.getInstance();
        days.removeAllTabs();
        for (int i = 0; i < 20; i++) {
            String weekday = Integer.toString(day.get(Calendar.DAY_OF_MONTH));
            String month = Integer.toString(day.get(Calendar.MONTH) +1);

            TabLayout.Tab tab = days.newTab().setCustomView(R.layout.custom_date_tab);
            View customView = tab.getCustomView();
            TextView dateText = (TextView) customView.findViewById(R.id.custom_date_tab_date);

            View badgeWrapper = customView.findViewById(R.id.custom_date_tab_badge_wrapper);
            TextView badgeText = (TextView)  customView.findViewById(R.id.custom_date_tab_badge);
            if (evs[i] > 0) {
                badgeWrapper.setVisibility(View.VISIBLE);
                badgeText.setText("" + evs[i]);
            }

            dateText.setText(weekday + "/" + month);

            days.addTab(tab);
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

    private int getIndexForCalendars(Calendar start, Calendar end) {
        int foundIndex = -1;

        for (int i = 0; i < mNewEvents.size(); i++) {
            WeekViewEvent event = mNewEvents.get(i);

            if (event.getStartTime().equals(start) && event.getEndTime().equals(end)) {
                foundIndex = i;
                break;
            }
        }

        return foundIndex;
    }

    @Override
    public void onEmptyViewClicked(Calendar time) {
        // Set the new event with duration one hour.
        time.set(Calendar.MINUTE, 0);

        Calendar endTime = (Calendar) time.clone();
        endTime.add(Calendar.HOUR, 1);

        // Create a new event.
        WeekViewEvent event = new WeekViewEvent(time, endTime);

        if (!mNewEvents.contains(event)) {
            mNewEvents.add(event);
            this.presenter.createFreeTime(event);
            mWeekView.notifyDatasetChanged();
        } else {
            int index = getIndexForCalendars(time, endTime);

            if (!mNewEvents.get(index).isReserved()) {
                mNewEvents.remove(event);
                this.presenter.removeTime(event);
                mWeekView.notifyDatasetChanged();
            }
        }
    }

    public void setTimes(TimesResponse events) {
        ArrayList<WeekViewEvent> ownEvents = events.getOwnEvents();
        mNewEvents.clear();
        mNewEvents.addAll(ownEvents);
        int evs[] = getEventCountForDate();
        showMoreDates(evs);
        showTimes();
    }

    public void setTuteeNames(ArrayList<User> tutees) {
        this.tutees = tutees;
        showTimes();
    }

    private void showTimes() {
        if (tutees == null || mNewEvents.size() == 0) return;

        for (WeekViewEvent e : mNewEvents) {
            for (User tutee : tutees) {
                if (e.getTuteeID() != null && e.getTuteeID() == tutee.getId()) {
                    e.setName(tutee.getFirstName() + " " + tutee.getLastName());
                    break;
                }
            }
        }

        mWeekView.notifyDatasetChanged();
    }

    @Override
    public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        ArrayList<WeekViewEvent> newEvents = getNewEvents(newYear, newMonth);
        events.addAll(newEvents);
        return events;
    }

    @Override
    public void onResume() {
        presenter.getTimes();
        showTutorialSnack();
        super.onResume();
    }
}
