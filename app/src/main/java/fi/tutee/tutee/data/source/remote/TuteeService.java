package fi.tutee.tutee.data.source.remote;

import fi.tutee.tutee.data.LoginRequest;
import fi.tutee.tutee.data.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by mat on 07/03/2017.
 */

public interface TuteeService {

    @POST("auth/login")
    Call<User> basicLogin(@Body LoginRequest req);

    @POST("auth/register")
    Call<User> login();
}
