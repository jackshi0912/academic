package cse110_group_13.ucsdconnect;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Christopher on 11/23/2016.
 */

public class EventHashCode {

    @Test
    public void testHashCode() throws Exception {

        Map<String, String> map = new HashMap<>();

        map.put("Title", "");

        Event event = new Event(map);

        assertEquals(event.hashcode(), 0);

        map.put("Title", "Test");
        event = new Event(map);

        assertEquals(event.hashcode(), ('T' + 'e' + 's' + 't') % 1001);
    }
}
