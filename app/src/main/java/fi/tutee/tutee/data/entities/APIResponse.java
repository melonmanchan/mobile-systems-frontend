package fi.tutee.tutee.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class APIResponse <T> {
    @SerializedName("errors")
    @Expose
    private List<String> errors = new ArrayList<String>();

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("results")
    @Expose
    private T response = null;

    public ArrayList<String> getErrors() {
        ArrayList<String> errs = new ArrayList<>(this.errors.size());
        errs.addAll(this.errors);
        return errs;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSuccesful() {
        return !(this.status >= 400);
    }
}
