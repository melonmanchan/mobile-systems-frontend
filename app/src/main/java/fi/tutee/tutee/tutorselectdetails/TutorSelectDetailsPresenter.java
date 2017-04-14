package fi.tutee.tutee.tutorselectdetails;

import java.util.ArrayList;

import fi.tutee.tutee.data.entities.APIError;
import fi.tutee.tutee.data.entities.APIResponse;
import fi.tutee.tutee.data.entities.CreateTutorshipRequest;
import fi.tutee.tutee.data.entities.User;
import fi.tutee.tutee.data.source.TuteeRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TutorSelectDetailsPresenter implements TutorSelectDetailsContract.Presenter {
    private final TuteeRepository repository;

    private final TutorSelectDetailsContract.View view;

    public TutorSelectDetailsPresenter(TuteeRepository repository, TutorSelectDetailsContract.View view) {
        this.repository = repository;
        this.view = view;

        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getTutorByID(int tutorID) {
        repository.getUser(tutorID, new Callback<APIResponse<User>>() {
            @Override
            public void onResponse(Call<APIResponse<User>> call, Response<APIResponse<User>> response) {
                APIResponse<User> resp = response.body();

                if (resp.isSuccessful()) {
                    view.setTutor(resp.getResponse());
                }
            }

            @Override
            public void onFailure(Call<APIResponse<User>> call, Throwable t) {
                // TODO
            }
        });
    }

    @Override
    public void pairWithTutor(int tutorID) {
        CreateTutorshipRequest req = new CreateTutorshipRequest(tutorID);

        repository.createTutorship(req, new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                APIResponse resp = response.body();

                if (resp.isSuccessful()) {
                    view.pairTutorSucceeded();
                } else {
                    view.pairTutorFailed(resp.getErrors());
                }
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                view.pairTutorFailed(null);
            }
        });
    }

    @Override
    public boolean alreadyPairedWith(User user) {
        return repository.isUserTutor(user);
    }
}
