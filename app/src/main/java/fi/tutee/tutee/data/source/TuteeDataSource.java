package fi.tutee.tutee.data.source;

import fi.tutee.tutee.data.User;

/**
 * Created by mat on 06/03/2017.
 */

public interface TuteeDataSource {
    interface LoginCallback {
        void onSuccess(User user);
        void onFailure();
    }


    void basicLogin(String email, String password);
    void googleLogin(String token);
}
