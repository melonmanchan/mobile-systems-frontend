package fi.tutee.tutee.messaging;

import fi.tutee.tutee.BasePresenter;
import fi.tutee.tutee.BaseView;

/**
 * Created by lehtone1 on 12/04/17.
 */

public interface MessagingContract {

    interface View extends BaseView<MessagingContract.Presenter> {

    }

    interface Presenter extends BasePresenter {

    }
}
