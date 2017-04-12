package fi.tutee.tutee.messaging;

import fi.tutee.tutee.data.source.TuteeRepository;

/**
 * Created by lehtone1 on 12/04/17.
 */

public class MessagingPresenter implements MessagingContract.Presenter {

private final TuteeRepository repository;

private final MessagingContract.View view;

public MessagingPresenter(TuteeRepository repository,
        MessagingContract.View view
        ) {
        this.repository = repository;
        this.view = view;

        view.setPresenter(this);
        }

@Override
public void start() {

        }
        }