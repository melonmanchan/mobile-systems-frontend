package fi.tutee.tutee.home;

import java.util.ArrayList;

import fi.tutee.tutee.BasePresenter;
import fi.tutee.tutee.BaseView;
import fi.tutee.tutee.data.entities.Subject;
import fi.tutee.tutee.data.entities.User;
import fi.tutee.tutee.data.entities.events.GeneralMessage;
import fi.tutee.tutee.usertypeselection.UserTypeSelectionContract;

public interface HomeContract {
    interface Presenter extends BasePresenter{
        void logOut();

        void getTutorships();
        void getMessages();
        void getSubjects();
        void getLatestMessages();
    }

    interface View extends BaseView<Presenter> {
        void setMessageUsers(ArrayList<User> users);
        void setSubjects(ArrayList<Subject> subjects);
        void setLatestMessages(ArrayList<GeneralMessage> latestMessages);
    }
}
