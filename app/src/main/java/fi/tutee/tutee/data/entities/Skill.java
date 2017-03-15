package fi.tutee.tutee.data.entities;

/**
 * Created by lehtone1 on 15/03/17.
 */

public class Skill
{
    private  String description;
    private  String level;

    public Skill(String description, String level)
    {
        this.description = description;
        this.level = level;
    }

    public String getDescription()   { return description; }

    public void setDescription(String description)  {
        this.description= description;
    }

    public String getLevel() { return level; }

    public void setLevel(String level)  {
        this.level=  level;
    }

}
