package fi.tutee.tutee.selecttutor;

import java.util.ArrayList;

import fi.tutee.tutee.BasePresenter;
import fi.tutee.tutee.BaseView;
import fi.tutee.tutee.data.entities.APIError;
import fi.tutee.tutee.data.entities.Subject;
import fi.tutee.tutee.data.entities.User;

public interface SelectTutorContract {
    interface Presenter extends BasePresenter{

        void getTutorsBySubjectID(int subjectID);
    }

    interface View extends BaseView<Presenter> {
        void setTutors(ArrayList<User> tutors);

        void getTutorsFailed(ArrayList<APIError> errors);
    }
}
