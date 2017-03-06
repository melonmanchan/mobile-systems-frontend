package fi.tutee.tutee.data.source;

/**
 * Created by mat on 06/03/2017.
 */

public class TuteeRepository  implements TuteeDataSource {

    private static TuteeRepository instance = null;

    private  final TuteeDataSource remote;

    private  final TuteeDataSource local;

    public TuteeRepository(TuteeDataSource remote, TuteeDataSource local) {
        this.remote = remote;
        this.local = local;
    }

    public static TuteeRepository getInstance(TuteeDataSource remote,
                                              TuteeDataSource local) {
        if (instance == null) {
            instance = new TuteeRepository(remote, local);
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
