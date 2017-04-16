package fi.tutee.tutee.adapters;

import android.content.Context;
import android.location.GnssClock;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import java.net.URI;
import java.util.ArrayList;
import fi.tutee.tutee.R;
import fi.tutee.tutee.data.entities.User;
import fi.tutee.tutee.data.entities.events.GeneralMessage;

import static android.text.format.DateUtils.FORMAT_ABBREV_RELATIVE;
import static android.text.format.DateUtils.MINUTE_IN_MILLIS;

public class UserChatListAdapter  extends ArrayAdapter<GeneralMessage> {
    private ArrayList<User> users;
    private ArrayList<GeneralMessage> latestMessages;
    private OnUserSelectedListener listener;
    private Context context;

    public interface OnUserSelectedListener {
        void onSelected(User user);
    }

    public class UserChatListHolder {
        public View wrapper;

        public ImageView avatar;

        public TextView userName;
        public TextView latestMessage;
        public TextView lastMessageSent;
    }

    public UserChatListAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<GeneralMessage> messages, ArrayList<User> users) {
        super(context, resource, messages);

        this.context = context;
        this.latestMessages = new ArrayList<GeneralMessage>();
        this.latestMessages.addAll(messages);

        this.users = new ArrayList<User>();
        this.users.addAll(users);
    }

    public void setListener(OnUserSelectedListener listener) {
        this.listener = listener;
    }

    @Override
    public GeneralMessage getItem(final int position) {
        return latestMessages.get(position);
    }

    public void setLatestMessages(ArrayList<GeneralMessage> latestMessages) {
        this.latestMessages.clear();
        this.latestMessages.addAll(latestMessages);
        notifyDataSetChanged();
    }

    public User getUserForMessage(GeneralMessage message) {
        User user = null;

        int sender = message.getSenderId();
        int receiver = message.getReceiverId();

        for (User u: users) {
            if (u.getId() == sender || u.getId() == receiver) {
                user = u;
            }
        }

        return user;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        UserChatListHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.partial_user_message_list_item, null);

            holder = new UserChatListHolder();

            holder.wrapper = (View) convertView.findViewById(R.id.partial_message_wrapper);

            holder.avatar = (ImageView) convertView.findViewById(R.id.partial_message_avatar);
            holder.userName = (TextView) convertView.findViewById(R.id.partial_message_name);

            holder.latestMessage = (TextView) convertView.findViewById(R.id.partial_message_content);
            holder.lastMessageSent = (TextView) convertView.findViewById(R.id.partial_message_last_sent);

            convertView.setTag(holder);
        } else {
            holder = (UserChatListHolder) convertView.getTag();
        }

        final GeneralMessage message = latestMessages.get(position);
        final User user = getUserForMessage(message);

        URI avatar = user.getAvatar();

        Picasso.with(context).load(avatar.toString()).into(holder.avatar);

        holder.userName.setText(user.getFirstName() + " " + user.getLastName());

        holder.latestMessage.setText(message.getContent());
        long now = System.currentTimeMillis();
        CharSequence date = DateUtils.getRelativeTimeSpanString(message.getSentAt().getTime(), now,  MINUTE_IN_MILLIS, FORMAT_ABBREV_RELATIVE);

        if (date.toString().indexOf("min. ago") > -1) {
            date = date.toString().replaceAll("\\D+"," m");
        } else if (date.toString().indexOf("hr. ago") > -1) {
            date = date.toString().replaceAll("\\D+"," h");
        }

        holder.lastMessageSent.setText(date);

        holder.wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onSelected(user);
                }
            }
        });

        return convertView;
    }
}
