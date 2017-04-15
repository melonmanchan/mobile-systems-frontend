package fi.tutee.tutee.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;

import fi.tutee.tutee.R;
import fi.tutee.tutee.data.entities.User;
import fi.tutee.tutee.data.entities.events.GeneralMessage;

/**
 * Created by lehtone1 on 12/04/17.
 */

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {
    private ArrayList<GeneralMessage> messages;
    private User thisUser;
    private User otherUser;
    private static int SENT_BY_THIS_USER = 0;
    private static int SENT_BY_OTHER_USER = 1;

    private HashSet<Integer> messageIDs = new HashSet<>();

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;

        public ViewHolder(LinearLayout layout) {
            super(layout);
            mTextView = (TextView) layout.findViewById(R.id.message_text);
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
        int otherUserId = otherUser.getId();

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
        LinearLayout layout;
        if (viewType == SENT_BY_THIS_USER) {
             layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                     .inflate(R.layout.message_list_item, parent, false);
        } else {
            // create a new view
            layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_list_item, parent, false);
        }
        ViewHolder vh = new ViewHolder(layout);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(messages.get(position).getContent());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addItem(GeneralMessage message) {
        if (messageIDs.contains(message.getId())) {
            return;
        }

        messages.add(message);
        notifyItemInserted(messages.size());
    }

    public void setMessages(ArrayList<GeneralMessage> messages) {
        this.messageIDs = new HashSet<Integer>();
        this.messages = messages;

        for (GeneralMessage message: messages) {
            messageIDs.add(message.getId());
        }

        notifyDataSetChanged();
    }
}


