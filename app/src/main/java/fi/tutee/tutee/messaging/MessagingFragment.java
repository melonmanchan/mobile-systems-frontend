package fi.tutee.tutee.messaging;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fi.tutee.tutee.R;



/**
 * Created by lehtone1 on 12/04/17.
 */

public class MessagingFragment extends Fragment implements MessagingContract.View {



    private MessagingContract.Presenter presenter;

    public static MessagingFragment newInstance() {
        Bundle arguments = new Bundle();
        MessagingFragment fragment = new MessagingFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.start();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.content_messaging, container, false);


        return root;
    }



    public void setPresenter(MessagingContract.Presenter presenter) {
        this.presenter = presenter;
    }


}
