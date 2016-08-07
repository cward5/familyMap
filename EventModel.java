package edu.byu.cs.familymap.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Model of an event in the FamilyMap
 * Created by chris on 8/3/2016.
 */
public class EventModel implements Comparable<EventModel>{
    private String eventID;
    private String personID;
    private PersonModel person;
    private double latitude;
    private double longitude;
    private String country;
    private String city;
    private String description;
    private int year;

    public void populateFromJSON(String json) throws JSONException
    {
        JSONObject object=new JSONObject(json);

        setEventID(object.getString("eventID"));
        setPersonID(object.getString("personID"));
        setLatitude(object.getDouble("latitude"));
        setLongitude(object.getDouble("longitude"));
        setCountry(object.getString("country"));
        setCity(object.getString("city"));
        setDescription(object.getString("description"));
        setYear(Integer.parseInt(object.getString("year")));
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public PersonModel getPerson() {
        if(person==null)
        {
            person=ModelContainer.getSingleton().getPerson(personID);
        }
        return person;
    }

    public void setPerson(PersonModel person) {
        this.person = person;
    }

    @Override
    public int compareTo(EventModel eventModel) {
        if(eventModel==null) throw new IllegalArgumentException();

        if(this.getYear()>eventModel.getYear())
        {
            return 1;
        }
        else if(this.getYear()<eventModel.getYear())
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }

    @Override
    public String toString()
    {
        String build = description.toLowerCase() + ": " +
                city + ", " + country + " (" + year + ")\n" +
                person.getFirstAndLastName();
        return build;
    }
}
