package edu.byu.cs.familymap.model;

import java.util.ArrayList;

/**
 * Holds the overall model of a session of FamilyMap.
 * Created by chris on 8/4/2016.
 */
public class ModelContainer {
    private static ModelContainer singleton;
    private ArrayList<PersonModel> people;
    private ArrayList<EventModel> events;
    private ArrayList<String> eventTypes;
    private String authorizationToken;
    private SettingsModel settingsModel;
    private FilterModel filterModel;
    private SearchModel searchModel;

    private ModelContainer()
    {

    }

    public static ModelContainer getSingleton()
    {
        if(singleton==null)
            singleton=new ModelContainer();
        return singleton;
    }

    public ArrayList<PersonModel> getPeople() {
        return people;
    }

    public PersonModel getPerson(String personID){
        for(PersonModel p:people)
            if(p.getPersonID().equals(personID))
                return p;

        return null;
    }

    public void setPeople(ArrayList<PersonModel> people) {
        this.people = people;
    }

    public void addPerson(PersonModel person)
    {
        if(people==null)
        {
            people=new ArrayList<>();
        }
        people.add(person);
    }

    public ArrayList<EventModel> getEvents() {
        return events;
    }

    public EventModel getEvent(String eventID)
    {
        for(EventModel e:events)
            if(e.getEventID().equals(eventID))
                return e;

        return null;
    }


    public void setEvents(ArrayList<EventModel> events) {
        this.events = events;
    }

    public void addEvent(EventModel event)
    {
        if(events==null)
        {
            events=new ArrayList<>();
            eventTypes=new ArrayList<>();
        }
        events.add(event);
        if(!eventTypes.contains(event.getDescription()))
        {
            eventTypes.add(event.getDescription());
        }

        for(PersonModel person:people)
        {
            if(person.getPersonID().equals(event.getPersonID()))
            {
                person.addEvent(event);
            }
        }
    }

    public ArrayList<String> getEventTypes()
    {
        return eventTypes;
    }

    public String getAuthorizationToken() {
        return authorizationToken;
    }

    public void setAuthorizationToken(String authorizationToken) {
        this.authorizationToken = authorizationToken;
    }

    public SettingsModel getSettingsModel() {
        return settingsModel;
    }

    public void setSettingsModel(SettingsModel settingsModel) {
        this.settingsModel = settingsModel;
    }

    public FilterModel getFilterModel() {
        return filterModel;
    }

    public void setFilterModel(FilterModel filterModel) {
        this.filterModel = filterModel;
    }

    public SearchModel getSearchModel() {
        return searchModel;
    }

    public void setSearchModel(SearchModel searchModel) {
        this.searchModel = searchModel;
    }
}
