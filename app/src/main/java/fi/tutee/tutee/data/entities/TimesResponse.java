package fi.tutee.tutee.data.entities;

import com.alamkanak.weekview.WeekViewEvent;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by lehtone1 on 16/04/17.
 */

public class TimesResponse {

    @SerializedName("own_events")
    @Expose
    ArrayList<WeekViewEvent> ownEvents = new ArrayList<>();

    @SerializedName("tutee_events")
    @Expose
    ArrayList<WeekViewEvent> reservedEvents = new ArrayList<>();

    public ArrayList<WeekViewEvent> getOwnEvents() {
        return ownEvents;
    }

    public ArrayList<WeekViewEvent> getReservedEvents() {
        return reservedEvents;
    }

    public void setOwnEvents(ArrayList<WeekViewEvent> ownEvents) {
        this.ownEvents = ownEvents;
    }

    public void setReservedEvents(ArrayList<WeekViewEvent> reservedEvents) {
        this.reservedEvents = reservedEvents;
    }
}
