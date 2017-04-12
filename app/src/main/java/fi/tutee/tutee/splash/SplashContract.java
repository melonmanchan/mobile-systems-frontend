package fi.tutee.tutee.splash;

import fi.tutee.tutee.BasePresenter;
import fi.tutee.tutee.BaseView;

/**
 * Created by emmilinkola on 12/04/17.
 */

public interface SplashContract {

    interface Presenter extends BasePresenter {

    }

    interface View extends BaseView<Presenter> {
    }
}
