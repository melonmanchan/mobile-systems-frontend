package fi.tutee.tutee.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateTutorshipRequest {
    @SerializedName("id")
    @Expose
    private int ID;

    public CreateTutorshipRequest(int id) {
        ID = id;
    }

    public int getID() {
        return ID;
    }
}
