package fi.tutee.tutee.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lehtone1 on 06/04/17.
 */

public class GetTutorsBySubjectRequest {

    @SerializedName("subject")
    @Expose
    private Subject subject;


    public GetTutorsBySubjectRequest(Subject subject){
        this.subject = subject;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
