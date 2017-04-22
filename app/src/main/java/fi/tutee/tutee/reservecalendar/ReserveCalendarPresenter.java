package fi.tutee.tutee.reservecalendar;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;

import fi.tutee.tutee.data.entities.APIResponse;
import fi.tutee.tutee.data.entities.DeviceRegisterRequest;
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
    public void getFreeTimes(int tutorID)  {
        this.repository.getFreeTimes(tutorID, new Callback<APIResponse<ArrayList<WeekViewEvent>>>() {
            @Override
            public void onResponse(Call<APIResponse<ArrayList<WeekViewEvent>>> call, Response<APIResponse<ArrayList<WeekViewEvent>>> response) {
                APIResponse<ArrayList<WeekViewEvent>> resp = response.body();

                if (resp != null && resp.isSuccessful()) {
                    ArrayList<WeekViewEvent> events = resp.getResponse();
                    view.setFreeTimes(events);
                }
            }

            @Override
            public void onFailure(Call<APIResponse<ArrayList<WeekViewEvent>>> call, Throwable t) {
                // TODO
            }
        });

    }

    @Override
    public void reserveTime(WeekViewEvent event) {
        this.repository.reserveTime(event, new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                APIResponse resp = response.body();

                if (resp.isSuccessful()) {
                    view.onReserveTimeSuccess();
                } else {
                    view.onReserveTimeFail(resp.getErrors());
                }

            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                view.onReserveTimeFail(null);
            }
        });
    }
}
