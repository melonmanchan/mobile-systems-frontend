package fi.tutee.tutee.profile;

import android.net.Uri;

import java.util.ArrayList;

import fi.tutee.tutee.BasePresenter;
import fi.tutee.tutee.BaseView;
import fi.tutee.tutee.data.entities.APIError;
import fi.tutee.tutee.data.entities.User;

/**
 * Created by emmilinkola on 15/03/17.
 */

public class ProfileContract {

    interface Presenter extends BasePresenter {
        void updateUser(String firstName, String lastName, int price);
        void changeAvatar(String avatarUri);
    }

    interface View extends BaseView<Presenter> {
        void setUser(User user);

        void onUpdateSuccess();
        void onUpdateFailure();

        void onAvatarFailed(ArrayList<APIError> errors );
        void onAvatarSuccess();
    }
}
