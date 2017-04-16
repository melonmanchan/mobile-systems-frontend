package fi.tutee.tutee.adapters;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fi.tutee.tutee.R;
import fi.tutee.tutee.data.entities.User;

/**
 * Created by lehtone1 on 16/04/17.
 */

public class EventListAdapter extends ArrayAdapter<Object>{

    private ArrayList<Object> events;
    private Context context;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;


    public EventListAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);

        this.context = context;
        this.events = new ArrayList<>();


    }

    private int getItemTypeAt(int position) {
        Object item = getItem(position);

        if(item instanceof WeekViewEvent) {
            return TYPE_ITEM;
        } else {
            return TYPE_HEADER;
        }
    }

    @Override
    public Object getItem(final int position) {
        return events.get(position);
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if(getItemTypeAt(position) == TYPE_HEADER) {
            String time = (String) getItem(position);
            convertView = inflater.inflate(R.layout.event_list_header_item, null);

            TextView headerText = (TextView) convertView.findViewById(R.id.event_list_header_text);
            headerText.setText(time);
        } else {
            WeekViewEvent event = (WeekViewEvent) getItem(position);
            convertView = inflater.inflate(R.layout.event_list_item, null);

            TextView time = (TextView) convertView.findViewById(R.id.event_list_item_time);
            TextView name = (TextView) convertView.findViewById(R.id.even_list_item_name);

            time.setText(event.getStartTime().get(Calendar.HOUR) + "-" + event.getEndTime().get(Calendar.HOUR));
            name.setText("blöö");


        }

        return convertView;
    }

    public void setEvents(ArrayList<WeekViewEvent> events) {
        this.events.clear();
        sortEvents(events);

        WeekViewEvent curr;
        int currDay;
        WeekViewEvent prev;
        int prevDay;

        for(int i = 0; i < events.size(); i++) {
            curr = events.get(i);
            currDay = curr.getStartTime().get(Calendar.DAY_OF_YEAR);

            if (i == 0) {
                this.events.add("header");
                this.events.add(curr);
            } else {
                prev = events.get(i - 1);
                prevDay = prev.getStartTime().get(Calendar.DAY_OF_YEAR);

                if (currDay != prevDay) {
                    this.events.add("header");
                }
                this.events.add(curr);
            }
        }

        notifyDataSetChanged();
    }

    private void sortEvents(List<WeekViewEvent> events) {
        Collections.sort(events, new Comparator<WeekViewEvent>() {
            @Override
            public int compare(WeekViewEvent event1, WeekViewEvent event2) {
                long start1 = event1.getStartTime().getTimeInMillis();
                long start2 = event2.getStartTime().getTimeInMillis();
                int comparator = start1 > start2 ? 1 : (start1 < start2 ? -1 : 0);
                if (comparator == 0) {
                    long end1 = event1.getEndTime().getTimeInMillis();
                    long end2 = event2.getEndTime().getTimeInMillis();
                    comparator = end1 > end2 ? 1 : (end1 < end2 ? -1 : 0);
                }
                return comparator;
            }
        });
    }
}
