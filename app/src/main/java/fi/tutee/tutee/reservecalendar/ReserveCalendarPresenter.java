package fi.tutee.tutee.reservecalendar;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;

import fi.tutee.tutee.data.entities.APIResponse;
import fi.tutee.tutee.data.entities.Subject;
import fi.tutee.tutee.data.source.TuteeRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lehtone1 on 16/04/17.
 */

public class ReserveCalendarPresenter implements ReserveCalendarContract.Presenter {

    private final TuteeRepository repository;

    private final ReserveCalendarContract.View view;

    public ReserveCalendarPresenter(TuteeRepository repository,
                                  ReserveCalendarContract.View view
    ) {
        this.repository = repository;
        this.view = view;

        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getTimes(int tutorID)  {
        this.repository.getTimes(tutorID, new Callback<APIResponse<ArrayList<WeekViewEvent>>>() {
            @Override
            public void onResponse(Call<APIResponse<ArrayList<WeekViewEvent>>> call, Response<APIResponse<ArrayList<WeekViewEvent>>> response) {
                APIResponse<ArrayList<WeekViewEvent>> resp = response.body();

                if (resp.isSuccessful()) {
                    ArrayList<WeekViewEvent> events = resp.getResponse();
                    view.setTimes(events);
                }
            }

            @Override
            public void onFailure(Call<APIResponse<ArrayList<WeekViewEvent>>> call, Throwable t) {
                // TODO
            }
        });

    }
}
