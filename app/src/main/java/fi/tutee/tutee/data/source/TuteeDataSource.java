package fi.tutee.tutee.data.source;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.Date;

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
import retrofit2.Callback;

/**
 * Created by mat on 06/03/2017.
 */

public interface TuteeDataSource {
    // API-related methods
    void basicLogin(LoginRequest req, Callback<APIResponse<AuthResponse>> cb);

    void googleLogin(String token);

    void changeAvatar(MultipartBody.Part body, Callback<APIResponse<User>> cb);

    void logOut();

    void register(RegisterRequest req, Callback<APIResponse<AuthResponse>> cb);

    void registerTutorExtra(RegisterTutorExtraRequest req, Callback<APIResponse> cb);

    void registerUserDevice(DeviceRegisterRequest req);

    void removeUserDevice(DeviceRegisterRequest req);

    void getUser(int userID, Callback<APIResponse<User>> cb);

    void updateUser(UpdateUserRequest req, Callback<APIResponse<User>> cb);

    void getSubjects(Callback<APIResponse<ArrayList<Subject>>> cb);

    void getTutorsBySubject(int subjectID, Callback<APIResponse<ArrayList<User>>> cb);

    void getMessagesFrom(int userId, Callback<APIResponse<ArrayList<GeneralMessage>>> cb);

    void createTutorship(CreateTutorshipRequest req, Callback<APIResponse> cb);

    void getTutorships(Callback<APIResponse<TutorshipsResponse>> cb);

    void createMessage(CreateMessageRequest req, Callback<APIResponse<GeneralMessage>> cb);

    void getLatestMessages(Callback<APIResponse<ArrayList<GeneralMessage>>> cb);

    void createFreeTime(CreateFreeTimeRequest req, Callback<APIResponse> cb);

    void removeTime(WeekViewEvent event, Callback<APIResponse> cb);

    void getFreeTimes(int tutorID, Callback<APIResponse<ArrayList<WeekViewEvent>>> cb);

    void getTimes(Callback<APIResponse<TimesResponse>> cb);


    // Other misc. helpers
    boolean isUserTutor(User user);
}
