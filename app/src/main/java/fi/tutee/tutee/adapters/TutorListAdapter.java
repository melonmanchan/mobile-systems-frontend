package fi.tutee.tutee.adapters;

/**
 * Created by lehtone1 on 05/04/17.
 */

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.vision.text.Line;
import com.google.android.gms.vision.text.Text;
import com.squareup.picasso.Picasso;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;
import java.util.HashSet;

import de.hdodenhof.circleimageview.CircleImageView;
import fi.tutee.tutee.R;
import fi.tutee.tutee.data.entities.Subject;
import fi.tutee.tutee.data.entities.User;

public class TutorListAdapter extends ArrayAdapter<User> {
    private ArrayList<User> tutors;
    private OnTutorSelectedListener listener;
    private Context context;
    private HashSet<Subject> subjectsAdded = new HashSet<>();

    //alternate white and lightgrey background
    private int[] colors = new int[] { 0x30ffffff, 0x30e2e2e2 };

    public interface OnTutorSelectedListener {
        void onSelected(User tutor);
    }

    public void setListener(OnTutorSelectedListener listener) {
        this.listener = listener;
    }

    public TutorListAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<User> tutors) {
        super(context, resource, tutors);

        this.context = context;
        this.tutors = new ArrayList<User>();
        this.tutors.addAll(tutors);
    }


    @Override
    public User getItem(final int position) {
        return tutors.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        User user = getItem(position);
        ArrayList<Subject> subjects = user.getSubjects();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.small_profile_item, null);
        }

        CircleImageView profilePicture = (CircleImageView) convertView.findViewById(R.id.small_profile_picture);
        TextView name = (TextView) convertView.findViewById(R.id.small_profile_name);
        TextView description = (TextView) convertView.findViewById(R.id.small_profile_description);
        FlowLayout subjectsWrapper = (FlowLayout)  convertView.findViewById(R.id.small_profile_subjects_wrapper);

        int colorPos = position % colors.length;
        convertView.setBackgroundColor(colors[colorPos]);

        initializeSubjectsWrapper(subjectsWrapper, subjects);

        Picasso.with(context).load(user.getAvatar().toString()).into(profilePicture);

        name.setText(user.getFirstName() + " " + user.getLastName());
        description.setText(user.getDescription());

        return convertView;
    }

    private void initializeSubjectsWrapper(FlowLayout wrapper, ArrayList<Subject> subjects) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (Subject s: subjects) {
            if (!subjectsAdded.contains(s)) {
                LinearLayout subjectTextLayout = (LinearLayout) inflater.inflate(R.layout.partial_rounded_textview, null);
                TextView subjectTextView = (TextView) subjectTextLayout.findViewById(R.id.subject_name);
                subjectTextView.setText(s.getType());
                wrapper.addView(subjectTextLayout);
            }
        }

        subjectsAdded.addAll(subjects);
    }
}


