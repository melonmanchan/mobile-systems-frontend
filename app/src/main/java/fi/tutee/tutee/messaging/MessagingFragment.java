package fi.tutee.tutee.messaging;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.wearable.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fi.tutee.tutee.R;
import fi.tutee.tutee.adapters.MessageListAdapter;
import fi.tutee.tutee.data.entities.APIError;
import fi.tutee.tutee.data.entities.User;
import fi.tutee.tutee.data.entities.events.GeneralMessage;
import fi.tutee.tutee.home.HomeMessagesFragment;


/**
 * Created by lehtone1 on 12/04/17.
 */

public class MessagingFragment extends Fragment implements MessagingContract.View {



    private MessagingContract.Presenter presenter;
    private RecyclerView mRecyclerView;
    private MessageListAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private EditText writeMessage;
    private Button sendMessage;
    private int otherUserId;



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
        //event.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.content_messaging, container, false);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.messaging_view);
        mLayoutManager = new LinearLayoutManager(root.getContext());
        ArrayList<GeneralMessage> a = new ArrayList<>();
        mAdapter = new MessageListAdapter(a);

        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        writeMessage = (EditText) root.findViewById(R.id.write_message);
        sendMessage = (Button) root.findViewById(R.id.send_message_button);

        otherUserId = getArguments().getInt(HomeMessagesFragment.USER_ID);
        getUser();
        getMessages();

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTempMessage(writeMessage.getText().toString());
            }
        });

        return root;
    }

    public void scrollToBottom() {
        mRecyclerView.scrollToPosition(mAdapter.getItemCount()-1);
    }

    public void addTempMessage(String message) {
        GeneralMessage generalMessage = new GeneralMessage();
        generalMessage.setContent(message);
        addMessage(generalMessage);
        writeMessage.setText("");
    }

    public void addMessage(GeneralMessage message) {
        mAdapter.addItem(message);
        scrollToBottom();
    }

    public void getMessages() {
        presenter.getMessagesFrom(otherUserId);
    }

    public void getUser() {
        presenter.getUserByID(otherUserId);
    }


    @Override
    public void setUser(User user) {
        getActivity().setTitle(user.getFirstName());
    }

    public void setPresenter(MessagingContract.Presenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void setMessages(ArrayList<GeneralMessage> messages) {
        mAdapter.setMessages(messages);
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
