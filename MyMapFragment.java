package edu.byu.cs.familymap.map;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.amazon.geo.mapsv2.AmazonMap;
import com.amazon.geo.mapsv2.AmazonMapOptions;
import com.amazon.geo.mapsv2.CameraUpdateFactory;
import com.amazon.geo.mapsv2.OnMapReadyCallback;
import com.amazon.geo.mapsv2.SupportMapFragment;
import com.amazon.geo.mapsv2.model.BitmapDescriptorFactory;
import com.amazon.geo.mapsv2.model.LatLng;
import com.amazon.geo.mapsv2.model.Marker;
import com.amazon.geo.mapsv2.model.MarkerOptions;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.byu.cs.familymap.R;
import edu.byu.cs.familymap.model.EventModel;
import edu.byu.cs.familymap.model.ModelContainer;
import edu.byu.cs.familymap.model.PersonModel;
import edu.byu.cs.familymap.otherActivities.FilterActivity;
import edu.byu.cs.familymap.personActivity.PersonActivity;
import edu.byu.cs.familymap.otherActivities.SearchActivity;
import edu.byu.cs.familymap.otherActivities.SettingsActivity;

/**
 * Created by chris on 8/4/2016.
 */
public class MyMapFragment extends Fragment {
    private SupportMapFragment amazonMapFragment;
    private AmazonMap myMap;
    private ArrayList<Marker> markers;
    private Button infoButton;
    private Map<String, Float> markerColors;
    private Menu mapMenu;
    private PersonModel currentPerson;

    public MyMapFragment()
    {

    }

    public static MyMapFragment newInstance()
    {
        MyMapFragment fragment=new MyMapFragment();
        Bundle args=new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceData)
    {
        super.onCreate(savedInstanceData);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceData)
    {
        View v = inflater.inflate(R.layout.map_fragment, container, false);

        setHasOptionsMenu(true);

        infoButton=(Button)v.findViewById(R.id.eventInfoButton);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentPerson!=null)
                {
                    PersonActivity person=new PersonActivity(currentPerson);
                }
            }
        });

        amazonMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        if (amazonMapFragment == null) {
            AmazonMapOptions options = new AmazonMapOptions();
            options.mapType(AmazonMap.MAP_TYPE_NORMAL);
            amazonMapFragment = SupportMapFragment.newInstance(options);
        }

        amazonMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(AmazonMap amazonMap) {
                myMap = amazonMap;

                markerColors=new HashMap<>();
                float base=360/(ModelContainer.getSingleton().getEventTypes().size()+2);
                int multiplier=1;
                for(String s:ModelContainer.getSingleton().getEventTypes())
                {
                    float hue=base*multiplier;
                    markerColors.put(s,hue);
                    multiplier++;
                }
                addMarkers();

                myMap.setOnMarkerClickListener(new AmazonMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        EventModel event=ModelContainer.getSingleton().getEvent(marker.getTitle());
                        String buttonText = event.getPerson().getFirstName() + " " +
                                event.getPerson().getLastName() + "\n" +
                                event.getDescription() + ": " + event.getCity() + ", " +
                                event.getCountry() + " (" + event.getYear() + ")";
                        infoButton.setText(buttonText);

                        myMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
                        currentPerson=event.getPerson();

                        Drawable genderIcon;
                        if(event.getPerson().getGender()=='m')
                        {
                            genderIcon = new IconDrawable(getActivity(), Iconify.IconValue.fa_male).
                                    colorRes(R.color.maleIcon).sizeDp(40);
                        }
                        else
                        {
                            genderIcon = new IconDrawable(getActivity(), Iconify.IconValue.fa_female).
                                    colorRes(R.color.femaleIcon).sizeDp(40);
                        }
                        return true;
                    }
                });
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.map_fragment_menu, menu);

        mapMenu=menu;

        MenuItem searchItem=mapMenu.getItem(0);
        Drawable searchIcon=new IconDrawable(getActivity(), Iconify.IconValue.fa_search)
                .colorRes(R.color.white).sizeDp(40);
        searchItem.setIcon(searchIcon);

        MenuItem filterItem=mapMenu.getItem(1);
        Drawable filterIcon=new IconDrawable(getActivity(), Iconify.IconValue.fa_filter)
                .colorRes(R.color.white).sizeDp(40);
        filterItem.setIcon(filterIcon);

        MenuItem settingsItem=mapMenu.getItem(2);
        Drawable settingsIcon=new IconDrawable(getActivity(), Iconify.IconValue.fa_gear)
                .colorRes(R.color.white).sizeDp(40);
        settingsItem.setIcon(settingsIcon);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.menuSearch:
            {
                SearchActivity search=new SearchActivity();
                return true;
            }
            case R.id.menuFilter:
            {
                FilterActivity filter=new FilterActivity();
                return true;
            }
            case R.id.menuSettings:
            {
                SettingsActivity settings=new SettingsActivity();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addMarkers()
    {
        markers=new ArrayList<>();
        for(EventModel event: ModelContainer.getSingleton().getEvents()) {
            LatLng position = new LatLng(event.getLatitude(), event.getLongitude());
            MarkerOptions opt = new MarkerOptions().position(position).title(event.getEventID())
                    .icon(BitmapDescriptorFactory.defaultMarker(markerColors.get(event.getDescription())));

            Marker m = myMap.addMarker(opt);
            markers.add(m);
        }
    }
}
