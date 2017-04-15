package fi.tutee.tutee.data.source.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashSet;

import fi.tutee.tutee.data.entities.APIResponse;
import fi.tutee.tutee.data.entities.AuthResponse;
import fi.tutee.tutee.data.entities.CreateMessageRequest;
import fi.tutee.tutee.data.entities.CreateTutorshipRequest;
import fi.tutee.tutee.data.entities.DeviceRegisterRequest;
import fi.tutee.tutee.data.entities.LoginRequest;
import fi.tutee.tutee.data.entities.RegisterRequest;
import fi.tutee.tutee.data.entities.RegisterTutorExtraRequest;
import fi.tutee.tutee.data.entities.Subject;
import fi.tutee.tutee.data.entities.TutorshipsResponse;
import fi.tutee.tutee.data.entities.UpdateUserRequest;
import fi.tutee.tutee.data.entities.User;
import fi.tutee.tutee.data.entities.events.GeneralMessage;
import fi.tutee.tutee.data.source.TuteeDataSource;
import okhttp3.MultipartBody;
import retrofit2.Callback;
import retrofit2.Response;

public class TuteeLocalDataSource implements TuteeDataSource {
    private static TuteeLocalDataSource instance;
    private SharedPreferences pref;
    private Gson gson;

    private HashSet<Integer> tutorIDs = new HashSet<Integer>();
    private HashSet<Integer> tuteeIDs = new HashSet<Integer>();

    private ArrayList<Subject> cachedSubjects;

    private SparseArray<User> cachedUsers;

    private static String PERSIST_LOGIN_DATA = "fi.tutee.tutee.PERSIST_LOGIN_DATA";

    private Context context;

    public TuteeLocalDataSource(Context context) {
        this.context = context;

        gson = new Gson();
        pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static TuteeLocalDataSource getInstance(Context context) {
        if (instance == null) {
            instance = new TuteeLocalDataSource(context);
        }

        return instance;
    }

    @Override
    public void basicLogin(LoginRequest req, Callback<APIResponse<AuthResponse>> cb) {
        cb.onFailure(null, new UnsupportedOperationException("Register not implemented for local data source"));
    }

    @Override
    public void googleLogin(String token) {
    }

    @Override
    public void changeAvatar(MultipartBody.Part body, Callback<APIResponse<User>> cb) {
        cb.onFailure(null, new Exception("Cannot change local avatar"));
    }

    @Override
    public void register(RegisterRequest req, Callback<APIResponse<AuthResponse>> cb) {
        cb.onFailure(null, new UnsupportedOperationException("Register not implemented for local data source"));
    }

    @Override
    public void registerTutorExtra(RegisterTutorExtraRequest req, Callback<APIResponse> cb) {
        cb.onFailure(null, new UnsupportedOperationException("Register not implemented for local data source"));
    }

    @Override
    public void registerUserDevice(DeviceRegisterRequest req) {
        // No-op
    }

    @Override
    public void getUser(int userID, Callback<APIResponse<User>> cb) {
        APIResponse<User> apiResponse = new APIResponse<User>();
        User user = this.cachedUsers.get(userID);
        apiResponse.setResponse(user);
        apiResponse.setStatus(200);
        Response<APIResponse<User>> resp = retrofit2.Response.success(apiResponse);
        cb.onResponse(null, resp);
    }

    @Override
    public void logOut() {
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(PERSIST_LOGIN_DATA);
        editor.commit();

        cachedSubjects = new ArrayList<Subject>();
        cachedUsers = new SparseArray<User>();
        tutorIDs = new HashSet<Integer>();
        tuteeIDs = new HashSet<Integer>();
    }

    public void persistUserLogin(AuthResponse authResponse) {
        String json = gson.toJson(authResponse);

        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PERSIST_LOGIN_DATA, json);
        editor.commit();
    }

    @Override
    public void updateUser(UpdateUserRequest req, Callback<APIResponse<User>> cb) {
        AuthResponse persistedAuthResponse = fetchPersistedUserLogin();

        if (persistedAuthResponse != null) {
            persistedAuthResponse.setUser(req.getUser());
            persistUserLogin(persistedAuthResponse);
        }
    }

