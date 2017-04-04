package fi.tutee.tutee.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class APIResponse <T> {
    @SerializedName("errors")
    @Expose
    private List<APIError> errors = new ArrayList<>();

    public void setStatus(int status) {
        this.status = status;
    }

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("results")
    @Expose
    private T response = null;

    public ArrayList<APIError> getErrors() {
        ArrayList<APIError> errs = new ArrayList<>();

        if (this.errors != null) {
            errs.addAll(this.errors);
        }

        return errs;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public T getResponse() {
        return response;
    }

    public boolean isSuccessful() {

        if (this.errors != null && this.errors.size() > 0) {
            return false;
        }

        return (!(this.status >= 400));
    }
}
