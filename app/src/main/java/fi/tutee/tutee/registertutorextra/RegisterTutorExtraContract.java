package fi.tutee.tutee.registertutorextra;

import fi.tutee.tutee.BasePresenter;
import fi.tutee.tutee.BaseView;

/**
 * Created by lehtone1 on 09/03/17.
 */

public interface RegisterTutorExtraContract {

    interface View extends BaseView<Presenter> {
        void onRegisterSuccess();
        void onRegisterFail();
    }

    interface Presenter extends BasePresenter {
        void registerTutorExtra(String description);
    }
}
