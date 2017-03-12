package fi.tutee.tutee.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class APIResponse <T> {
    @SerializedName("errors")
    @Expose
    private List<APIError> errors = new ArrayList<>();

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("results")
    @Expose
    private T response = null;

    public ArrayList<APIError> getErrors() {
        ArrayList<APIError> errs = new ArrayList<>(this.errors.size());
        errs.addAll(this.errors);
        return errs;
    }

    public T getResponse() {
        return response;
    }

    public boolean isSuccessful() {
        return (!(this.status >= 400) && this.errors.size() == 0);
    }
}
