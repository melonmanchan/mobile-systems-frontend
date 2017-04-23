package fi.tutee.tutee.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alamkanak.weekview.WeekViewEvent;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
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

public class EventListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Object> events;
    private Context context;
    private static onEventSelectedListener listener;
    private ArrayList<User> tutors;

    private boolean alreadyPaired = true;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private SimpleDateFormat headerFormat = new SimpleDateFormat("EEE dd.MM");
    private SimpleDateFormat itemFormat = new SimpleDateFormat("HH:mm");



    public EventListAdapter(ArrayList<User> tutors) {
        this.tutors = tutors;
        this.events = new ArrayList<>();
    }

    public interface onEventSelectedListener {
        void onSelected(int position);
    }

    public void setListener(onEventSelectedListener listener) {
        this.listener = listener;
    }
    
    public void detachListener() {
        this.listener = null;
    }

    public void setAlreadyPaired(boolean alreadyPaired) {
        this.alreadyPaired = alreadyPaired;
    }

    public static class ViewHolderHeader extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView text;

        public ViewHolderHeader(LinearLayout layout) {
            super(layout);
            text = (TextView) layout.findViewById(R.id.event_list_header_text);
        }
    }

    public static class ViewHolderItem extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView time;
        public TextView name;

        public ViewHolderItem(LinearLayout layout) {
            super(layout);
            name = (TextView) layout.findViewById(R.id.event_list_item_name);
            time = (TextView) layout.findViewById(R.id.event_list_item_time);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        final int position = getAdapterPosition();
                        listener.onSelected(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object item = getItem(position);

        if (item instanceof WeekViewEvent) {
            return TYPE_ITEM;
        } else {
            return TYPE_HEADER;
        }
    }

    public Object getItem(final int position) {
        return events.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        context = parent.getContext();
        LinearLayout layout;
        RecyclerView.ViewHolder holder;

        if(viewType == TYPE_HEADER) {
            layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.event_list_header_item, parent, false);
            holder = new ViewHolderHeader(layout);
        } else {
            // create a new view
            layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.event_list_item, parent, false);

            if (!this.alreadyPaired) {
                layout.setAlpha(0.5f);
            }

            holder = new ViewHolderItem(layout);

        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_HEADER:
                ViewHolderHeader header = (ViewHolderHeader) holder;
                String text = (String) events.get(position);
                header.text.setText(text);
                break;
            case TYPE_ITEM:
                ViewHolderItem item = (ViewHolderItem) holder;
                WeekViewEvent event = (WeekViewEvent) events.get(position);
                item.time.setText(itemFormat.format(event.getStart()) + " - " + itemFormat.format(event.getEnd()));
                if (tutors != null) {
                    item.name.setText(getTutorName(event.getTutorID()));
                } else {
                    item.name.setVisibility(View.GONE);
                }
                break;
        }
    }

    private String getTutorName(int tutorID) {
        for (User tutor : tutors) {
            if (tutor.getId() == tutorID) {
                return tutor.getFirstName() + " " + tutor.getLastName();
            }
        }
        return "Couldn't find tutors name";
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
                this.events.add(headerFormat.format(curr.getStart()));
                this.events.add(curr);
            } else {
                prev = events.get(i - 1);
                prevDay = prev.getStartTime().get(Calendar.DAY_OF_YEAR);

                if (currDay != prevDay) {
                    this.events.add(headerFormat.format(curr.getStart()));
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

    @Override
    public int getItemCount() {
        return this.events.size();
    }
}
