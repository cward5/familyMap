package edu.byu.cs.familymap.personActivity;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.byu.cs.familymap.R;

/**
 * Created by chris on 8/6/2016.
 */
public class EventListAdapter extends BaseExpandableListAdapter {
    private ArrayList<String> eventInfo;
    private ArrayList<String> familyInfo;
    private Activity activity;

    @Override
    public int getGroupCount() {
        return 2;
    }

    @Override
    public int getChildrenCount(int i) {
        if(i==0)
            return eventInfo.size();
        else return familyInfo.size();
    }

    @Override
    public Object getGroup(int i) {
        if(i==0)
            return eventInfo;
        else return familyInfo;
    }

    @Override
    public Object getChild(int i, int i1) {
        if(i==0)
            return eventInfo.get(i1);
        else return familyInfo.get(i1);
    }

    @Override
    public long getGroupId(int i) {
        if(i==0)
            return eventInfo.hashCode();
        else return familyInfo.hashCode();
    }

    @Override
    public long getChildId(int i, int i1) {
        if(i==0)
            return eventInfo.get(i1).hashCode();
        else return familyInfo.get(i1).hashCode();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if(view ==  null) {
            view = activity.getLayoutInflater().inflate(R.layout.event_list_header, null);
        }

        TextView header=(TextView)view;
        if(i==0)
            header.setText("LIFE EVENTS");
        else header.setText("FAMILY");

        return header;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if(view ==  null) {
            view = activity.getLayoutInflater().inflate(R.layout.event_list_item, null);
        }

        TextView child=(TextView)view;
        child.setText((String)getChild(i,i1));

        return child;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setEventInfo(ArrayList<String> eventInfo) {
        this.eventInfo = eventInfo;
    }

    public void setFamilyInfo(ArrayList<String> familyInfo) {
        this.familyInfo = familyInfo;
    }
}
