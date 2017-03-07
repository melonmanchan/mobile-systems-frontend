package fi.tutee.tutee.data.source.remote;

import java.io.IOException;

import fi.tutee.tutee.data.LoginRequest;
import fi.tutee.tutee.data.RegisterRequest;
import fi.tutee.tutee.data.User;
import fi.tutee.tutee.data.source.TuteeDataSource;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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
        retrofit = buildUnauthenticatedRetrofit();
        service = retrofit.create(TuteeService.class);

    }

    public static TuteeRemoteDataSource getInstance() {
        if (instance == null) {
            instance = new TuteeRemoteDataSource();
        }

        return instance;
    }

    public void setToken(String authToken) {
        retrofit = buildAuthenticatedRetrofit(authToken);
        this.service = retrofit.create(TuteeService.class);
    }

    private Retrofit buildAuthenticatedRetrofit(final String authToken) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Authorization", "Bearer: " + authToken)
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://apartheidfun.club")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }

    private Retrofit buildUnauthenticatedRetrofit () {

        retrofit = new Retrofit.Builder()
                .baseUrl("https://apartheidfun.club")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
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
