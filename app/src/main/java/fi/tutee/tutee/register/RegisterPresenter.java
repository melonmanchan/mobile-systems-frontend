package fi.tutee.tutee.register;

import fi.tutee.tutee.data.source.TuteeRepository;

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
}
