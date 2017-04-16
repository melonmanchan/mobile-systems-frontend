package fi.tutee.tutee.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import fi.tutee.tutee.R;
import fi.tutee.tutee.data.entities.User;
import fi.tutee.tutee.data.entities.events.GeneralMessage;

public class UserChatListAdapter  extends ArrayAdapter<User> {
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

    public UserChatListAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<User> users) {
        super(context, resource, users);

        this.context = context;
        this.users = new ArrayList<User>();
        this.users.addAll(users);
    }

    public void setListener(OnUserSelectedListener listener) {
        this.listener = listener;
    }


    @Override
    public User getItem(final int position) {
        return users.get(position);
    }

    public void setLatestMessages(ArrayList<GeneralMessage> latestMessages) {
        this.latestMessages = latestMessages;
        notifyDataSetChanged();
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

        final User user = users.get(position);

        URI avatar = user.getAvatar();

        Picasso.with(context).load(avatar.toString()).into(holder.avatar);

        holder.userName.setText(user.getFirstName() + " " + user.getLastName());

        if(latestMessages == null) {
            holder.latestMessage.setText("Lorem ipsum lorem ipsum");
            holder.lastMessageSent.setText("1h");
        } else {
            for(GeneralMessage msg : latestMessages) {
                if (msg.getReceiverId() == user.getId() ||
                        msg.getSenderId() == user.getId()) {
                    holder.latestMessage.setText(msg.getContent());
                    //TODO: holder.lastMessageSent
                    break;
                }
            }
        }


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
