package fi.tutee.tutee.tutorselectdetails;

import fi.tutee.tutee.data.source.TuteeRepository;

public class TutorSelectDetailsPresenter implements TutorSelectDetailsContract.Presenter {
    private final TuteeRepository repository;

    private final TutorSelectDetailsContract.View view;

    public TutorSelectDetailsPresenter(TuteeRepository repository, TutorSelectDetailsContract.View view) {
        this.repository = repository;
        this.view = view;

        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getTutorByID(int tutorID) {

    }
}
