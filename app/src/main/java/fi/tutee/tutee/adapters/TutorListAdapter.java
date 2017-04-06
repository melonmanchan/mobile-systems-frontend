package fi.tutee.tutee.adapters;

/**
 * Created by lehtone1 on 05/04/17.
 */

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

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import fi.tutee.tutee.R;
import fi.tutee.tutee.data.entities.Subject;
import fi.tutee.tutee.data.entities.User;


public class TutorListAdapter extends ArrayAdapter<User> {
    private ArrayList<User> tutors;
    private OnTutorSelectedListener listener;
    private Context context;

    public interface OnTutorSelectedListener {
        void onSelected(User tutor);
    }

    public void setListener(OnTutorSelectedListener listener) {
        this.listener = listener;
    }

    //alternate white and lightgrey background
    private int[] colors = new int[] { 0x30ffffff, 0x30eaeaea };

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

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.small_profile_item, null);


        }

        CircleImageView profilePicture = (CircleImageView) convertView.findViewById(R.id.small_profile_picture);
        TextView name = (TextView) convertView.findViewById(R.id.small_prfile_name);
        TextView skills = (TextView) convertView.findViewById(R.id.small_profile_skills);
        TextView classesAmount = (TextView) convertView.findViewById(R.id.small_profile_classes_done_number);

        Picasso.with(context).load(user.getAvatar().toString()).into(profilePicture);

        name.setText(user.getFirstName() + " " + user.getLastName());
        skills.setText("Inset skills here");
        classesAmount.setText("amount");



        final User tutor = tutors.get(position);

        int colorPos = position % colors.length;

        convertView.setBackgroundColor(colorPos);




        return convertView;
    }

}


