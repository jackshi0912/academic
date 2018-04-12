package cse110_group_13.ucsdconnect;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Christopher on 10/31/2016.
 *
 * Class Notes
 * In order to retreive data, the following stack overflow post was followed
 * http://stackoverflow.com/questions/32886546/how-to-get-all-child-list-from-firebase-android
 * This technique is used to retrieve data in all other classes.
 *
 * Description: This class is used to retrieve a list of events from fire base and creates a method
 * and uniform way for the event list to be used throughout our code.
 *
 * Public Methods: fetchEventList (final EventFetchListener listener)
 *
 */

public class EventFetcher {
    public interface EventFetchListener {
        void eventListFetched (List<Event> events);
    }

    public static void fetchEventList (final EventFetchListener listener) {

        if (User.currentUser == null || User.currentUser.blocked == null) return;

        final List<Event> events = new ArrayList<Event>();

        Firebase ref = new Firebase("https://ucsdconnect-25d00.firebaseio.com/Events/");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {

                    Map<String, String> map = eventSnapshot.getValue(Map.class);

                    Log.d("Event constructor", "Adding fields");

                    Event nextEvent = new Event(map);
                    if (!events.contains(nextEvent) && !User.currentUser.blocked.contains(nextEvent)) events.add(nextEvent);

                    Log.d("Creating events", map.keySet().toString());

                }

                listener.eventListFetched(events);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}