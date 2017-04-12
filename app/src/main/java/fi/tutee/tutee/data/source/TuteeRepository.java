package fi.tutee.tutee.data.source;

import android.text.TextUtils;

import com.google.firebase.iid.FirebaseInstanceId;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import fi.tutee.tutee.data.entities.APIResponse;
import fi.tutee.tutee.data.entities.AuthResponse;
import fi.tutee.tutee.data.entities.CreateTutorshipRequest;
import fi.tutee.tutee.data.entities.DeviceRegisterRequest;
import fi.tutee.tutee.data.entities.GetTutorsBySubjectRequest;
import fi.tutee.tutee.data.entities.LoginRequest;
import fi.tutee.tutee.data.entities.RegisterRequest;
import fi.tutee.tutee.data.entities.RegisterTutorExtraRequest;
import fi.tutee.tutee.data.entities.Subject;
import fi.tutee.tutee.data.entities.UpdateUserRequest;
import fi.tutee.tutee.data.entities.User;
import fi.tutee.tutee.data.source.local.TuteeLocalDataSource;
import fi.tutee.tutee.data.source.remote.TuteeRemoteDataSource;
import fi.tutee.tutee.utils.EmptyCallback;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TuteeRepository implements TuteeDataSource {

    private static TuteeRepository instance = null;

    private final TuteeRemoteDataSource remote;

    private final TuteeLocalDataSource local;

    private String deviceToken;

    private User loggedInUser;

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public TuteeRepository(TuteeRemoteDataSource remote, TuteeLocalDataSource local) {
        this.remote = remote;
        this.local = local;
    }

    public static TuteeRepository getInstance(TuteeRemoteDataSource remote,
                                              TuteeLocalDataSource local) {
        if (instance == null) {
            instance = new TuteeRepository(remote, local);
        }
        return instance;
    }

    @Override
    public void basicLogin(LoginRequest req, final Callback<APIResponse<AuthResponse>> cb) {
        remote.basicLogin(req, new Callback<APIResponse<AuthResponse>>() {
            @Override
            public void onResponse(Call<APIResponse<AuthResponse>> call, Response<APIResponse<AuthResponse>> response) {
                APIResponse<AuthResponse> resp = response.body();

                if (resp != null && resp.isSuccessful()) {
                    AuthResponse authResponse = resp.getResponse();

                    remote.setToken(authResponse.getToken());
                    local.persistUserLogin(authResponse);

                    loggedInUser = authResponse.getUser();
                }

                cb.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<APIResponse<AuthResponse>> call, Throwable t) {
                cb.onFailure(call, t);
            }
        });
    }

    @Override
    public void googleLogin(String token) {

    }

    @Override
    public void changeAvatar(MultipartBody.Part body, final Callback<APIResponse<User>> cb) {
        this.remote.changeAvatar(body, new Callback<APIResponse<User>>() {
            @Override
            public void onResponse(Call<APIResponse<User>> call, Response<APIResponse<User>> response) {
                APIResponse<User> resp = response.body();

                if (resp != null && resp.isSuccessful()) {
                    UpdateUserRequest updateUserRequest = new UpdateUserRequest(resp.getResponse());
                    local.updateUser(updateUserRequest, new EmptyCallback<APIResponse<User>>());

                    try {
                        loggedInUser.setAvatar(new URL(updateUserRequest.getUser().getAvatar().toString()));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }

                cb.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<APIResponse<User>> call, Throwable t) {
                cb.onFailure(call, t);
            }
        });
    }

    @Override
    public void register(RegisterRequest req, final Callback<APIResponse<AuthResponse>> cb) {
        remote.register(req, new Callback<APIResponse<AuthResponse>>() {
            @Override
            public void onResponse(Call<APIResponse<AuthResponse>> call, Response<APIResponse<AuthResponse>> response) {
                APIResponse<AuthResponse> resp = response.body();

                if (resp != null && resp.isSuccessful()) {
                    AuthResponse authResponse = resp.getResponse();

                    remote.setToken(authResponse.getToken());
                    local.persistUserLogin(authResponse);
                    loggedInUser = authResponse.getUser();
                }

                cb.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<APIResponse<AuthResponse>> call, Throwable t) {
                cb.onFailure(call, t);
            }
        });
    }

    @Override
    public void registerTutorExtra(final RegisterTutorExtraRequest req, final Callback<APIResponse> cb) {
        remote.registerTutorExtra(req, new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                APIResponse resp = response.body();

                if (resp.isSuccessful()) {
                    loggedInUser.setSubjects(req.getSubjects());
                }

                cb.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                cb.onFailure(call, t);
            }
        });
    }

    @Override
    public void registerUserDevice(DeviceRegisterRequest req) {
        try {
            if (this.deviceToken == null || TextUtils.isEmpty(deviceToken)) {
                this.deviceToken = FirebaseInstanceId.getInstance().getToken();
            }

            if (this.deviceToken != null) {
                req.setToken(this.deviceToken);
                this.remote.registerUserDevice(req);
            }

        } catch(NullPointerException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public void getUser(int userID, Callback<APIResponse<User>> cb) {
        local.getUser(userID, cb);
    }

    public AuthResponse fetchPersistedUserInfo() {
        AuthResponse authResponse = local.fetchPersistedUserLogin();

        if (authResponse != null) {
            if (authResponse.isValid()) {
                this.loggedInUser = authResponse.getUser();
                remote.setToken(authResponse.getToken());
            } else {
                local.logOut();
            }
        }

        return authResponse;
    }

    @Override
    public void updateUser(final UpdateUserRequest req, final Callback<APIResponse<User>> cb) {
        remote.updateUser(req, new Callback<APIResponse<User>>() {
            @Override
            public void onResponse(Call<APIResponse<User>> call, Response<APIResponse<User>> response) {
                APIResponse<User> resp = response.body();

                if (resp != null && resp.isSuccessful()) {
                    local.updateUser(req, new EmptyCallback<APIResponse<User>>());
                }

                cb.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<APIResponse<User>> call, Throwable t) {
                cb.onFailure(call, t);
            }
        });
    }

    @Override
    public void getSubjects(final Callback<APIResponse<ArrayList<Subject>>> cb) {
        if (this.local.hasCachedSubjects()) {
            this.local.getSubjects(cb);
        } else {
            this.remote.getSubjects(new Callback<APIResponse<ArrayList<Subject>>>() {
                @Override
                public void onResponse(Call<APIResponse<ArrayList<Subject>>> call, Response<APIResponse<ArrayList<Subject>>> response) {
                    APIResponse<ArrayList<Subject>> resp = response.body();

                    if (resp.isSuccessful()) {
                        ArrayList<Subject> subjects = resp.getResponse();
                        local.setCachedSubjects(subjects);
                    }

                    cb.onResponse(call, response);
                }

                @Override
                public void onFailure(Call<APIResponse<ArrayList<Subject>>> call, Throwable t) {
                    cb.onFailure(call, t);
                }
            });
            // TODO
        }
    }

    @Override
    public void getTutorsBySubject(int subjectID, final Callback<APIResponse<ArrayList<User>>> cb) {
        this.remote.getTutorsBySubject(subjectID, new Callback<APIResponse<ArrayList<User>>>() {
            @Override
            public void onResponse(Call<APIResponse<ArrayList<User>>> call, Response<APIResponse<ArrayList<User>>> response) {
                APIResponse<ArrayList<User>> resp = response.body();

                if (resp.isSuccessful()) {
                    local.setCachedUsers(resp.getResponse());
                }

                cb.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<APIResponse<ArrayList<User>>> call, Throwable t) {
                cb.onFailure(call, t);
            }
        });
    }

    @Override
    public void createTutorship(final CreateTutorshipRequest req, final Callback<APIResponse> cb) {
        this.remote.createTutorship(req, new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                APIResponse resp = response.body();

                if (resp.isSuccessful()) {
                    local.markUserAsTutor(req.getID());
                }

                cb.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                cb.onFailure(call, t);
            }
        });
    }

    @Override
    public void logOut() {
        this.remote.logOut();
        this.local.logOut();
    }
}
