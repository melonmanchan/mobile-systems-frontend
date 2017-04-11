package fi.tutee.tutee.register;

import android.graphics.Bitmap;

import java.io.File;

import fi.tutee.tutee.data.entities.APIResponse;
import fi.tutee.tutee.data.entities.AuthResponse;
import fi.tutee.tutee.data.entities.DeviceRegisterRequest;
import fi.tutee.tutee.data.entities.RegisterRequest;
import fi.tutee.tutee.data.entities.User;
import fi.tutee.tutee.data.source.TuteeRepository;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
                    repository.registerUserDevice(new DeviceRegisterRequest());
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

    @Override
    public void setAvatar(String avatarUri) {
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
