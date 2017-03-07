package fi.tutee.tutee.data.source.remote;

import fi.tutee.tutee.data.entities.APIResponse;
import fi.tutee.tutee.data.entities.AuthResponse;
import fi.tutee.tutee.data.entities.LoginRequest;
import fi.tutee.tutee.data.entities.RegisterRequest;
import fi.tutee.tutee.data.entities.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by mat on 07/03/2017.
 */

public interface TuteeService {

    @POST("auth/login")
    Call<APIResponse<AuthResponse>> basicLogin(@Body LoginRequest req);

    @POST("auth/register")
    Call<User> register(@Body RegisterRequest req);
}
