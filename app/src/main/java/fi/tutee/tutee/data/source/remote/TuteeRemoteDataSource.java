package fi.tutee.tutee.data.source.remote;

import fi.tutee.tutee.data.LoginRequest;
import fi.tutee.tutee.data.RegisterRequest;
import fi.tutee.tutee.data.User;
import fi.tutee.tutee.data.source.TuteeDataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TuteeRemoteDataSource implements TuteeDataSource {
    private static TuteeRemoteDataSource instance;

    private Retrofit retrofit;
    private TuteeService service;

    public TuteeRemoteDataSource() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://apartheidfun.club")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(TuteeService.class);

    }

    public static TuteeRemoteDataSource getInstance() {
        if (instance == null) {
            instance = new TuteeRemoteDataSource();
        }

        return instance;
    }

    @Override
    public void basicLogin(LoginRequest req, Callback<User> cb) {
        Call<User> call = service.basicLogin(req);
        call.enqueue(cb);
        /*
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        */
    }

    @Override
    public void googleLogin(String token) {

    }

    @Override
    public void register(RegisterRequest req, Callback<User> cb) {
        Call<User> call = service.register(req);
        call.enqueue(cb);
    }
}
