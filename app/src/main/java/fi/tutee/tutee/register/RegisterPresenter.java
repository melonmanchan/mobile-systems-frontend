package fi.tutee.tutee.register;

import fi.tutee.tutee.data.entities.APIResponse;
import fi.tutee.tutee.data.entities.AuthResponse;
import fi.tutee.tutee.data.entities.RegisterRequest;
import fi.tutee.tutee.data.source.TuteeRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by emmilinkola on 08/03/17.
 */

public class RegisterPresenter implements RegisterContract.Presenter {

    private final TuteeRepository repository;

    private final RegisterContract.View view;

    public RegisterPresenter(TuteeRepository repository,
                                      RegisterContract.View view
    ) {
        this.repository = repository;
        this.view = view;

        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void register(String firstName, String lastName, String email, String password, String userType) {
        RegisterRequest req = new RegisterRequest(firstName, lastName, email, password, userType);
        repository.register(req, new Callback<APIResponse<AuthResponse>>() {
            @Override
            public void onResponse(Call<APIResponse<AuthResponse>> call, Response<APIResponse<AuthResponse>> response) {
                APIResponse resp = response.body();

                if (resp.isSuccessful()) {
                    view.onRegisterSuccess();
                } else {
                    view.onRegisterFail(resp.getErrors());
                }
            }

            @Override
            public void onFailure(Call<APIResponse<AuthResponse>> call, Throwable t) {
                view.onRegisterFail(null);
            }
        });
    }
}
