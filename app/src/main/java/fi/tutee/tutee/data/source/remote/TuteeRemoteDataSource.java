package fi.tutee.tutee.data.source.remote;

import fi.tutee.tutee.data.source.TuteeDataSource;
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
    public void basicLogin(String email, String password) {

    }

    @Override
    public void googleLogin(String token) {

    }
}
