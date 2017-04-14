package fi.tutee.tutee.data.entities.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Time;

public class GeneralMessage {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("sender_id")
    @Expose
    private int senderId;

    @SerializedName("receiver_id")
    @Expose
    private int receiverId;

    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("sent_at")
    @Expose
    private Time sentAt;


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

    public Time getSentAt() {
        return sentAt;
    }

    public void setSentAt(Time sentAt) {
        this.sentAt = sentAt;
    }



}
