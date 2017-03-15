package fi.tutee.tutee.profile;

import fi.tutee.tutee.BasePresenter;
import fi.tutee.tutee.BaseView;

/**
 * Created by emmilinkola on 15/03/17.
 */

public class ProfileContract {

    interface Presenter extends BasePresenter {
    }

    interface View extends BaseView<Presenter> {

    }
}
