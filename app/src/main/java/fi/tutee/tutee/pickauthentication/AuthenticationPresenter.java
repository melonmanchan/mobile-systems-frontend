package fi.tutee.tutee.pickauthentication;

import fi.tutee.tutee.data.APIError;
import fi.tutee.tutee.data.LoginRequest;
import fi.tutee.tutee.data.User;
import fi.tutee.tutee.data.source.TuteeRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mat on 06/03/2017.
 */

public class AuthenticationPresenter implements AuthenticationContract.Presenter {

    private final TuteeRepository repository;

    private final AuthenticationContract.View view;

    public AuthenticationPresenter(TuteeRepository repository,
                                      AuthenticationContract.View view
    ) {
        this.repository = repository;
        this.view = view;

        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void login(String email, String password) {
        LoginRequest req = new LoginRequest(email, password);
        repository.basicLogin(req, new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    view.loginSucceeded();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                view.loginFailed();
            }
        });
    }
}
