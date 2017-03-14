package fi.tutee.tutee.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;

/**
 * Created by lehtone1 on 09/03/17.
 */

public class RegisterTutorExtraRequest  {

    @SerializedName("description")
    @Expose
    private String description;

    private String skillDescription;

    private Integer skillLevel;

    private Array skills;



    public RegisterTutorExtraRequest(String description, Array skills) {
        this.description = description;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
