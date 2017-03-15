package fi.tutee.tutee.profile;

import fi.tutee.tutee.data.source.TuteeRepository;

/**
 * Created by emmilinkola on 15/03/17.
 */

public class ProfilePresenter implements ProfileContract.Presenter {
    private final TuteeRepository repository;
    private final ProfileContract.View view;

    public ProfilePresenter(TuteeRepository repository,
                             ProfileContract.View view
    ) {
        this.repository = repository;
        this.view = view;

        view.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
