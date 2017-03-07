package fi.tutee.tutee.data.source;

import fi.tutee.tutee.data.entities.APIResponse;
import fi.tutee.tutee.data.entities.AuthResponse;
import fi.tutee.tutee.data.entities.LoginRequest;
import fi.tutee.tutee.data.entities.RegisterRequest;
import fi.tutee.tutee.data.entities.User;
import retrofit2.Callback;

/**
 * Created by mat on 06/03/2017.
 */

public interface TuteeDataSource {
    void basicLogin(LoginRequest req, Callback<APIResponse<AuthResponse>> cb);
    void googleLogin(String token);

    void register(RegisterRequest req, Callback<User> cb);
}
