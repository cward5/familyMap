package edu.byu.cs.familymap.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Model of a Person in the FamilyMap.
 * Created by chris on 8/3/2016.
 */
public class PersonModel {
    private String personID;
    private String firstName;
    private String lastName;
    private char gender;
    private String fatherID;
    private String motherID;
    private String spouseID;
    private ArrayList<EventModel> events;

    public void populateFromJSON(String json) throws JSONException
    {
        JSONObject object=new JSONObject(json);

        setPersonID(object.getString("personID"));
        setFirstName(object.getString("firstName"));
        setLastName(object.getString("lastName"));
        setGender(object.getString("gender").charAt(0));
        if(object.has("father"))
            setFatherID(object.getString("father"));
        if(object.has("mother"))
            setMotherID(object.getString("mother"));
        if(object.has("spouse"))
            setSpouseID(object.getString("spouse"));
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
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

    public String getFirstAndLastName()
    {
        return firstName+" "+lastName;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    public ArrayList<EventModel> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<EventModel> events) {
        this.events = events;
    }

    public void addEvent(EventModel event)
    {
        if(events==null)
            events=new ArrayList<>();

        events.add(event);
    }

}
