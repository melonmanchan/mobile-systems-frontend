package fi.tutee.tutee.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import fi.tutee.tutee.R;

/**
 * Created by mat on 12/03/2017.
 */

public class HomeFragment extends Fragment implements HomeContract.View {
    private HomeContract.Presenter presenter;


    public static HomeFragment newInstance() {
        Bundle arguments = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.content_home, container, false);

        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_logout:
                Toast.makeText(getContext(), "asdasda", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
