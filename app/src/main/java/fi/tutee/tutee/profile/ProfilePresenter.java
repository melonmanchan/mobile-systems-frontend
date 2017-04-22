package fi.tutee.tutee.profile;

import android.content.Context;
import android.net.Uri;

import java.io.File;

import fi.tutee.tutee.data.entities.APIResponse;
import fi.tutee.tutee.data.entities.UpdateUserRequest;
import fi.tutee.tutee.data.entities.User;
import fi.tutee.tutee.data.source.TuteeRepository;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by emmilinkola on 15/03/17.
 */

public class ProfilePresenter implements ProfileContract.Presenter {
    private final TuteeRepository repository;
    private final ProfileContract.View view;
    private Context context;
    private User user;

    public ProfilePresenter(TuteeRepository repository,
                            ProfileContract.View view,
                            User user,
                            Context context
    ) {
        this.repository = repository;
        this.view = view;
        this.user = user;
        this.context = context;

        view.setPresenter(this);
    }

    @Override
    public void start() {
        view.setUser(user);
    }

    public void updateUser(String firstName, String lastName, String description, int price) {
        User user = repository.getLoggedInUser();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDescription(description);
        user.setPrice(price);

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

    @Override
    public void changeAvatar(String avatarUri) {
        File avatarFile = new File(avatarUri);
        RequestBody req = RequestBody.create(MediaType.parse("image/*"), avatarFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", avatarFile.getName(), req);

        this.repository.changeAvatar(body, new Callback<APIResponse<User>>() {
            @Override
            public void onResponse(Call<APIResponse<User>> call, Response<APIResponse<User>> response) {
                APIResponse resp = response.body();
                if (resp.isSuccessful()) {
                    view.onAvatarSuccess();
                } else {
                    view.onAvatarFailed(resp.getErrors());
                }
            }

            @Override
            public void onFailure(Call<APIResponse<User>> call, Throwable t) {
                view.onAvatarFailed(null);
            }
        });
    }
}
