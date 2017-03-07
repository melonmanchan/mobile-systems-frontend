package fi.tutee.tutee.data.source.local;

import fi.tutee.tutee.data.LoginRequest;
import fi.tutee.tutee.data.RegisterRequest;
import fi.tutee.tutee.data.User;
import fi.tutee.tutee.data.source.TuteeDataSource;
import retrofit2.Callback;

/**
 * Created by mat on 06/03/2017.
 */

public class TuteeLocalDataSource implements TuteeDataSource{
    private  static TuteeLocalDataSource instance;

    public static TuteeLocalDataSource getInstance() {
        if (instance == null) {
            instance = new TuteeLocalDataSource();
        }

        return instance;
    }

    @Override
    public void basicLogin(LoginRequest req, Callback<User> cb) {
        cb.onFailure(null, new UnsupportedOperationException("Register not implemented for local data source"));
    }

    @Override
    public void googleLogin(String token) {
    }

    @Override
    public void register(RegisterRequest req, Callback<User> cb) {
        cb.onFailure(null, new UnsupportedOperationException("Register not implemented for local data source"));
    }
}
