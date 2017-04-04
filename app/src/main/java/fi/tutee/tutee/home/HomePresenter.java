package fi.tutee.tutee.home;

import java.util.ArrayList;

import fi.tutee.tutee.data.entities.APIResponse;
import fi.tutee.tutee.data.entities.Subject;
import fi.tutee.tutee.data.entities.User;
import fi.tutee.tutee.data.source.TuteeRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter implements HomeContract.Presenter {
    private final TuteeRepository repository;

    private final HomeContract.View view;

    public HomePresenter(TuteeRepository repository,
                                      HomeContract.View view
    ) {
        this.repository = repository;
        this.view = view;

        view.setPresenter(this);
    }
    @Override
    public void start() {

    }

    @Override
    public void logOut() {
        this.repository.logOut();
    }

    @Override
    public void getMessageUsers() {
        User user = this.repository.getLoggedInUser();

        ArrayList<User> users = new ArrayList<>();
        users.add(user);

        this.view.setMessageUsers(users);
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
