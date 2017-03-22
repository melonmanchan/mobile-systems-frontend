package fi.tutee.tutee.registertutorextra;

import java.util.ArrayList;

import fi.tutee.tutee.BasePresenter;
import fi.tutee.tutee.BaseView;
import fi.tutee.tutee.data.entities.Skill;

/**
 * Created by lehtone1 on 09/03/17.
 */

public interface RegisterExtraContract {

    interface View extends BaseView<Presenter> {
        void onRegisterSuccess();
        void onRegisterFail();
    }

    interface Presenter extends BasePresenter {
        void registerTutorExtra(String country, String city, String description, ArrayList<Skill> skills);
    }
}
