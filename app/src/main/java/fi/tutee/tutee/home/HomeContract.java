package fi.tutee.tutee.home;

import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;

import fi.tutee.tutee.BasePresenter;
import fi.tutee.tutee.BaseView;
import fi.tutee.tutee.data.entities.Subject;
import fi.tutee.tutee.data.entities.TimesResponse;
import fi.tutee.tutee.data.entities.User;
import fi.tutee.tutee.data.entities.events.GeneralMessage;
import fi.tutee.tutee.usertypeselection.UserTypeSelectionContract;

public interface HomeContract {
    interface Presenter extends BasePresenter{
        void logOut();

        void getTutorships();

        void getSubjects();
        void getLatestMessages();

        void createFreeTime(WeekViewEvent event);
        void removeTime(WeekViewEvent event);

        User getUser();
        void getReservedTimes();
    }

    interface View extends BaseView<Presenter> {
        void setMessageUsers(ArrayList<User> users);
        void setSubjects(ArrayList<Subject> subjects);
        void setLatestMessages(ArrayList<GeneralMessage> latestMessages);

        void setTimes(TimesResponse events);
    }
}
