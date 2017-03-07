package fi.tutee.tutee.data.source;

import fi.tutee.tutee.data.LoginRequest;
import fi.tutee.tutee.data.RegisterRequest;
import fi.tutee.tutee.data.User;
import retrofit2.Callback;

/**
 * Created by mat on 06/03/2017.
 */

public interface TuteeDataSource {
    void basicLogin(LoginRequest req, Callback<User> cb);
    void googleLogin(String token);

    void register(RegisterRequest req, Callback<User> cb);
}
