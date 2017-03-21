package fi.tutee.tutee.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by emmilinkola on 21/03/17.
 */

public class UpdateUserRequest {
    @SerializedName("user")
    @Expose
    private User user;

    public UpdateUserRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
