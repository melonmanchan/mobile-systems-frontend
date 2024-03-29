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

    @SerializedName("price")
    @Expose
    private int price;

    public RegisterTutorExtraRequest(String description, ArrayList<Subject> subjects, int price) {
        this.description = description;
        this.subjects = subjects;
        this.price = price;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public int getPrice() { return price; }

    public String getDescription() { return description; }
}
