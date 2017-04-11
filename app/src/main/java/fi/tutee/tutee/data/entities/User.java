package fi.tutee.tutee.data.entities;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.webkit.URLUtil;

import com.google.android.gms.vision.text.Text;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class User {

    @NonNull
    @SerializedName("id")
    private final int id;

    @NonNull
    @Expose
    @SerializedName("first_name")
    private String firstName;

    @Expose
    @SerializedName("avatar")
    private URL avatar;

    @NonNull
    @Expose
    @SerializedName("last_name")
    private String lastName;

    @NonNull
    @Expose
    @SerializedName("email")
    private String email;


    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("auth_method")
    @Expose
    private AuthMethod authMethod;

    @SerializedName("user_type")
    @Expose
    private UserType userType;

    @SerializedName("subjects")
    @Expose
    private ArrayList<Subject> subjects;

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }

    public User(@NonNull int id, String description) {
        this.id = id;
        this.description = description;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NonNull String firstName) {
        this.firstName = firstName;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setAvatar(URL avatar) {
        this.avatar = avatar;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NonNull String lastName) {
        this.lastName = lastName;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (!firstName.equals(user.firstName)) return false;
        if (!lastName.equals(user.lastName)) return false;
        return email.equals(user.email);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public boolean needsToFillProfile() {
        if (!this.userType.equals(UserType.TUTOR)) {
            return false;
        }

        if (TextUtils.isEmpty(this.description)) {
            return true;
        }

        if (this.subjects == null || this.subjects.size() == 0) {
            return true;
        }

        return false;
    }


    public String getDescription() {
        return description;
    }

    public AuthMethod getAuthMethod() {
        return authMethod;
    }

    public void setAuthMethod(AuthMethod authMethod) {
        this.authMethod = authMethod;
    }

    public URI getAvatar() {
        URI url = null;

        if (avatar != null) {
            try {
                url = avatar.toURI();
            } catch (URISyntaxException ex) {
                ex.printStackTrace();
            }
        } else {
            url = URI.create("android.resource://fi.tutee.tutee/drawable/profile_picture");
        }

        return url;
    }
}
