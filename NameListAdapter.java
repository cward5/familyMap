package edu.byu.cs.familymap.personActivity;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import edu.byu.cs.familymap.R;

/**
 * Adapter to insert name information into ListView.
 * Created by chris on 8/6/2016.
 */
public class NameListAdapter extends ArrayAdapter<NameInfo> {
    private int resourceID;

    public NameListAdapter(Context context, int resource, List<NameInfo> objects) {
        super(context, resource, objects);
        resourceID = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(resourceID, null);
        }

        NameInfo info=getItem(position);

        TextView value=(TextView)convertView.findViewById(R.id.nameValueView);
        value.setText(info.getValue());

        TextView label=(TextView)convertView.findViewById(R.id.nameLabelView);
        label.setText(info.getLabel());

        return convertView;
    }
}
