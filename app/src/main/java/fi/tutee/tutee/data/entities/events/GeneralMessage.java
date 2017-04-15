package fi.tutee.tutee.data.entities.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Time;
import java.util.Date;

public class GeneralMessage {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("sender")
    @Expose
    private int senderId;

    @SerializedName("receiver")
    @Expose
    private int receiverId;

    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("sent_at")
    @Expose
    private Date sentAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSentAt() {
        return sentAt;
    }

    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }
}
