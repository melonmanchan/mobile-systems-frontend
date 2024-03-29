package fi.tutee.tutee.messaging;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.wearable.MessageEvent;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import fi.tutee.tutee.R;
import fi.tutee.tutee.adapters.MessageListAdapter;
import fi.tutee.tutee.data.entities.APIError;
import fi.tutee.tutee.data.entities.CreateMessageRequest;
import fi.tutee.tutee.data.entities.User;
import fi.tutee.tutee.data.entities.events.GeneralMessage;
import fi.tutee.tutee.home.HomeMessagesFragment;
import fi.tutee.tutee.reservecalendar.ReserveCalendarActivity;
import fi.tutee.tutee.tutorselectdetails.TutorSelectDetailsFragment;

import static fi.tutee.tutee.tutorselectdetails.TutorSelectDetailsFragment.TUTOR_ID;

public class MessagingFragment extends Fragment implements MessagingContract.View {
    private MessagingContract.Presenter presenter;
    private RecyclerView mRecyclerView;
    private MessageListAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private EditText writeMessage;
    private Button sendMessage;
    private int otherUserId;
    private User user;
    private User otherUser;

    private int showMessageFrom = 0;
    private int showMessageTo = 10;
    private int messageIncrement = 10;

    private  boolean isOtherUserTutor = true;

    boolean moreMessagesLeft = true;

    public static MessagingFragment newInstance(int userId) {
        Bundle arguments = new Bundle();
        MessagingFragment fragment = new MessagingFragment();
        arguments.putInt(HomeMessagesFragment.USER_ID, userId);
        fragment.setArguments(arguments);
        return fragment;
    }

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

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.start();
        }
    }

    @Subscribe (threadMode = ThreadMode.MAIN)
    public void onMessageEvent(GeneralMessage message) {
        addMessage(message);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_go_to_calendar:
                Intent intent = new Intent(getActivity(), ReserveCalendarActivity.class);
                intent.putExtra(TUTOR_ID, otherUserId);
                intent.putExtra(TutorSelectDetailsFragment.ALREADY_PAIRED, true);
                intent.putExtra(TutorSelectDetailsFragment.TUTOR_PRICE, otherUser.getPrice());
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.content_messaging, container, false);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.messaging_view);
        mLayoutManager = new LinearLayoutManager(root.getContext());

        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        writeMessage = (EditText) root.findViewById(R.id.write_message);
        sendMessage = (Button) root.findViewById(R.id.send_message_button);

        otherUserId = getArguments().getInt(HomeMessagesFragment.USER_ID);

        getUser();
        getMessages();

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = writeMessage.getText().toString();

                if (!TextUtils.isEmpty(message)) {
                    createMessage(otherUserId, message);
                    addTempMessage(writeMessage.getText().toString());
                }
            }
        });

        setHasOptionsMenu(true);
        return root;
    }

    public boolean isOtherUserTutor() {
        return isOtherUserTutor;
    }

    private void createMessage(int receiver, String content) {
        presenter.createMessage(receiver, content);
    }

    public void scrollToBottom() {
        mRecyclerView.scrollToPosition(mAdapter.getItemCount()-1);
    }

    public void addTempMessage(String message) {
        GeneralMessage generalMessage = new GeneralMessage();
        generalMessage.setContent(message);
        generalMessage.setReceiverId(otherUserId);
        generalMessage.setSenderId(user.getId());
        generalMessage.setSentAt(new Date());
        addMessage(generalMessage);
        writeMessage.setText("");
    }

    public void addMessage(GeneralMessage message) {
        mAdapter.addItem(message);
        scrollToBottom();
    }

    public void getMessages() {
        presenter.getMessagesFrom(otherUserId, showMessageFrom, showMessageTo);
    }

    public void getUser() {
        presenter.getUserByID(otherUserId);

    }

    @Override
    public void setOtherUser(User user) {
        otherUser = user;
        getActivity().setTitle(user.getFirstName());
        AppCompatActivity parent = (AppCompatActivity) getActivity();

        parent.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        parent.getSupportActionBar().setCustomView(R.layout.actionbar_message);

        if (!user.isTutor()) {
            isOtherUserTutor = false;
            parent.invalidateOptionsMenu();
        }

        TextView title = (TextView) parent.getSupportActionBar().getCustomView().findViewById(R.id.action_bar_title);
        ImageView avatar = (ImageView)  parent.getSupportActionBar().getCustomView().findViewById(R.id.action_bar_avatar);

        title.setText(user.getFirstName() + " " + user.getLastName());
        Picasso.with(getContext()).load(user.getAvatar().toString()).into(avatar);

        ArrayList<GeneralMessage> a = new ArrayList<>();
        mAdapter = new MessageListAdapter(a, this.user, user);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (moreMessagesLeft && !recyclerView.canScrollVertically(-1)) {
                    showMessageFrom = showMessageTo;
                    showMessageTo += messageIncrement;
                    getMessages();
                }
            }
        });
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void createMessageSucceeded(CreateMessageRequest req) {
        // TODO?
    }

    @Override
    public void createMessageFailed(CreateMessageRequest req, ArrayList<APIError> errors) {
        String errorMessage = "Something went wrong!";

        if (errors != null) {
            errorMessage = errors.get(0).getMessage();
        }

        Snackbar.make(getView(), errorMessage, Snackbar.LENGTH_LONG).show();
    }

    public void setPresenter(MessagingContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void addMessages(ArrayList<GeneralMessage> messages) {
        if (messages.size() < messageIncrement) {
            moreMessagesLeft = false;
        }

        Collections.sort(messages, new Comparator<GeneralMessage>() {
            @Override
            public int compare(GeneralMessage msg1, GeneralMessage msg2) {
                return msg1.getSentAt().compareTo(msg2.getSentAt());
            }
        });

        mAdapter.addMessages(messages);
    }

    @Override
    public void getMessagesFailed(ArrayList<APIError> errors) {
        String errorMessage = "Something went wrong!";

        if (errors != null) {
            errorMessage = errors.get(0).getMessage();
        }

        Snackbar.make(getView(), errorMessage, Snackbar.LENGTH_LONG).show();
    }

}
