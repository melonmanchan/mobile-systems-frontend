package fi.tutee.tutee.selecttutor;

import java.util.ArrayList;

import fi.tutee.tutee.data.entities.APIResponse;
import fi.tutee.tutee.data.entities.Subject;
import fi.tutee.tutee.data.entities.User;
import fi.tutee.tutee.data.source.TuteeRepository;
import fi.tutee.tutee.home.HomeContract;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectTutorPresenter implements SelectTutorContract.Presenter {
    private final TuteeRepository repository;
    private final SelectTutorContract.View view;

    public SelectTutorPresenter(TuteeRepository repository,
                                SelectTutorContract.View view
    ) {
        this.repository = repository;
        this.view = view;

        view.setPresenter(this);
    }


    @Override
    public void getTutors(String subject) {
        this.repository.getTutors(new Callback<APIResponse<ArrayList<User>>>() {
            @Override
            public void onResponse(Call<APIResponse<ArrayList<User>>> call, Response<APIResponse<ArrayList<User>>> response) {
                APIResponse<ArrayList<User>> resp = response.body();

                if (resp.isSuccessful()) {
                    ArrayList<User> tutors = resp.getResponse();
                    view.setTutors(tutors);
                }
            }

            @Override
            public void onFailure(Call<APIResponse<ArrayList<User>>> call, Throwable t) {
                // TODO
            }
        });
    }

    @Override
    public void start() {

    }
}
