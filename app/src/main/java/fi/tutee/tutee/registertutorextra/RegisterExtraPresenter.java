package fi.tutee.tutee.registertutorextra;

import java.util.ArrayList;

import fi.tutee.tutee.data.entities.APIResponse;
import fi.tutee.tutee.data.entities.RegisterTutorExtraRequest;
import fi.tutee.tutee.data.entities.Subject;
import fi.tutee.tutee.data.source.TuteeRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lehtone1 on 09/03/17.
 */

public class RegisterExtraPresenter implements RegisterExtraContract.Presenter {

    private final TuteeRepository repository;

    private final RegisterExtraContract.View view;

    public RegisterExtraPresenter(TuteeRepository repository,
                                  RegisterExtraContract.View view
    ) {
        this.repository = repository;
        this.view = view;

        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void registerTutorExtra(String description, ArrayList<Subject> subjects) {
        RegisterTutorExtraRequest req = new RegisterTutorExtraRequest(description, subjects);

        repository.registerTutorExtra(req, new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                APIResponse resp = response.body();

                if (resp.isSuccessful()) {
                    view.onRegisterSuccess();
                } else {
                    view.onRegisterFail();
                }
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                view.onRegisterFail();
            }
        });
    }

    @Override
    public void getSubjects() {
        this.repository.getSubjects(new Callback<APIResponse<ArrayList<Subject>>>() {
            @Override
            public void onResponse(Call<APIResponse<ArrayList<Subject>>> call, Response<APIResponse<ArrayList<Subject>>> response) {
                APIResponse<ArrayList<Subject>> resp = response.body();

                if (resp.isSuccessful()) {
                    ArrayList<Subject> subjects = resp.getResponse();
                    view.setSubjects(subjects);
                }
            }

            @Override
            public void onFailure(Call<APIResponse<ArrayList<Subject>>> call, Throwable t) {
                // TODO
            }
        });

    }
}
