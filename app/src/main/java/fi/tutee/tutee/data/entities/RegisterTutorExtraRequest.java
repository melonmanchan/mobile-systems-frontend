package fi.tutee.tutee.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lehtone1 on 09/03/17.
 */

public class RegisterTutorExtraRequest {

    @SerializedName("description")
    @Expose
    private String description;



    public RegisterTutorExtraRequest(String description) {
        this.description = description;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
