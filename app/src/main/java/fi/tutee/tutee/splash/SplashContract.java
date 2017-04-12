package fi.tutee.tutee.splash;

import fi.tutee.tutee.BasePresenter;
import fi.tutee.tutee.BaseView;
import fi.tutee.tutee.data.entities.AuthResponse;

public interface SplashContract {

    interface Presenter extends BasePresenter {
        AuthResponse getAutoLoginInfo();
    }

    interface View extends BaseView<Presenter> {
        void goToNextActivity();
    }
}
