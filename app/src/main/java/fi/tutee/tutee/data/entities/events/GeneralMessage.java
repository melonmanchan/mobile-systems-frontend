package fi.tutee.tutee.data.entities.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeneralMessage {

    @SerializedName("content")
    @Expose
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
