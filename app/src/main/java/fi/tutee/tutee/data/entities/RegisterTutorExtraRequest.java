package fi.tutee.tutee.data.entities;

import android.util.Pair;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.lang.Object.*;
import java.util.ArrayList;

/**
 * Created by lehtone1 on 09/03/17.
 */

public class RegisterTutorExtraRequest  {

    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("skills")
    @Expose
    private ArrayList<Skill> skills;



    public RegisterTutorExtraRequest(String country, String city, String description, ArrayList<Skill> skills) {
        this.country = country;
        this.city = city;
        this.description = description;
        this.skills = skills;


    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Skill>  getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skill>  skills) {
        this.skills = skills;
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

}
