package fi.tutee.tutee.data.source.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import fi.tutee.tutee.data.entities.APIResponse;
import fi.tutee.tutee.data.entities.AuthResponse;
import fi.tutee.tutee.data.entities.DeviceRegisterRequest;
import fi.tutee.tutee.data.entities.LoginRequest;
import fi.tutee.tutee.data.entities.RegisterRequest;
import fi.tutee.tutee.data.entities.RegisterTutorExtraRequest;
import fi.tutee.tutee.data.source.TuteeDataSource;
import retrofit2.Callback;

/**
 * Created by mat on 06/03/2017.
 */

public class TuteeLocalDataSource implements TuteeDataSource{
    private static TuteeLocalDataSource instance;

    private static String PERSIST_LOGIN_DATA = "fi.tutee.tutee.PERSIST_LOGIN_DATA";

    private Context context;

    public TuteeLocalDataSource(Context context) {
        this.context = context;
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
    public void register(RegisterRequest req, Callback<APIResponse<AuthResponse>> cb) {
        cb.onFailure(null, new UnsupportedOperationException("Register not implemented for local data source"));
    }

    @Override
    public void registerTutorExtra(RegisterTutorExtraRequest req, Callback<APIResponse<AuthResponse>> cb) {
        cb.onFailure(null, new UnsupportedOperationException("Register not implemented for local data source"));
    }

    @Override
    public void registerUserDevice(DeviceRegisterRequest req) {
        // No-op
    }

    @Override
    public void logOut() {
        SharedPreferences mySPrefs =PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = mySPrefs.edit();
        editor.remove(PERSIST_LOGIN_DATA);
        editor.apply();
    }

    public void persistUserLogin(AuthResponse authResponse) {
        Gson gson = new Gson();
        String json = gson.toJson(authResponse);
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PERSIST_LOGIN_DATA, json).apply();
    }

    public AuthResponse fetchPersistedUserLogin() {
        Gson gson = new Gson();
        String json = PreferenceManager.getDefaultSharedPreferences(context).getString(PERSIST_LOGIN_DATA, null);

        if (json == null) {
            return null;
        }

        return gson.fromJson(json, AuthResponse.class);
    }
}
