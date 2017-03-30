package fi.tutee.tutee.data.source.remote;

import fi.tutee.tutee.data.entities.APIResponse;
import fi.tutee.tutee.data.entities.AuthResponse;
import fi.tutee.tutee.data.entities.DeviceRegisterRequest;
import fi.tutee.tutee.data.entities.LoginRequest;
import fi.tutee.tutee.data.entities.RegisterRequest;
import fi.tutee.tutee.data.entities.RegisterTutorExtraRequest;
import fi.tutee.tutee.data.entities.UpdateUserRequest;
import fi.tutee.tutee.data.entities.User;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

/**
 * Created by mat on 07/03/2017.
 */

public interface TuteeService {

    @POST("auth/login")
    Call<APIResponse<AuthResponse>> basicLogin(@Body LoginRequest req);

    @POST("user/register_device")
    Call<APIResponse> registerUserDevice(@Body DeviceRegisterRequest req);

    @POST("auth/register")
    Call<APIResponse<AuthResponse>> register(@Body RegisterRequest req);

    @POST("auth/registerTutorExtra")
    Call registerTutorExtra(@Body RegisterTutorExtraRequest req);

    @PUT("user/update_profile")
    Call<APIResponse<User>> updateUser(@Body UpdateUserRequest req);

    @Multipart
    @PUT("user/change_avatar")
    Call<APIResponse<User>> changeAvatar(@Part MultipartBody.Part file);
}
