package fi.tutee.tutee.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lehtone1 on 06/04/17.
 */

public class GetTutorBySubjectRequest {

    @SerializedName("subject")
    @Expose
    private String subject;


    public GetTutorBySubjectRequest(String subject){
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
