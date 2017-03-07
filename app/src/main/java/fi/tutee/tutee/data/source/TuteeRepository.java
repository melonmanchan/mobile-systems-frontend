package fi.tutee.tutee.data.source;

import fi.tutee.tutee.data.LoginRequest;
import fi.tutee.tutee.data.RegisterRequest;
import fi.tutee.tutee.data.User;
import fi.tutee.tutee.data.source.local.TuteeLocalDataSource;
import fi.tutee.tutee.data.source.remote.TuteeRemoteDataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TuteeRepository implements TuteeDataSource {

    private static TuteeRepository instance = null;

    private final TuteeRemoteDataSource remote;

    private final TuteeLocalDataSource local;

    private User loggedInUser;

    public User getLoggedInUser() {
        return loggedInUser;
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
    public void basicLogin(LoginRequest req, final Callback<User> cb) {

        remote.basicLogin(req, new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    loggedInUser = response.body();
                }

                cb.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                cb.onFailure(call, t);
            }
        });
    }

    @Override
    public void googleLogin(String token) {

    }

    @Override
    public void register(RegisterRequest req, final Callback<User> cb) {

        remote.register(req, new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    loggedInUser = response.body();
                }

                cb.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                cb.onFailure(call, t);
            }
        });
    }
}
