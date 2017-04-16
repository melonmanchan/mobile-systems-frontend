package fi.tutee.tutee.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import android.text.format.DateUtils;

import fi.tutee.tutee.R;
import fi.tutee.tutee.data.entities.User;
import fi.tutee.tutee.data.entities.events.GeneralMessage;

import static android.text.format.DateUtils.MINUTE_IN_MILLIS;
import static android.text.format.DateUtils.WEEK_IN_MILLIS;

/**
 * Created by lehtone1 on 12/04/17.
 */

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {
    private ArrayList<GeneralMessage> messages;
    private User thisUser;
    private User otherUser;
    private static int SENT_BY_THIS_USER = 0;
    private static int SENT_BY_OTHER_USER = 1;
    private Context context;

    private HashSet<Integer> messageIDs = new HashSet<>();

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView messageContent;
        public TextView messageSent;

        public ViewHolder(LinearLayout layout) {
            super(layout);
            messageContent = (TextView) layout.findViewById(R.id.message_text);
            messageSent = (TextView) layout.findViewById(R.id.message_sent_time);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MessageListAdapter(ArrayList<GeneralMessage> messages, User thisUser, User otherUser) {
        this.thisUser = thisUser;
        this.otherUser = otherUser;
        this.messages = messages;

        for (GeneralMessage m: messages) {
            messageIDs.add(m.getId());
        }
    }

    @Override
    public int getItemViewType(int position) {
        GeneralMessage message = this.messages.get(position);
        int thisUserId = thisUser.getId();

        if (message.getSenderId() == thisUserId) {
            return SENT_BY_THIS_USER;
        } else {
            return SENT_BY_OTHER_USER;
        }

    }

    // Create new views (invoked by the layout manager)
    @Override
    public MessageListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        context = parent.getContext();
        LinearLayout layout;
        if (viewType == SENT_BY_THIS_USER) {
             layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                     .inflate(R.layout.message_sent_by_this_user, parent, false);
        } else {
            // create a new view
            layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_sent_by_other_user, parent, false);
        }
        ViewHolder vh = new ViewHolder(layout);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.messageContent.setText(messages.get(position).getContent());
        CharSequence date = DateUtils.getRelativeDateTimeString(context, messages.get(position).getSentAt().getTime(), MINUTE_IN_MILLIS,  WEEK_IN_MILLIS, 0);
        holder.messageSent.setText(date);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addItem(GeneralMessage message) {
        int receiverId = message.getReceiverId();
        int senderId = message.getSenderId();

        int thisUserId = thisUser.getId();
        int otherUserId = otherUser.getId();

        if (receiverId != thisUserId && senderId != thisUserId) {
            return;
        }

        if (receiverId != otherUserId && senderId != otherUserId) {
            return;
        }

        messages.add(message);
        notifyItemInserted(messages.size());
    }

    public void setMessages(ArrayList<GeneralMessage> messages) {
        this.messageIDs = new HashSet<Integer>();
        this.messages = messages;

        int thisUserId = thisUser.getId();
        int otherUserId = otherUser.getId();

        for (GeneralMessage message: messages) {
            int receiverId = message.getReceiverId();
            int senderId = message.getSenderId();

            if (receiverId != thisUserId && senderId != thisUserId) {
                return;
            }

            if (receiverId != otherUserId && senderId != otherUserId) {
                return;
            }

            messageIDs.add(message.getId());
        }

        notifyDataSetChanged();
    }
}
