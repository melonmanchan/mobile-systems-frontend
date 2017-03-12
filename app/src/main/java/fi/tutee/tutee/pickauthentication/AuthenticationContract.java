package fi.tutee.tutee.pickauthentication;

import java.util.ArrayList;

import fi.tutee.tutee.BasePresenter;
import fi.tutee.tutee.BaseView;
import fi.tutee.tutee.data.entities.APIError;
import fi.tutee.tutee.data.entities.User;

/**
 * Created by mat on 06/03/2017.
 */

public interface AuthenticationContract {
    interface View extends BaseView<Presenter> {
        void loginSucceeded(User currentUser);

        void loginFailed(ArrayList<APIError> errors);
    }

    interface Presenter extends BasePresenter {
        void login(String username, String password);
    }
}
