package fi.tutee.tutee.data.source.remote;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;

import fi.tutee.tutee.data.entities.APIResponse;
import fi.tutee.tutee.data.entities.AuthResponse;
import fi.tutee.tutee.data.entities.CreateFreeTimeRequest;
import fi.tutee.tutee.data.entities.CreateMessageRequest;
import fi.tutee.tutee.data.entities.CreateTutorshipRequest;
import fi.tutee.tutee.data.entities.DeviceRegisterRequest;
import fi.tutee.tutee.data.entities.LoginRequest;
import fi.tutee.tutee.data.entities.RegisterRequest;
import fi.tutee.tutee.data.entities.RegisterTutorExtraRequest;
import fi.tutee.tutee.data.entities.Subject;
import fi.tutee.tutee.data.entities.TimesResponse;
import fi.tutee.tutee.data.entities.TutorshipsResponse;
import fi.tutee.tutee.data.entities.UpdateUserRequest;
import fi.tutee.tutee.data.entities.User;
import fi.tutee.tutee.data.entities.events.GeneralMessage;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface TuteeService {

    @GET("subject/")
    Call<APIResponse<ArrayList<Subject>>> getSubjects();

    @GET("subject/{id}/tutors")
    Call<APIResponse<ArrayList<User>>> getTutorsBySubject(@Path("id") int subjectID);

    @POST("auth/login")
    Call<APIResponse<AuthResponse>> basicLogin(@Body LoginRequest req);

    @POST("user/register_device")
    Call<APIResponse> registerUserDevice(@Body DeviceRegisterRequest req);

    @PUT("user/remove_device")
    Call<APIResponse> removeUserDevice(@Body DeviceRegisterRequest req);

    @POST("auth/register")
    Call<APIResponse<AuthResponse>> register(@Body RegisterRequest req);

    @POST("user/register_tutor_extra")
    Call<APIResponse> registerTutorExtra(@Body RegisterTutorExtraRequest req);

    @PUT("user/update_profile")
    Call<APIResponse<User>> updateUser(@Body UpdateUserRequest req);

    @Multipart
    @POST("user/change_avatar")
    Call<APIResponse<User>> changeAvatar(@Part MultipartBody.Part file);

    @POST("tutorship/")
    Call<APIResponse> createTutorship(@Body CreateTutorshipRequest req);

    @GET("tutorship/")
    Call<APIResponse<TutorshipsResponse>> getTutorships();

    @GET("message/{id}")
    Call<APIResponse<ArrayList<GeneralMessage>>> getMessagesFrom(@Path("id") int userId);

    @POST("message/")
    Call<APIResponse<GeneralMessage>> createMessage(@Body CreateMessageRequest req);

    @GET("message/latest")
    Call<APIResponse<ArrayList<GeneralMessage>>> getLatestMessages();

    @POST("event/")
    Call<APIResponse> createFreeTime(@Body CreateFreeTimeRequest req);

    @DELETE("event/")
    Call<APIResponse> removeTime(@Body WeekViewEvent event);

    @GET("event/{id}")
    Call<APIResponse<ArrayList<WeekViewEvent>>> getFreeTimes(@Path("id") int tutorID);

    @GET("event/")
    Call<APIResponse<TimesResponse>> getTimes();




}
