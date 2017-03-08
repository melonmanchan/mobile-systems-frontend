package fi.tutee.tutee.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mat on 07/03/2017.
 */

public class APIResponse <T> {
    @SerializedName("errors")
    @Expose
    private List<String> errors = null;

    @SerializedName("results")
    @Expose
    private T response = null;

    public List<String> getErrors() {
        return errors;
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
}
