package fi.tutee.tutee.data.source.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.Map;

import fi.tutee.tutee.data.entities.APIResponse;
import fi.tutee.tutee.data.entities.AuthResponse;
import fi.tutee.tutee.data.entities.DeviceRegisterRequest;
import fi.tutee.tutee.data.entities.GetTutorsBySubjectRequest;
import fi.tutee.tutee.data.entities.LoginRequest;
import fi.tutee.tutee.data.entities.RegisterRequest;
import fi.tutee.tutee.data.entities.RegisterTutorExtraRequest;
import fi.tutee.tutee.data.entities.Subject;
import fi.tutee.tutee.data.entities.UpdateUserRequest;
import fi.tutee.tutee.data.entities.User;
import fi.tutee.tutee.data.source.TuteeDataSource;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mat on 06/03/2017.
 */

public class TuteeLocalDataSource implements TuteeDataSource{
    private static TuteeLocalDataSource instance;
    private SharedPreferences pref;
    private Gson gson;

    private ArrayList<Subject> cachedSubjects;

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
    public void logOut() {
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(PERSIST_LOGIN_DATA);
        editor.commit();
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
    public void getTutorsBySubject(GetTutorsBySubjectRequest req, Callback<APIResponse<ArrayList<User>>> cb) {

    }

    public void setCachedSubjects(ArrayList<Subject> cachedSubjects) {
        this.cachedSubjects = cachedSubjects;
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
