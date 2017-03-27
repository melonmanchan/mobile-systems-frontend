package fi.tutee.tutee.profile;

import fi.tutee.tutee.data.entities.APIResponse;
import fi.tutee.tutee.data.entities.UpdateUserRequest;
import fi.tutee.tutee.data.entities.User;
import fi.tutee.tutee.data.source.TuteeRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by emmilinkola on 15/03/17.
 */

public class ProfilePresenter implements ProfileContract.Presenter {
    private final TuteeRepository repository;
    private final ProfileContract.View view;
    private User user;

    public ProfilePresenter(TuteeRepository repository,
                             ProfileContract.View view,
                            User user
    ) {
        this.repository = repository;
        this.view = view;
        this.user = user;

        view.setPresenter(this);
    }

    @Override
    public void start() {
        view.setUser(user);
    }

    public void updateUser(String firstName, String lastName) {
        User user = repository.getLoggedInUser();

        user.setFirstName(firstName);
        user.setLastName(lastName);

        UpdateUserRequest req = new UpdateUserRequest(user);

        repository.updateUser(req, new Callback<APIResponse<User>>() {
            @Override
            public void onResponse(Call<APIResponse<User>> call, Response<APIResponse<User>> response) {
                APIResponse<User> resp = response.body();

                if (resp.isSuccessful()) {
                    view.onUpdateSuccess();
                } else {
                    view.onUpdateFailure();
                }
            }

            @Override
            public void onFailure(Call<APIResponse<User>> call, Throwable t) {
                view.onUpdateFailure();
            }
        });

    }
}
