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


    public EventListAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<Object> objects) {
        super(context, resource, objects);

        this.context = context;
        this.events = objects;


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
}
