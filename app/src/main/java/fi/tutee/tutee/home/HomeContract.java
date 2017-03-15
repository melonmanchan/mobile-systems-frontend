package fi.tutee.tutee.home;

import fi.tutee.tutee.BasePresenter;
import fi.tutee.tutee.BaseView;
import fi.tutee.tutee.usertypeselection.UserTypeSelectionContract;

/**
 * Created by mat on 12/03/2017.
 */

public interface HomeContract {
    interface Presenter extends BasePresenter{
        void logOut();
    }

    interface View extends BaseView<Presenter> {

    }
}
