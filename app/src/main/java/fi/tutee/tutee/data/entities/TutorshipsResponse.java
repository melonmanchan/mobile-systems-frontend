package fi.tutee.tutee.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TutorshipsResponse {

    @SerializedName("tutors")
    @Expose
    ArrayList<User> tutors = new ArrayList<>();

    @SerializedName("tutees")
    @Expose
    ArrayList<User> tutees = new ArrayList<>();

    public ArrayList<User> getTutors() {
        return tutors;
    }

    public ArrayList<User> getTutees() {
        return tutees;
    }

    public void setTutors(ArrayList<User> tutors) {
        this.tutors = tutors;
    }

    public void setTutees(ArrayList<User> tutees) {
        this.tutees = tutees;
    }

}
