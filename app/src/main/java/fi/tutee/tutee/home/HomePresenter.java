package fi.tutee.tutee.home;

import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;

import fi.tutee.tutee.data.entities.APIResponse;
import fi.tutee.tutee.data.entities.CreateFreeTimeRequest;
import fi.tutee.tutee.data.entities.Subject;
import fi.tutee.tutee.data.entities.TimesResponse;
import fi.tutee.tutee.data.entities.TutorshipsResponse;
import fi.tutee.tutee.data.entities.User;
import fi.tutee.tutee.data.entities.events.GeneralMessage;
import fi.tutee.tutee.data.source.TuteeRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter implements HomeContract.Presenter {
    private final TuteeRepository repository;

    private final HomeContract.View view;

    public HomePresenter(TuteeRepository repository,
                                      HomeContract.View view
    ) {
        this.repository = repository;
        this.view = view;

        view.setPresenter(this);
    }
    @Override
    public void start() {

    }

    @Override
    public void logOut() {
        this.repository.logOut();
    }

    @Override
    public void getTutorships() {
        this.repository.getTutorships(new Callback<APIResponse<TutorshipsResponse>>() {
            @Override
            public void onResponse(Call<APIResponse<TutorshipsResponse>> call, Response<APIResponse<TutorshipsResponse>> response) {
                APIResponse<TutorshipsResponse> resp =response.body();

                if (resp != null && resp.isSuccessful()) {
                    TutorshipsResponse tutorshipsResponse = resp.getResponse();

                    ArrayList<User> allUsers = new ArrayList<User>();

                    allUsers.addAll(tutorshipsResponse.getTutees());
                    allUsers.addAll(tutorshipsResponse.getTutors());
                    view.setMessageUsers(allUsers);
                    view.setTutorNames(tutorshipsResponse.getTutors());
                    view.setTuteeNames(tutorshipsResponse.getTutees());
                }
            }

            @Override
            public void onFailure(Call<APIResponse<TutorshipsResponse>> call, Throwable t) {
                // TODO
            }
        });
    }

    @Override
    public void getSubjects() {
        this.repository.getSubjects(new Callback<APIResponse<ArrayList<Subject>>>() {
            @Override
            public void onResponse(Call<APIResponse<ArrayList<Subject>>> call, Response<APIResponse<ArrayList<Subject>>> response) {
                APIResponse<ArrayList<Subject>> resp = response.body();

                if (resp != null && resp.isSuccessful()) {
                    ArrayList<Subject> subjects = resp.getResponse();
                    view.setSubjects(subjects);
                }
            }

            @Override
            public void onFailure(Call<APIResponse<ArrayList<Subject>>> call, Throwable t) {
                // TODO
            }
        });
    }


    @Override
    public void getLatestMessages() {
        this.repository.getLatestMessages(new Callback<APIResponse<ArrayList<GeneralMessage>>>() {
            @Override
            public void onResponse(Call<APIResponse<ArrayList<GeneralMessage>>> call, Response<APIResponse<ArrayList<GeneralMessage>>> response) {
                APIResponse<ArrayList<GeneralMessage>> resp = response.body();

                if (resp != null && resp.isSuccessful()) {
                    ArrayList<GeneralMessage> messages = resp.getResponse();
                    view.setLatestMessages(messages);
                }
            }

            @Override
            public void onFailure(Call<APIResponse<ArrayList<GeneralMessage>>> call, Throwable t) {
                // TODO
            }
        });
    }

    @Override
    public void createFreeTime(WeekViewEvent event) {
        CreateFreeTimeRequest req = new CreateFreeTimeRequest(event.getStartTime().getTime());

        this.repository.createFreeTime(req, new Callback<APIResponse<WeekViewEvent>>() {
            @Override
            public void onResponse(Call<APIResponse<WeekViewEvent>> call, Response<APIResponse<WeekViewEvent>> response) {
                // TODO
                APIResponse<WeekViewEvent> resp = response.body();
            }

            @Override
            public void onFailure(Call<APIResponse<WeekViewEvent>> call, Throwable throwable) {
                // TODO
            }});
    }

    @Override
    public void removeTime(WeekViewEvent event) {
        this.repository.removeTime(event, new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                APIResponse resp = response.body();
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public User getUser() {
        return repository.getLoggedInUser();
    }

    @Override
    public void getTimes() {
        this.repository.getTimes(new Callback<APIResponse<TimesResponse>>() {
            @Override
            public void onResponse(Call<APIResponse<TimesResponse>> call, Response<APIResponse<TimesResponse>> response) {
                APIResponse<TimesResponse> resp = response.body();

                if (resp != null && resp.isSuccessful()) {
                    TimesResponse events = resp.getResponse();
                    view.setTimes(events);
                }
            }

            @Override
            public void onFailure(Call<APIResponse<TimesResponse>> call, Throwable t) {
                // TODO
            }
        });
    }
}
