package fi.tutee.tutee.registertutorextra;

import java.util.ArrayList;

import fi.tutee.tutee.BasePresenter;
import fi.tutee.tutee.BaseView;
import fi.tutee.tutee.data.entities.APIError;
import fi.tutee.tutee.data.entities.APIResponse;
import fi.tutee.tutee.data.entities.Subject;

/**
 * Created by lehtone1 on 09/03/17.
 */

public interface RegisterExtraContract {

    interface View extends BaseView<Presenter> {
        void onRegisterSuccess();
        void onRegisterFail(ArrayList<APIError> errors);

        void setSubjects(ArrayList<Subject> subjects);
    }

    interface Presenter extends BasePresenter {
        void registerTutorExtra(String description, ArrayList<Subject> subjects, int price);
        void getSubjects();
    }
}
