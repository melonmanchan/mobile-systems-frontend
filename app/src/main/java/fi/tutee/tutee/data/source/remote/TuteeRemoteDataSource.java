package fi.tutee.tutee.data.source.remote;

import fi.tutee.tutee.data.source.TuteeDataSource;

/**
 * Created by mat on 06/03/2017.
 */


public class TuteeRemoteDataSource implements TuteeDataSource {
    private  static TuteeRemoteDataSource instance;

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
