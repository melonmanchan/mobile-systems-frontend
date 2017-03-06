package fi.tutee.tutee.pickauthentication;

import fi.tutee.tutee.data.source.TuteeRepository;

/**
 * Created by mat on 06/03/2017.
 */

public class AuthenticationPresenter implements AuthenticationContract.Presenter {

    private final TuteeRepository repository;

    private final AuthenticationContract.View view;

    public AuthenticationPresenter(TuteeRepository repository,
                                      AuthenticationContract.View view
    ) {
        this.repository = repository;
        this.view = view;

        view.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
