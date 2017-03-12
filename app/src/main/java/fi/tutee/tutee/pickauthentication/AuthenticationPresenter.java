package fi.tutee.tutee.pickauthentication;

import java.util.ArrayList;
import java.util.List;

import fi.tutee.tutee.data.entities.APIResponse;
import fi.tutee.tutee.data.entities.AuthResponse;
import fi.tutee.tutee.data.entities.LoginRequest;
import fi.tutee.tutee.data.entities.User;
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
        repository.basicLogin(req, new Callback<APIResponse<AuthResponse>>() {
            @Override
            public void onResponse(Call<APIResponse<AuthResponse>> call, Response<APIResponse<AuthResponse>> response) {
                APIResponse<AuthResponse> resp = response.body();

                if (resp.isSuccessful()) {
                    AuthResponse authResponse = resp.getResponse();
                    view.loginSucceeded(authResponse.getUser());
                } else {
                    view.loginFailed(resp.getErrors());
                }
            }

            @Override
            public void onFailure(Call<APIResponse<AuthResponse>> call, Throwable t) {
                view.loginFailed(null);
            }
        });
    }
}
