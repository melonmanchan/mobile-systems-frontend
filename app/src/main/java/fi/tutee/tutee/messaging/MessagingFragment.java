package fi.tutee.tutee.messaging;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fi.tutee.tutee.R;
import fi.tutee.tutee.adapters.MessageListAdapter;


/**
 * Created by lehtone1 on 12/04/17.
 */

public class MessagingFragment extends Fragment implements MessagingContract.View {



    private MessagingContract.Presenter presenter;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


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
        mRecyclerView = (RecyclerView) root.findViewById(R.id.messaging_view);
        mLayoutManager = new LinearLayoutManager(root.getContext());
        String[] messages = {"hei", "heihei", "asasd", "adaspdjalös", "jsajriw", "öasjdöas", "sjö", "hei", "heihei", "asasd", "adaspdjalös", "jsajriw", "öasjdöas", "sjö", "hei", "heihei", "asasd", "adaspdjalös", "jsajriw", "öasjdöas", "sjö", "hei", "heihei", "asasd", "adaspdjalös", "jsajriw", "öasjdöas", "sjö", "hei", "heihei", "asasd", "adaspdjalös", "jsajriw", "öasjdöas", "sjö", "hei", "heihei", "asasd", "adaspdjalös", "jsajriw", "öasjdöas", "sjö", "hei", "heihei", "asasd", "adaspdjalös", "jsajriw", "öasjdöas", "sjö", "hei", "heihei", "asasd", "adaspdjalös", "jsajriw", "öasjdöas", "sjö", "hei", "heihei", "asasd", "adaspdjalös", "jsajriw", "öasjdöas", "sjö", "hei", "heihei", "asasd", "adaspdjalös", "jsajriw", "öasjdöas", "sjö", "hei", "heihei", "asasd", "adaspdjalös", "jsajriw", "öasjdöas", "sjö", "hei", "heihei", "asasd", "adaspdjalös", "jsajriw", "öasjdöas", "sjö", "hei", "heihei", "asasd", "adaspdjalössfdsdffsfshgrgdfgfhgfhsfdsfdsfdsgfdsgdsgsdgdsgfdsgfdsgfdsgfsdgfsdgfsgfdsgfsdgfsdgfdsgfdsgfsgfsgfs fdfdsfgdsgfdsgfdsgfdsgfsgfsgfsgfdsgfsgfdgfsgfdsgfdsgfsgfsgsgfsgfsgfsgfdsgfsgfsgfgfdgfsgfdsgfgfs fdsgfdsgfsgfdsfgdsgfdsfgdsfgdsgfdsgfdgfdgfgfsfdsgfdsgdsgdfsgfdsgfsgfsgfsfsdgfdsgsfgsdfdsgdsgfgfsd", "jsajriw", "öasjdöas", "sjö", "hei", "heihei", "asasd", "adaspdjalös", "jsajriw", "öasjdöas", "sjö" };
        mAdapter = new MessageListAdapter(messages);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);





        return root;
    }



    public void setPresenter(MessagingContract.Presenter presenter) {
        this.presenter = presenter;
    }


}
