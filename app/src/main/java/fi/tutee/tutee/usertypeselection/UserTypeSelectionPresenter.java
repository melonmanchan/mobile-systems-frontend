package fi.tutee.tutee.usertypeselection;

import fi.tutee.tutee.data.source.TuteeRepository;

public class UserTypeSelectionPresenter implements UserTypeSelectionContract.Presenter {

    private final TuteeRepository repository;

    private final UserTypeSelectionContract.View view;

    public UserTypeSelectionPresenter(TuteeRepository repository,
                                      UserTypeSelectionContract.View view
    ) {
        this.repository = repository;
        this.view = view;

        view.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
