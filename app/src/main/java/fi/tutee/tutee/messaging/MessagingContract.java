package fi.tutee.tutee.messaging;

import java.util.ArrayList;

import fi.tutee.tutee.BasePresenter;
import fi.tutee.tutee.BaseView;
import fi.tutee.tutee.data.entities.events.GeneralMessage;

/**
 * Created by lehtone1 on 12/04/17.
 */

public interface MessagingContract {

    interface View extends BaseView<MessagingContract.Presenter> {
        void setMessages(ArrayList<GeneralMessage> messages);

    }

    interface Presenter extends BasePresenter {
        void getMessageBetween(int thisUserId, int otherUserId);
    }
}
