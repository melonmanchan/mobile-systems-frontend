package fi.tutee.tutee.messaging;

import java.util.ArrayList;

import fi.tutee.tutee.BasePresenter;
import fi.tutee.tutee.BaseView;
import fi.tutee.tutee.data.entities.APIError;
import fi.tutee.tutee.data.entities.CreateMessageRequest;
import fi.tutee.tutee.data.entities.User;
import fi.tutee.tutee.data.entities.events.GeneralMessage;

/**
 * Created by lehtone1 on 12/04/17.
 */

public interface MessagingContract {

    interface View extends BaseView<MessagingContract.Presenter> {
        void setMessages(ArrayList<GeneralMessage> messages);

        void getMessagesFailed(ArrayList<APIError> errors);

        void setUser(User user);

        void setOtherUser(User user);

        void createMessageSucceeded(CreateMessageRequest req);

        void createMessageFailed(CreateMessageRequest req ,ArrayList<APIError> errors);
    }

    interface Presenter extends BasePresenter {
        void getMessagesFrom(int userId);

        void getUserByID(int userId);

        void createMessage(int receiverID, String content);
    }
}
