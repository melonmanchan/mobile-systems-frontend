package fi.tutee.tutee.register;

import fi.tutee.tutee.BasePresenter;
import fi.tutee.tutee.BaseView;


public interface RegisterContract {

    interface View extends BaseView<Presenter> {
        void onRegisterSuccess();
        void onRegisterFail();
    }

    interface Presenter extends BasePresenter {
        void register(String firstname, String lastname, String email, String password, String usertype);
    }
}
