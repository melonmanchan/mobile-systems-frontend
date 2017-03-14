package fi.tutee.tutee.home;

import fi.tutee.tutee.data.source.TuteeRepository;

/**
 * Created by mat on 12/03/2017.
 */

public class HomePresenter implements HomeContract.Presenter {
    private final TuteeRepository repository;

    private final HomeContract.View view;

    public HomePresenter(TuteeRepository repository,
                                      HomeContract.View view
    ) {
        this.repository = repository;
        this.view = view;

        view.setPresenter(this);
    }
    @Override
    public void start() {

    }

    @Override
    public void logOut() {
        this.repository.logOut();
    }
}
