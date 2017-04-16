package fi.tutee.tutee.reservecalendar;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;

import fi.tutee.tutee.BasePresenter;
import fi.tutee.tutee.BaseView;
import fi.tutee.tutee.data.entities.APIError;
import fi.tutee.tutee.data.entities.Subject;
import fi.tutee.tutee.registertutorextra.RegisterExtraContract;

/**
 * Created by lehtone1 on 16/04/17.
 */

public interface ReserveCalendarContract {

    interface View extends BaseView<ReserveCalendarContract.Presenter> {
        void setFreeTimes(ArrayList<WeekViewEvent> events);
    }

    interface Presenter extends BasePresenter {
        void getFreeTimes(int tutorID);

    }
}
