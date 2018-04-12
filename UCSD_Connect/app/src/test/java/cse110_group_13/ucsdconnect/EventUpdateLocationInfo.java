package cse110_group_13.ucsdconnect;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Christopher on 11/24/2016.
 */

public class EventUpdateLocationInfo {

    @Test
    public void testUpdateInfo() throws Exception{

        Map<String, String> eventInfo = new HashMap<>();

        // fill with data
        eventInfo.put("Title", "EventTitle");
        eventInfo.put("Date", "EventDate");
        eventInfo.put("Time", "EventTime");
        eventInfo.put("Location", "EventLocation");
        eventInfo.put("Description", "EventDescription");
        eventInfo.put("Category", "EventCategory");
        eventInfo.put("Latitude", "EventLatitude");
        eventInfo.put("Longitude", "EventLongitude");

        Event event = new Event(eventInfo);

        assertEquals("EventTitle", event.name);
        assertEquals("EventDate", event.date);
        assertEquals("EventTime", event.time);
        assertEquals("EventLocation", event.location);
        assertEquals("EventDescription", event.description);
        assertEquals("EventCategory", event.category);
        assertEquals("EventLatitude", event.latitude);
        assertEquals("EventLongitude", event.longitude);

        eventInfo.put("Location", "NewEventLocation");
        eventInfo.put("Latitude", "NewEventLatitude");
        eventInfo.put("Longitude", "NewEventLongitude");

        Event newEvent = event.updateEventLocationInfo(eventInfo);

        assertEquals("EventTitle", newEvent.name);
        assertEquals("EventDate", newEvent.date);
        assertEquals("EventTime", newEvent.time);
        assertEquals("NewEventLocation", newEvent.location);
        assertEquals("EventDescription", newEvent.description);
        assertEquals("EventCategory", newEvent.category);
        assertEquals("NewEventLatitude", newEvent.latitude);
        assertEquals("NewEventLongitude", newEvent.longitude);
    }
}
