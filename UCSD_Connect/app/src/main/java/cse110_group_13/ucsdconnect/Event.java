package cse110_group_13.ucsdconnect;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Description: This is a simple class to hold event item information.
 *
 * Public Methods: Event(String name)
 *                 Event(Map<String,String> eventInfo)
 *                 updateEventLocationInfo(Map<String, String> map)
 *                 equals(Object other)
 *                 hashcode()
 *                 filterEventsByCategory(List<Event> eventList, String category)
 *                 match(String query)
 *
 */

public class Event implements Serializable {
    public final String name;
    public final String date;
    public final String time;
    public final String location;
    public final String description;
    public final String category;
    public final String latitude;
    public final String longitude;
    public final String createdBy;
    public final List<String> plannedBy;
    public final List<String> blockedBy;

    public Event(String name){
        this.name = name;
        date = time = location = description = category = latitude = longitude = createdBy = null;
        plannedBy = blockedBy = null;
    }

    public Event(Map<String,String> eventInfo) {
        this.name = eventInfo.get("Title");
        this.date = eventInfo.get("Date");
        this.time = eventInfo.get("Time");
        this.location = eventInfo.get("Location");
        this.description = eventInfo.get("Description");
        this.category = eventInfo.get("Category");
        this.createdBy = eventInfo.get("CreatedBy");
        this.latitude = eventInfo.get("Latitude");
        this.longitude = eventInfo.get("Longitude");

        if (eventInfo.containsKey("PlannedBy")) {
            this.plannedBy = new ArrayList<String>();
            this.plannedBy.addAll(Arrays.asList(eventInfo.get("PlannedBy").split(",")));
        } else {
            this.plannedBy = new ArrayList<String>();
        }

        if (eventInfo.containsKey("BlockedBy")) {
            this.blockedBy = new ArrayList<String>();
            this.blockedBy.addAll(Arrays.asList(eventInfo.get("BlockedBy").split(",")));
        } else {
            this.blockedBy = new ArrayList<String>();
        }
    }

    private static String joinStrings(List<String> strings) {
        String output = "";
        for (String string : strings) {
            if (!output.isEmpty()) {
                output += ",";
            }
            output += string;
        }
        return output;
    }

    public Event updateEventLocationInfo(Map<String, String> map) {

        Map<String, String> eventInfo = new HashMap<>();
        // make a new map with most old info
        eventInfo.put("Title", this.name);
        eventInfo.put("Date", this.date);
        eventInfo.put("Time", this.time);
        eventInfo.put("Description", this.description);
        eventInfo.put("Category", this.category);
        eventInfo.put("CreatedBy", this.createdBy);
        eventInfo.put("PlannedBy", joinStrings(this.plannedBy));
        eventInfo.put("BlockedBy", joinStrings(this.blockedBy));
        eventInfo.put("Location", map.get("Location"));
        eventInfo.put("Latitude", map.get("Latitude"));
        eventInfo.put("Longitude", map.get("Longitude"));

        return new Event(eventInfo);
    }

    /*
     * equals method, returns true if the names are the same
     */
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;

        if (name == null && otherEvent.name == null) {
            return true;
        } else if (name == null || otherEvent.name == null) {
            return false;
        }

        return name.equals(otherEvent.name);
    }

    /*
     * Temporary hashcode function, needs to be improved.
     */
    public int hashcode() {

        int sum = 0;

        for (int index = 0; index < name.length(); index++) {
            sum += name.charAt(index);
        }

        return sum % 1001;
    }

    public static List<Event> filterEventsByCategory(List<Event> eventList, String category) {

        if (category == null) return null;

        List<Event> newEventList = new ArrayList<>();

        for (Event event : eventList) {

            if (event.category != null && event.category.equals(category)) newEventList.add(event);
        }

        return newEventList;
    }

    public boolean match(String query) {
        query = query.toLowerCase().trim();
        return query.length() == 0
                || name.toLowerCase().contains(query)
                || date.toLowerCase().contains(query)
                || time.toLowerCase().contains(query)
                || location.toLowerCase().contains(query)
                || description.toLowerCase().contains(query)
                || category.toLowerCase().contains(query);
    }
}
