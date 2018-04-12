package cse110_group_13.ucsdconnect;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by Christopher on 11/23/2016.
 */

public class EventEquals {

    @Test
    public void testEquals() throws Exception {

        Map<String, String> eventInfo1 = new HashMap<>();

        // fill with data
        eventInfo1.put("Title", "EventTitle1");
        eventInfo1.put("Date", "EventDate1");
        eventInfo1.put("Time", "EventTime1");
        eventInfo1.put("Location", "EventLocation1");
        eventInfo1.put("Description", "EventDescription1");
        eventInfo1.put("Category", "EventCategory1");
        eventInfo1.put("Latitude", "EventLatitude1");
        eventInfo1.put("Longitude", "EventLongitude1");

        Event event1 = new Event(eventInfo1);

        Map<String, String> eventInfo2 = new HashMap<>();

        // fill with data
        eventInfo2.put("Title", "EventTitle2");
        eventInfo2.put("Date", "EventDate2");
        eventInfo2.put("Time", "EventTime2");
        eventInfo2.put("Location", "EventLocation2");
        eventInfo2.put("Description", "EventDescription2");
        eventInfo2.put("Category", "EventCategory2");
        eventInfo2.put("Latitude", "EventLatitude2");
        eventInfo2.put("Longitude", "EventLongitude2");

        Event event2 = new Event(eventInfo2);

        assertEquals(event1.equals(event2), false);

        // fill with data
        eventInfo2.put("Title", "EventTitle1");
        eventInfo2.put("Date", "EventDate1");
        eventInfo2.put("Time", "EventTime1");
        eventInfo2.put("Location", "EventLocation1");
        eventInfo2.put("Description", "EventDescription1");
        eventInfo2.put("Category", "EventCategory1");
        eventInfo2.put("Latitude", "EventLatitude1");
        eventInfo2.put("Longitude", "EventLongitude1");

        event2 = new Event(eventInfo2);

        assertEquals(event1.equals(event2), true);

        eventInfo2.put("Title", "EventTitle2");
        event2 = new Event(eventInfo2);
        assertEquals(event1.equals(event2), false);

        assertEquals(event1.equals(event1), true);

    }
}
