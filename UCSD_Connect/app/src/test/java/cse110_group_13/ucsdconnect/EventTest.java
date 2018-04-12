package cse110_group_13.ucsdconnect;

import android.support.annotation.NonNull;

import org.junit.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Acer on 11/16/2016.
 */
public class EventTest {
    @Test
    public void equals() throws Exception {
        HashMap<String, String> testInput = new HashMap<>();
        testInput.put("Title", "testTitle");
        testInput.put("Date", "testDate");
        testInput.put("Time", "testTime");
        testInput.put("Location", "testLocation");
        testInput.put("Description", "testDescription");
        testInput.put("Category", "testCategory");
        testInput.put("Latitude", "testLatitude");
        testInput.put("Longitude", "testLongtitude");

        // Test for true, Map constructor
        Event testEvent0 = new Event(testInput);
        Event testEvent1 = new Event(testInput);
        assertTrue(testEvent0.equals(testEvent1));

        // Test false, Map constructur
        testInput.put("Title", "testTitle1");
        Event testEvent2 = new Event(testInput);
        assertFalse(testEvent0.equals(testEvent2));

        // Name only constructor true
        Event testEvent3 = new Event("testTitle");
        assertTrue(testEvent0.equals(testEvent3));
    }

}