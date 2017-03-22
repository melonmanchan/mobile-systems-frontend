package fi.tutee.tutee.register;

import android.graphics.Bitmap;

import java.util.ArrayList;

import fi.tutee.tutee.BasePresenter;
import fi.tutee.tutee.BaseView;
import fi.tutee.tutee.data.entities.APIError;


public interface RegisterContract {

    interface View extends BaseView<Presenter> {
        void onRegisterSuccess();
        void onRegisterFail(ArrayList<APIError> errors);
    }

    interface Presenter extends BasePresenter {
        void register(String firstname, String lastname, String email, String password, String usertype, Bitmap profilePicture);
    }
}
