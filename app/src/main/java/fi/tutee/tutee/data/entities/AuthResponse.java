package fi.tutee.tutee.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mat on 07/03/2017.
 */

public class AuthResponse {
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("expires_at")
    @Expose
    private int expiresAt;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("user")
    @Expose
    private User user;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(int expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isValid() {
        long currentTime = System.currentTimeMillis() / 1000L;

        boolean isExpired =  (currentTime > this.expiresAt);

        return  !isExpired;
    }
}
