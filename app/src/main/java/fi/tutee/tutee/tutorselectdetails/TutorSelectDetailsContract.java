package fi.tutee.tutee.tutorselectdetails;

import fi.tutee.tutee.BasePresenter;
import fi.tutee.tutee.BaseView;
import fi.tutee.tutee.data.entities.User;

interface TutorSelectDetailsContract {

    interface View extends BaseView<Presenter> {
        void setTutor(User user);
    }

    interface Presenter extends BasePresenter {
        void getTutorByID(int tutorID);
    }
}
