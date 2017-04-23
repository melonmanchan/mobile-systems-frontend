package fi.tutee.tutee.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.List;

import fi.tutee.tutee.R;
import fi.tutee.tutee.data.entities.Subject;
import fi.tutee.tutee.data.entities.TimesResponse;
import fi.tutee.tutee.data.entities.User;
import fi.tutee.tutee.data.entities.events.GeneralMessage;
import fi.tutee.tutee.pickauthentication.AuthenticationActivity;
import fi.tutee.tutee.profile.ProfileActivity;

/**
 * Created by mat on 12/03/2017.
 */

public class HomeFragment extends Fragment implements HomeContract.View {
    private HomeContract.Presenter presenter;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private HomeMessagesFragment messagesFragment;
    private HomeTutorScheduleFragment tutorScheduleFragment;
    private HomeTuteeScheduleFragment tuteeScheduleFragment;
    private HomeSearchFragment searchFragment;

    private int[] tabIcons = {
            R.drawable.ic_perm_contact_calendar_white_24dp,
            R.drawable.ic_message_white_24dp,
            R.drawable.ic_search_white_24dp,
    };

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

        viewPager = (ViewPager) root.findViewById(R.id.homeViewPager);
        tabLayout = (TabLayout) root.findViewById(R.id.homeTabs);

        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabIcons.length; i++) {
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);
            tabLayout.getTabAt(i).setCustomView(R.layout.tab_item);
        }

        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_logout:
                presenter.logOut();
                Intent intent = new Intent(getContext(), AuthenticationActivity.class);
                startActivity(intent);
                break;
            case R.id.action_profile:
                Intent profileIntent = new Intent(getContext(), ProfileActivity.class);
                startActivity(profileIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPager.setOffscreenPageLimit(3);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());

        if (presenter.getUser().isTutor()) {
            this.tutorScheduleFragment = new HomeTutorScheduleFragment();
            this.tutorScheduleFragment.setPresenter(this.presenter);
            adapter.addFragment(tutorScheduleFragment, "");
        } else {
            this.tuteeScheduleFragment = new HomeTuteeScheduleFragment();
            this.tuteeScheduleFragment.setPresenter(this.presenter);
            adapter.addFragment(tuteeScheduleFragment, "");
        }

        this.messagesFragment = new HomeMessagesFragment();
        this.searchFragment = new HomeSearchFragment();

        this.messagesFragment.setPresenter(this.presenter);
        this.searchFragment.setPresenter(this.presenter);

        adapter.addFragment(messagesFragment, "");
        adapter.addFragment(searchFragment, "");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void setMessageUsers(ArrayList<User> users) {
        this.messagesFragment.setMessageUsers(users);
    }

    @Override
    public void setTutorNames(ArrayList<User> tutors) {
        if (this.tuteeScheduleFragment != null) {
            this.tuteeScheduleFragment.setTutorNames(tutors);
        }
    }

    @Override
    public void setTuteeNames(ArrayList<User> tutees) {
        if (this.tutorScheduleFragment != null) {
            this.tutorScheduleFragment.setTuteeNames(tutees);
        }
    }

    @Override
    public void setSubjects(ArrayList<Subject> subjects) {
        this.searchFragment.setSubjects(subjects);
    }

    @Override
    public void setLatestMessages(ArrayList<GeneralMessage> latestMessages) {
        this.messagesFragment.setLatestMessages(latestMessages);
    }

    @Override
    public void setTimes(TimesResponse events) {
        if (this.presenter.getUser().isTutor()) {
            this.tutorScheduleFragment.setTimes(events);
        } else {
            this.tuteeScheduleFragment.setTimes(events);
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
