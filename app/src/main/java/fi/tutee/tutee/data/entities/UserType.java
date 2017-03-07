package fi.tutee.tutee.data.entities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserType {

    @SerializedName("type")
    @Expose
    private String type;

    public UserType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserType userType = (UserType) o;

        return type != null ? type.equals(userType.type) : userType.type == null;

    }

    @Override
    public int hashCode() {
        return type != null ? type.hashCode() : 0;
    }

    public static final UserType TUTOR = new UserType("TUTOR");
    public static final UserType TUTEE = new UserType("TUTEE");
}
