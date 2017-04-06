package fi.tutee.tutee.selecttutor;

import java.util.ArrayList;

import fi.tutee.tutee.BasePresenter;
import fi.tutee.tutee.BaseView;
import fi.tutee.tutee.data.entities.Subject;
import fi.tutee.tutee.data.entities.User;

public interface SelectTutorContract {
    interface Presenter extends BasePresenter{

        void getTutors();
    }

    interface View extends BaseView<Presenter> {
        void setTutors(ArrayList<User> tutors);
        void setPresenter(SelectTutorPresenter presenter);
    }
}
