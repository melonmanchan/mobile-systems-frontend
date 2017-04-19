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

    public CreateFreeTimeRequest(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }


}
