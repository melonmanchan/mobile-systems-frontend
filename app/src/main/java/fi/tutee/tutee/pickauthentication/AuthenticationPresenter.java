package fi.tutee.tutee.pickauthentication;

import fi.tutee.tutee.data.entities.APIResponse;
import fi.tutee.tutee.data.entities.AuthResponse;
import fi.tutee.tutee.data.entities.DeviceRegisterRequest;
import fi.tutee.tutee.data.entities.LoginRequest;
import fi.tutee.tutee.data.source.TuteeRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public AuthResponse getAutoLoginInfo() {
        AuthResponse authResponse = repository.fetchPersistedUserInfo();
        return authResponse;
    }

    @Override
    public void login(String email, String password) {
        LoginRequest req = new LoginRequest(email, password);

        repository.basicLogin(req, new Callback<APIResponse<AuthResponse>>() {
            @Override
            public void onResponse(Call<APIResponse<AuthResponse>> call, Response<APIResponse<AuthResponse>> response) {
                APIResponse<AuthResponse> resp = response.body();

                if (resp.isSuccessful()) {
                    repository.registerUserDevice(new DeviceRegisterRequest());
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
