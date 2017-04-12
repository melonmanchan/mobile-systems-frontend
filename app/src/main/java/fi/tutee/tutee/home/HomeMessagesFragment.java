package fi.tutee.tutee.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import fi.tutee.tutee.R;
import fi.tutee.tutee.adapters.UserChatListAdapter;
import fi.tutee.tutee.data.entities.User;
import fi.tutee.tutee.messaging.MessagingActivity;

public class HomeMessagesFragment extends HomeBaseFragment {
    private ListView messageUsersList;
    public final static String USER_ID = "fi.tutee.tutee.USER_ID";

    public HomeMessagesFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.content_home_messages, container, false);

        this.messageUsersList = (ListView) root.findViewById(R.id.messages_user_list);

        this.presenter.getMessageUsers();

        return root;
    }

    public void setMessageUsers(ArrayList<User> users) {
        UserChatListAdapter adapter = new UserChatListAdapter(getContext(), R.layout.partial_user_message_list_item, users);
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
}
