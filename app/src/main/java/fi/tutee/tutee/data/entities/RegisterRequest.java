package fi.tutee.tutee.data.entities;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterRequest {

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("first_name")
    @Expose
    private String firstName;

    @SerializedName("last_name")
    @Expose
    private String lastName;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("user_type")
    @Expose
    private String userType;

    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("profilePicture")
    @Expose
    private Bitmap profilePicture;



    public RegisterRequest(String firstName, String lastName, String email, String password, String userType, String country, String city, Bitmap profilePicture) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.country = country;
        this.city = city;
        this.profilePicture = profilePicture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) { this.country = country; }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city= city;
    }

    public Bitmap getProfilePicture() {
        return profilePicture;
    }

    public void setProilePicture(Bitmap profilePicture) {
        this.profilePicture= profilePicture;
    }

}
