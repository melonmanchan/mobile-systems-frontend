package fi.tutee.tutee.profile;

import android.net.Uri;

import fi.tutee.tutee.BasePresenter;
import fi.tutee.tutee.BaseView;
import fi.tutee.tutee.data.entities.User;

/**
 * Created by emmilinkola on 15/03/17.
 */

public class ProfileContract {

    interface Presenter extends BasePresenter {
        void updateUser(String firstName, String lastName);
        void changeAvatar(Uri avatarUri);
    }

    interface View extends BaseView<Presenter> {
        void setUser(User user);
        void onUpdateSuccess();
        void onUpdateFailure();
    }
}
