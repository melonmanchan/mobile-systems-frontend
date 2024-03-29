package fi.tutee.tutee.messaging;

import java.util.ArrayList;

import fi.tutee.tutee.data.entities.APIResponse;
import fi.tutee.tutee.data.entities.CreateMessageRequest;
import fi.tutee.tutee.data.entities.User;
import fi.tutee.tutee.data.entities.events.GeneralMessage;
import fi.tutee.tutee.data.source.TuteeRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lehtone1 on 12/04/17.
 */

public class MessagingPresenter implements MessagingContract.Presenter {

    private final TuteeRepository repository;

    private final MessagingContract.View view;

    private User user;

    public MessagingPresenter(TuteeRepository repository, MessagingContract.View view) {
        this.repository = repository;
        this.view = view;
        this.user = repository.getLoggedInUser();

        view.setPresenter(this);
    }

    @Override
    public void start() {
        view.setUser(user);
    }

    @Override
    public void getMessagesFrom(int otherUserId, int fromOffset, int toOffset) {
        this.repository.getMessagesFrom(otherUserId, fromOffset, toOffset, new Callback<APIResponse<ArrayList<GeneralMessage>>>() {
            @Override
            public void onResponse(Call<APIResponse<ArrayList<GeneralMessage>>> call, Response<APIResponse<ArrayList<GeneralMessage>>> response) {
                    APIResponse<ArrayList<GeneralMessage>> resp = response.body();

                    if (resp.isSuccessful()) {
                            view.addMessages(resp.getResponse());
                    } else {
                            view.getMessagesFailed(resp.getErrors());
                    }
            }

            @Override
            public void onFailure(Call<APIResponse<ArrayList<GeneralMessage>>> call, Throwable t) {
                    view.getMessagesFailed(null);
            }
        });

    }

    @Override
    public void getUserByID(int tutorID) {
        repository.getUser(tutorID, new Callback<APIResponse<User>>() {
            @Override
            public void onResponse(Call<APIResponse<User>> call, Response<APIResponse<User>> response) {
                APIResponse<User> resp = response.body();

                if (resp.isSuccessful()) {
                    view.setOtherUser(resp.getResponse());
                }
            }

            @Override
            public void onFailure(Call<APIResponse<User>> call, Throwable t) {
                // TODO
            }
        });
    }

    @Override
    public void createMessage(int receiverID, String content) {
        final CreateMessageRequest req = new CreateMessageRequest(receiverID, content);

        repository.createMessage(req, new Callback<APIResponse<GeneralMessage>>() {
            @Override
            public void onResponse(Call<APIResponse<GeneralMessage>> call, Response<APIResponse<GeneralMessage>> response) {
                APIResponse resp = response.body();

                if (resp.isSuccessful()) {
                    view.createMessageSucceeded(req);
                } else {
                    view.createMessageFailed(req, resp.getErrors());
                }
            }

            @Override
            public void onFailure(Call<APIResponse<GeneralMessage>> call, Throwable t) {
                view.createMessageFailed(req, null);
            }
        });
    }
}