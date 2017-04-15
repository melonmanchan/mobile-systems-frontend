package fi.tutee.tutee.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateMessageRequest {
    @SerializedName("receiver")
    @Expose
    private int receiver;

    @SerializedName("content")
    @Expose
    private String content;

    public CreateMessageRequest(int receiver, String content) {
        this.receiver = receiver;
        this.content = content;
    }
}
