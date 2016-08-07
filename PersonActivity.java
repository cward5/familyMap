package edu.byu.cs.familymap.personActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import edu.byu.cs.familymap.R;
import edu.byu.cs.familymap.model.EventModel;
import edu.byu.cs.familymap.model.ModelContainer;
import edu.byu.cs.familymap.model.PersonModel;

/**
 * Created by chris on 8/4/2016.
 */
public class PersonActivity extends Activity{
    private PersonModel person;
    private PersonModel father;
    private PersonModel mother;
    private PersonModel spouse;
    private ArrayList<PersonModel> children;

    private Activity parentActivity;
    private ListView nameListView;
    private ExpandableListView eventListView;
    private ExpandableListView familyListView;

    public PersonActivity(PersonModel person)
    {
        setPerson(person);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_activity_layout);

        nameListView=(ListView)findViewById(R.id.nameListView);

        List<NameInfo> nameInfo=new ArrayList<>();
        nameInfo.add(new NameInfo(person.getFirstName(),"FIRST NAME"));
        nameInfo.add(new NameInfo(person.getLastName(),"LAST NAME"));
        if(person.getGender()=='m')
            nameInfo.add(new NameInfo("Male","GENDER"));
        else nameInfo.add(new NameInfo("Female","GENDER"));

        NameListAdapter nameListAdapter=new NameListAdapter(this, R.layout.name_list_item,nameInfo);
        nameListView.setAdapter(nameListAdapter);
        nameListAdapter.notifyDataSetChanged();

        eventListView=(ExpandableListView)findViewById(R.id.eventListView);
        EventListAdapter eventListAdapter=new EventListAdapter();
        eventListAdapter.setEventInfo(generateEventList());
        eventListAdapter.setFamilyInfo(generateFamilyList());
        eventListAdapter.setActivity(this);
        eventListView.setAdapter(eventListAdapter);
        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

    }

    private ArrayList<String> generateEventList()
    {
        EventModel birth=null;
        EventModel death=null;
        HashSet<EventModel> otherEvents=new HashSet<>();
        for(EventModel event:person.getEvents())
        {
            String description=event.getDescription().toLowerCase();
            switch (description) {
                case "birth":
                    birth = event;
                    break;
                case "death":
                    death = event;
                    break;
                default:
                    otherEvents.add(event);
                    break;
            }
        }
        ArrayList<String> result=new ArrayList<>();
        if(birth!=null)
        {
            result.add(birth.toString());
        }
        for(EventModel event:otherEvents)
        {
            result.add(event.toString());
        }
        if(death!=null)
        {
            result.add(death.toString());
        }
        return result;
    }

    private ArrayList<String> generateFamilyList() {
        return null;
    }

    public PersonModel getPerson() {
        return person;
    }

    public void setPerson(PersonModel person) {
        this.person = person;
        if(person.getFatherID()!=null)
        {
            father=ModelContainer.getSingleton().getPerson(person.getFatherID());
        }
        if(person.getMotherID()!=null)
        {
            mother=ModelContainer.getSingleton().getPerson(person.getMotherID());
        }
        if(person.getSpouseID()!=null)
        {
            spouse=ModelContainer.getSingleton().getPerson(person.getSpouseID());
        }
        children=new ArrayList<>();
        for(PersonModel p:ModelContainer.getSingleton().getPeople())
        {
            if(person.getGender()=='m')
            {
                if (p.getFatherID()!=null&&p.getFatherID().equals(person.getPersonID()))
                {
                    children.add(p);
                }
            }
            else
            {
                if(p.getMotherID()!=null&&p.getMotherID().equals(person.getPersonID()))
                {
                    children.add(p);
                }
            }
        }
    }
}
