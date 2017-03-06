package fi.tutee.tutee.data.source.local;

import fi.tutee.tutee.data.source.TuteeDataSource;

/**
 * Created by mat on 06/03/2017.
 */

public class TuteeLocalDataSource implements TuteeDataSource{
    private  static TuteeLocalDataSource instance;

    public static TuteeLocalDataSource getInstance() {
        if (instance == null) {
            instance = new TuteeLocalDataSource();
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
