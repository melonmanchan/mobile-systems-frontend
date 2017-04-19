package fi.tutee.tutee.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;

import fi.tutee.tutee.R;
import fi.tutee.tutee.adapters.UserChatListAdapter;
import fi.tutee.tutee.data.entities.User;
import fi.tutee.tutee.data.entities.events.GeneralMessage;
import fi.tutee.tutee.data.entities.events.LatestMessagesChangedEvent;
import fi.tutee.tutee.messaging.MessagingActivity;

public class HomeMessagesFragment extends HomeBaseFragment {
    private ListView messageUsersList;
    public final static String USER_ID = "fi.tutee.tutee.USER_ID";

    private ArrayList<GeneralMessage> latestMessages = null;
    private ArrayList<User> chatUsers = null;

    private UserChatListAdapter adapter;

    public HomeMessagesFragment() {}

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LatestMessagesChangedEvent event) {
        this.presenter.getLatestMessages();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.content_home_messages, container, false);

        this.messageUsersList = (ListView) root.findViewById(R.id.messages_user_list);
        TextView emptyView = (TextView) root.findViewById(R.id.messages_user_list_empty);
        messageUsersList.setEmptyView(emptyView);

        return root;
    }

    private void addPlaceHolderMessagesIfNeeded() {
        if (latestMessages.size() == chatUsers.size()) {
            return;
        }

        // We have more tuteeships than messages! Need to create a dummy message
        HashSet<Integer> userIds = new HashSet<Integer>();

        for (User u: chatUsers) {
            userIds.add(u.getId());
        }

        for (GeneralMessage m: latestMessages) {
            int receiverID = m.getReceiverId();
            int senderID = m.getSenderId();

            if (userIds.contains(receiverID)) {
                userIds.remove(receiverID);
            } else if (userIds.contains(senderID)) {
                userIds.remove(senderID);
            }
        }

        // Now we only have the user IDs who don't have any messages
        // Create a temporary message for them!
        for (int i: userIds) {
            GeneralMessage temp = new GeneralMessage();
            temp.setSentAt(new Date());
            temp.setContent("");
            temp.setSenderId(i);
            temp.setReceiverId(i);
            temp.setId(new Random().nextInt(Integer.MAX_VALUE));
            latestMessages.add(0, temp);
        }
    }

    private void checkIfShouldInitializeMessageArray() {
        if (chatUsers != null && latestMessages != null) {
            addPlaceHolderMessagesIfNeeded();

            if (adapter == null) {

                adapter = new UserChatListAdapter(getContext(), R.layout.partial_user_message_list_item, latestMessages, chatUsers);

                adapter.setListener(new UserChatListAdapter.OnUserSelectedListener() {
                    @Override
                    public void onSelected(User user) {
                        Intent intent = new Intent(getActivity(), MessagingActivity.class);
                        intent.putExtra(USER_ID, user.getId());
                        startActivity(intent);
                    }
                });

                messageUsersList.setAdapter(adapter);
            }

            adapter.setLatestMessages(latestMessages);
        }
    }

    public void setMessageUsers(ArrayList<User> users) {
        this.chatUsers = users;
        checkIfShouldInitializeMessageArray();
    }

    public void setLatestMessages(ArrayList<GeneralMessage> latestMessages) {
        Collections.sort(latestMessages, new Comparator<GeneralMessage>() {
            @Override
            public int compare(GeneralMessage msg1, GeneralMessage msg2) {
                return msg2.getSentAt().compareTo(msg1.getSentAt());
            }
        });

        this.latestMessages = latestMessages;
        checkIfShouldInitializeMessageArray();
    }

    @Override
    public void onResume() {
        this.presenter.getLatestMessages();
        this.presenter.getTutorships();
        super.onResume();
    }
}
