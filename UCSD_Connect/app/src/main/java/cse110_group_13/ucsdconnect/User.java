package cse110_group_13.ucsdconnect;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/*
 * Created by Christopher on 11/3/2016.
 *
 * Description: Main purpose of this class is to act as a somewhat of a facade to the eventManager
 * class and to ensure that only one user is signed in at a time
 *
 * Public Methods: User()
 *                 addBlocked(Event mEvent)
 *                 addAttend(Event mEvent)
 *                 removeBlocked(Event event)
 *                 removeAttend(Event event)
 *                 checkInDatabase(Event event)
 *                 addEvent(Event event)
 *                 createdEvent(Event event)
 *                 getEventManager()
 *                 getEventInfo(Event event)
 *                 isEventPlanned(Event event)
 *                 isEventBlocked(Event event)
 *
 */

public class User {

    public FirebaseUser user;

    public static User currentUser;

    // sorted by date
    private EventManager eventManager;
    public ArrayList<Event> attend;
    public ArrayList<Event> blocked;
    public ArrayList<Event> created;


    public User() {

        // get the user
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return;

        // create all of the fields
        blocked = new ArrayList<Event>();
        attend = new ArrayList<Event>();
        created = new ArrayList<Event>();
        eventManager = new EventManager();

        // get the attened and blocked events
        eventManager.getAttendEvents(user, attend);
        eventManager.getBlockedEvents(user, blocked);
        eventManager.getCreatedEvents(user, created);

    }

    public void addBlocked(Event mEvent) {

        eventManager.addBlockedEvent(user, mEvent, blocked);

    }

    public void addAttend(Event mEvent) {

        eventManager.addAttendEvent(user, mEvent, attend);
    }

    public void removeBlocked(Event event) {

        eventManager.removeBlockedEvent(user, event, blocked);
    }

    public void removeAttend(Event event) {

        eventManager.removeAttendEvent(user, event, attend);
    }

    public boolean checkInDatabase(Event event) {

        List<Event> allEvents = new ArrayList<Event>();
        allEvents.addAll(blocked);

        return allEvents.contains(event);
    }

    public void addEvent(Event event) {

        eventManager.addToDatabase(event);
    }

    public boolean createdEvent(Event event) {

        for (Event createdEvent : created) {
            Log.d("[User]", "The event is: " + createdEvent.name);
        }

        Log.d("[User]", "The number of created events is: " + created.size());

        return created.contains(event);
        /*
        eventManager.checkUid(event);
        return EventManager.uidEqual;
        */
    }
/*
    public List<Event> getCreatedEvents() {

        List<Event> eventList = new ArrayList<Event>();

        for (Event event: ItemFragment.eventListContainer.mEvents) {

            if (createdEvent(event)) {
                eventList.add(event);
            }
        }

        return eventList;
    }*/

    public EventManager getEventManager() {
        return eventManager;
    }

    public Map<String, String> getEventInfo(Event event) {

        return eventManager.getEventInfo(event);
    }

    public boolean isEventPlanned(Event event) {
        return event.plannedBy.contains(user.getUid());
    }

    public boolean isEventBlocked(Event event) {
        return event.blockedBy.contains(user.getUid());
    }
}
