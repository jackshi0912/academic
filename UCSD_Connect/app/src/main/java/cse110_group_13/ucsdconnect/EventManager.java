package cse110_group_13.ucsdconnect;

import android.text.TextUtils;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Christopher on 11/11/2016.
 *
 * Class Notes: In order to retrieve data, the following stack overflow post was followed
 * http://stackoverflow.com/questions/32886546/how-to-get-all-child-list-from-firebase-android
 * This technique is used to retrieve data in all other classes.
 *
 * Sending data to the database was followed by this tutorial
 * https://www.youtube.com/watch?v=hoF5A30fbu4
 * This technique is used to send data to the database in all other classes
 *
 * Description: This class creates methods that deal with the modification of event lists that
 * are formed based on identifiers given by the user for example planned events, and adds those
 * events that have been selected to a list of the user's planned events.
 *
 * Public Methods: EventManager()
 *                 getBlockedEvents(FirebaseUser user, final List<Event> currentList)
 *                 getAttendEvents(FirebaseUser user, final List<Event> currentList)
 *                 getCreatedEvents(final FirebaseUser user, final List<Event> currentList)
 *                 addBlockedEvent(FirebaseUser user, Event event, List<Event> currentList)
 *                 addAttendEvent(FirebaseUser user, Event event, List<Event> currentList)
 *                 removeBlockedEvent(FirebaseUser user, Event event, List<Event> currentList)
 *                 addToDatabase(Event event)
 *                 getEventInfo(Event event)
 *                 checkUid(Event event)
 *
 */

public class EventManager {

    private Firebase mRef;

    public EventManager() {
        // get  a reference to the database
        mRef = new Firebase("https://ucsdconnect-25d00.firebaseio.com/");
    }

    public void getBlockedEvents(FirebaseUser user, final List<Event> currentList) {

        if (user == null) return;

        mRef.child("Users").child(user.getUid()).child("BlockedEvents").addValueEventListener(
                new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {

                    // get next event, see if its already in the list before adding
                    String event = eventSnapshot.getValue(String.class);
                    Event nextEvent = new Event(event);
                    if (!currentList.contains(nextEvent)) currentList.add(nextEvent);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }

    public void getAttendEvents(FirebaseUser user, final List<Event> currentList) {

        if (user == null) return;

        mRef.child("Users").child(user.getUid()).child("PlannedEvents").addValueEventListener(
                new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {

                    // get next event, see if its already in the list before adding
                    String event = eventSnapshot.getValue(String.class);
                    Event nextEvent = new Event(event);
                    if (!currentList.contains(nextEvent)) currentList.add(nextEvent);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void getCreatedEvents(final FirebaseUser user, final List<Event> currentList) {

        if (user == null) return;

        mRef.child("Events").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {

                            // get next event, see if its already in the list before adding
                            Map<String, String> eventInfo = eventSnapshot.getValue(Map.class);
                            Event nextEvent = new Event(eventInfo);
                            if ((!currentList.contains(nextEvent)) && eventInfo != null &&
                                    eventInfo.get("CreatedBy") != null &&
                                    eventInfo.get("CreatedBy").equals(user.getUid()))
                                currentList.add(nextEvent);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
    }


    public void addBlockedEvent(FirebaseUser user, Event event, List<Event> currentList) {

        // check to make sure everything is valid
        if (user == null || event == null || event.blockedBy.contains(user.getUid())) return;

        // add to current list and to firebase
        currentList.add((event));
        event.blockedBy.add(user.getUid());
        mRef.child("Events").child(Integer.valueOf(event.hashcode()).toString())
                .child("BlockedBy").setValue(TextUtils.join(",", event.blockedBy));
    }

    public void addAttendEvent(FirebaseUser user, Event event, List<Event> currentList) {

        // check to make sure everything is valid
        if (user == null || event == null || event.plannedBy.contains(user.getUid())) return;

        // add to current list and to firebase
        currentList.add((event));
        event.plannedBy.add(user.getUid());
        mRef.child("Events").child(Integer.valueOf(event.hashcode()).toString())
                .child("PlannedBy").setValue(TextUtils.join(",", event.plannedBy));

        Log.d("[Event Manager]", "Adding event to planned events");
    }

    public void removeBlockedEvent(FirebaseUser user, Event event, List<Event> currentList) {

        // check input
        if (user == null || event == null || !event.blockedBy.contains(user.getUid())) return;

        currentList.remove(event);
        event.blockedBy.remove(user.getUid());
        mRef.child("Events").child(Integer.valueOf(event.hashcode()).toString())
                .child("BlockedBy").setValue(TextUtils.join(",", event.blockedBy));

    }

    public void removeAttendEvent(FirebaseUser user, Event event, List<Event> currentList) {

        // check input
        if (user == null || event == null || !event.plannedBy.contains(user.getUid())) return;

        currentList.remove(event);
        event.plannedBy.remove(user.getUid());
        mRef.child("Events").child(Integer.valueOf(event.hashcode()).toString())
                .child("PlannedBy").setValue(TextUtils.join(",", event.plannedBy));
    }

    public void addToDatabase(Event event) {

        mRef.child("Events").child(Integer.valueOf(event.hashcode()).toString()).child(
                "Title").setValue(event.name);

        mRef.child("Events").child(Integer.valueOf(event.hashcode()).toString()).child(
                "Date").setValue(event.date);

        mRef.child("Events").child(Integer.valueOf(event.hashcode()).toString()).child(
                "Time").setValue(event.time);

        mRef.child("Events").child(Integer.valueOf(event.hashcode()).toString()).child(
                "Location").setValue(event.location);

        mRef.child("Events").child(Integer.valueOf(event.hashcode()).toString()).child(
                "Category").setValue(event.category);

        mRef.child("Events").child(Integer.valueOf(event.hashcode()).toString()).child(
                "Description").setValue(event.description);

        mRef.child("Events").child(Integer.valueOf(event.hashcode()).toString()).child(
                "Latitude").setValue(event.latitude);

        mRef.child("Events").child(Integer.valueOf(event.hashcode()).toString()).child(
                "Longitude").setValue(event.longitude);

        mRef.child("Events").child(Integer.valueOf(event.hashcode()).toString()).child(
                "CreatedBy").setValue(User.currentUser.user.getUid());

        mRef.child("Events").child(Integer.valueOf(event.hashcode()).toString()).child(
                "PlannedBy").setValue("");

        mRef.child("Events").child(Integer.valueOf(event.hashcode()).toString()).child(
                "BlockedBy").setValue("");
    }

    public Map<String, String> getEventInfo(Event event) {

        Firebase ref = new Firebase(mRef.toString() + "Events/" + event.hashcode());

        final List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot eventInfoSnapshot : dataSnapshot.getChildren()) {

                    Map<String, String> map = eventInfoSnapshot.getValue(Map.class);

                    mapList.add(map);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        if (mapList.size() > 0) return mapList.get(0);
        else return null;

    }

    static ArrayList<String> userId;
    public static boolean uidEqual;

    public boolean checkUid(Event event) {

        userId = new ArrayList<String>();


        mRef.child("Events").child(Integer.valueOf(event.hashcode()).toString()).child(
                "CreatedBy").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                uidEqual = (dataSnapshot.getValue()).equals(User.currentUser.user.getUid());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        if (userId.size() != 0) {
            Log.d("[Event Manager]", userId.get(0) + " " + User.currentUser.user.getUid() + " " +
                    userId.equals(User.currentUser.user.getUid()));

            return userId.get(0).equals(User.currentUser.user.getUid());
        }
        return false;
    }
}
