package cse110_group_13.ucsdconnect;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Thomas on 11/12/2016.
 *
 * Description: This class creates a popup window for events in Google Maps view.
 *
 * Public Methods: EventInfoWindowAdapter(LayoutInflater inflater)
 *                 getInfoContents(Marker marker)
 *                 getInfoWindow(Marker marker)
 */

public class EventInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private View eventInfo = null;
    private LayoutInflater inflater = null;

    public EventInfoWindowAdapter(LayoutInflater inflater){
        this.inflater = inflater;
    }

    public View getInfoContents(Marker marker){
        View v =  inflater.inflate(R.layout.info_window,null);
        Event e = (Event) marker.getTag();


        //Build popup to be displayed with event info
        TextView nameTV = (TextView) v.findViewById(R.id.infoWindowEventName);
        nameTV.setText(e.name);
        TextView timeTV = (TextView) v.findViewById(R.id.infoWindowEventTime);
        timeTV.setText(e.time);
        TextView dateTV = (TextView) v.findViewById(R.id.infoWindowEventDate);
        dateTV.setText(e.date);


        return v;
    }
    public View getInfoWindow(Marker marker){
        return null;
    }
}
