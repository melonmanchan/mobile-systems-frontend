package fi.tutee.tutee.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by lehtone1 on 16/04/17.
 */

public class CreateFreeTimeRequest {



    @SerializedName("start_time")
    @Expose
    private Date startTime;

    @SerializedName("end_time")
    @Expose
    private Date endTime;

    public CreateFreeTimeRequest(Date startTime, Date endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
