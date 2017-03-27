package fi.tutee.tutee.home;

import java.util.ArrayList;

import fi.tutee.tutee.data.entities.User;
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

    @Override
    public void getMessageUsers() {
        User user = this.repository.getLoggedInUser();

        ArrayList<User> users = new ArrayList<>();
        users.add(user);

        this.view.setMessageUsers(users);
    }
}
