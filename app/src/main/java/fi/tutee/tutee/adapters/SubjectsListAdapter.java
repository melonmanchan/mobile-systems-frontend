package fi.tutee.tutee.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fi.tutee.tutee.R;
import fi.tutee.tutee.data.entities.Subject;


public class SubjectsListAdapter extends ArrayAdapter<Subject> {
    private ArrayList<Subject> subjects;

    public void setListener(OnSubjectSelectedListener listener) {
        this.listener = listener;
    }

    private OnSubjectSelectedListener listener;
    private Context context;
    private final int resource;

    public interface OnSubjectSelectedListener {
        void onSelected(Subject user);
    }

    //alternate white and lightgrey background
    private int[] colors = new int[] { 0x30ffffff, 0x30eaeaea };

    public SubjectsListAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<Subject> subjects) {
        super(context, resource, subjects);

        this.context = context;
        this.resource = resource;

        this.subjects = new ArrayList<Subject>();
        this.subjects.addAll(subjects);
    }

    class SubjectHolder {
        private View wrapper;
        private TextView name;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        SubjectHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.search_list_item, null);

            holder = new SubjectHolder();
            holder.wrapper = (View) convertView.findViewById(R.id.search_list_id);
            holder.name = (TextView) convertView.findViewById(R.id.search_list_name);

            convertView.setTag(holder);
        } else {
            holder = (SubjectHolder) convertView.getTag();
        }

        final Subject subject = subjects.get(position);

        int colorPos = position % colors.length;

        holder.wrapper.setBackgroundColor(colors[colorPos]);

        holder.name.setText(subject.getType());

        holder.wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    Subject subject = subjects.get(position);
                    listener.onSelected(subject);
                }
            }
        });

        return convertView;
    }

}

