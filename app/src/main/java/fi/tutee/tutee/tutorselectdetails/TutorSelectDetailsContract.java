package fi.tutee.tutee.tutorselectdetails;

import java.util.ArrayList;

import fi.tutee.tutee.BasePresenter;
import fi.tutee.tutee.BaseView;
import fi.tutee.tutee.data.entities.APIError;
import fi.tutee.tutee.data.entities.User;

interface TutorSelectDetailsContract {

    interface View extends BaseView<Presenter> {
        void setTutor(User user);

        void pairTutorSucceeded();
        void pairTutorFailed(ArrayList<APIError> errors);
    }

    interface Presenter extends BasePresenter {
        void getTutorByID(int tutorID);
        void pairWithTutor(int tutorID);

        boolean alreadyPairedWith(User user);
    }
}
