package fi.tutee.tutee.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import fi.tutee.tutee.R;
import fi.tutee.tutee.data.entities.Subject;

public class SubjectExtraListAdapter extends ArrayAdapter<Subject>{
    private ArrayList<Subject> subjects;
    private Set<Integer> selectedSubjectIds;
    private Context context;

    private  OnSubjectExtraSelectedListener listener;

    public SubjectExtraListAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<Subject> subjects) {
        super(context, resource, subjects);

        this.context = context;
        this.subjects = new ArrayList<Subject>();
        this.subjects.addAll(subjects);

        this.selectedSubjectIds = new HashSet<Integer>();
    }

    public interface OnSubjectExtraSelectedListener {
        void onSelected(Subject subject);
        void onDeselected(Subject subject);
    }

    protected class SubjectExtraHolder {
        View wrapper;
        TextView name;
        CheckBox selected;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        SubjectExtraHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.search_list_item, null);

            holder = new SubjectExtraHolder();
            holder.wrapper = (View) convertView.findViewById(R.id.search_list_id);

            convertView.setTag(holder);
        } else {
            holder = (SubjectExtraHolder) convertView.getTag();
        }

        final Subject subject = subjects.get(position);

        holder.wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    return;
                }

                Integer id = subject.getId();

                if (selectedSubjectIds.contains(id)) {
                    selectedSubjectIds.remove(id);
                    listener.onDeselected(subject);
                } else {
                    selectedSubjectIds.add(id);
                    listener.onSelected(subject);
                }
            }
        });

        return convertView;
    }
}
