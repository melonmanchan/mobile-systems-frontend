package fi.tutee.tutee.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import fi.tutee.tutee.R;
import fi.tutee.tutee.adapters.UserChatListAdapter;
import fi.tutee.tutee.data.entities.User;

public class HomeMessagesFragment extends Fragment {
    private ListView messageUsersList;
    private HomeContract.Presenter presenter;

    public HomeMessagesFragment() {}

    public void setPresenter(HomeContract.Presenter presenter) {
        this.presenter = presenter;
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

        this.presenter.getMessageUsers();

        return root;
    }

    public void setMessageUsers(ArrayList<User> users) {
        UserChatListAdapter adapter = new UserChatListAdapter(getContext(), R.layout.partial_user_message_list_item, users);
        messageUsersList.setAdapter(adapter);
    }
}