    @Override
    public void getSubjects(Callback<APIResponse<ArrayList<Subject>>> cb) {
        if (this.hasCachedSubjects()) {
            APIResponse<ArrayList<Subject>> apiResponse = new APIResponse<ArrayList<Subject>>();
            apiResponse.setResponse(cachedSubjects);
            apiResponse.setStatus(200);
            Response<APIResponse<ArrayList<Subject>>> resp = retrofit2.Response.success(apiResponse);
            cb.onResponse(null, resp);
        }
    }

    @Override
    public void getTutorsBySubject(int subjectID, Callback<APIResponse<ArrayList<User>>> cb) {
        cb.onFailure(null, new Exception("Not yet implemented!"));
    }

    @Override
    public void getMessagesFrom(int userId, Callback<APIResponse<ArrayList<GeneralMessage>>> cb) {
        cb.onFailure(null, new Exception("Not yet implemented!"));
    }

    @Override
    public void createTutorship(CreateTutorshipRequest req, Callback<APIResponse> cb) {
        cb.onFailure(null, new Exception("Cannot perform action locally"));
    }

    @Override
    public void getTutorships(Callback<APIResponse<TutorshipsResponse>> cb) {
        if (this.hasCachedTutorships()) {
            TutorshipsResponse tutorshipsResponse = new TutorshipsResponse();

            tutorshipsResponse.setTutees(getCachedTutees());
            tutorshipsResponse.setTutors(getCachedTutors());

            APIResponse<TutorshipsResponse> apiResponse = new APIResponse<TutorshipsResponse>();
            apiResponse.setResponse(tutorshipsResponse);
            apiResponse.setStatus(200);
            Response<APIResponse<TutorshipsResponse>> resp = retrofit2.Response.success(apiResponse);
            cb.onResponse(null, resp);
        }
    }

    @Override
    public void createMessage(CreateMessageRequest req, Callback<APIResponse> cb) {
        cb.onFailure(null, new Exception("Cannot create message locally"));
    }

    @Override
    public boolean isUserTutor(User user) {
        return tutorIDs.contains(user.getId());
    }

    public ArrayList<User> getCachedTutees() {
        ArrayList<User> users = new ArrayList<>();

        for (Integer i: tuteeIDs) {
            users.add(cachedUsers.get(i));
        }

        return  users;
    }

    public ArrayList<User> getCachedTutors() {
        ArrayList<User> users = new ArrayList<>();

        for (Integer i: tutorIDs) {
            users.add(cachedUsers.get(i));
        }

        return  users;
    }

    public void setCachedTutors(ArrayList<User> tutors) {
        if (tutorIDs == null) {
            tutorIDs = new HashSet<>();
        }

        for (User u: tutors) {
            tutorIDs.add(u.getId());
        }

        setCachedUsers(tutors);
    }

    public void setCachedTutees(ArrayList<User> tutees) {
        if (tuteeIDs == null) {
            tuteeIDs = new HashSet<>();
        }

        for (User u: tutees) {
            tuteeIDs.add(u.getId());
        }

        setCachedUsers(tutees);
    }

    public void setCachedUsers(ArrayList<User> users) {
        if (cachedUsers == null) {
            cachedUsers = new SparseArray<User>();
        }

        for (User u: users) {
            int id = u.getId();
            cachedUsers.put(id, u);
        }
    }

    public void markUserAsTutor(int tutorID) {
        if (!tutorIDs.contains(tutorID)) {
            tutorIDs.add(tutorID);
        }
    }

    public void setCachedSubjects(ArrayList<Subject> cachedSubjects) {
        this.cachedSubjects = cachedSubjects;
    }

    public boolean hasCachedTutorships() {
        return (this.tuteeIDs != null && this.tutorIDs != null && this.cachedUsers != null);
    }

    public boolean hasCachedSubjects() {
        return (this.cachedSubjects != null && this.cachedSubjects.size() > 0);
    }

    public AuthResponse fetchPersistedUserLogin() {
        String json = pref.getString(PERSIST_LOGIN_DATA, null);

        if (json == null) {
            return null;
        }


        try {
            return gson.fromJson(json, AuthResponse.class);
        } catch(JsonSyntaxException ex) {
            this.logOut();
            return null;
        }
    }
}
