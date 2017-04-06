package fi.tutee.tutee.data.entities;

import android.util.Pair;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.lang.Object.*;
import java.util.ArrayList;

public class RegisterTutorExtraRequest  {

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("subjects")
    @Expose
    private ArrayList<Subject> subjects;

    public RegisterTutorExtraRequest(String description, ArrayList<Subject> subjects) {
        this.description = description;
        this.subjects = subjects;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }
}
