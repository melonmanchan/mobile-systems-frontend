package fi.tutee.tutee.registertutorextra;

import fi.tutee.tutee.data.entities.APIResponse;
import fi.tutee.tutee.data.entities.AuthResponse;
import fi.tutee.tutee.data.entities.RegisterRequest;
import fi.tutee.tutee.data.entities.RegisterTutorExtraRequest;
import fi.tutee.tutee.data.source.TuteeRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lehtone1 on 09/03/17.
 */

public class RegisterTutorExtraPresenter implements RegisterTutorExtraContract.Presenter {

    private final TuteeRepository repository;

    private final RegisterTutorExtraContract.View view;

    public RegisterTutorExtraPresenter(TuteeRepository repository,
                             RegisterTutorExtraContract.View view
    ) {
        this.repository = repository;
        this.view = view;

        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void registerTutorExtra(String description) {
        RegisterTutorExtraRequest req = new RegisterTutorExtraRequest(description);
        repository.registerTutorExtra(req, new Callback<APIResponse<AuthResponse>>() {
            @Override
            public void onResponse(Call<APIResponse<AuthResponse>> call, Response<APIResponse<AuthResponse>> response) {
                if (response.isSuccessful()) {
                    view.onRegisterSuccess();
                } else {
                    view.onRegisterFail();
                }
            }

            @Override
            public void onFailure(Call<APIResponse<AuthResponse>> call, Throwable t) {
                view.onRegisterFail();
            }
        });
    }
}
