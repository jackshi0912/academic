package cse110_group_13.ucsdconnect;

import java.io.Serializable;

/*
 * Description: Serializable event filters that can be passed into the events list reusable
 * fragment as an argument (note that fragment arguments must be Serializable). Controls which
 * events are displayed in the list. Note that events can be filtered further by searching in
 * the UI.
 *
 * Classes : DefaultEvents
 *           CreatedEvents
 *           PlannedEvents
 *           BlockedEvents
 *           CategoryEvents
 */

public interface EventFilter extends Serializable {
    // called with every event which may be displayed in the list.
    boolean filterEvent(Event event); // true => include event in list; false => don't.

    // show all events (global view), minus the user's hidden events
    class DefaultEvents implements EventFilter {
        private final String uid;

        public DefaultEvents(String uid) {
            this.uid = uid;
        }

        @Override
        public boolean filterEvent(Event event) {
            return !event.blockedBy.contains(uid);
        }
    }

    // show all events a specific user has created
    class CreatedEvents implements EventFilter {
        private final String uid;

        public CreatedEvents(String uid) {
            this.uid = uid;
        }

        @Override
        public boolean filterEvent(Event event) {
            return event.createdBy != null && event.createdBy.equals(uid);
        }
    }

    // show all events a specific user has planned
    class PlannedEvents implements EventFilter {
        private final String uid;

        public PlannedEvents(String uid) {
            this.uid = uid;
        }

        @Override
        public boolean filterEvent(Event event) {
            return event.plannedBy.contains(uid);
        }
    }

    // show all events a specific user has hidden
    class BlockedEvents implements EventFilter {
        private final String uid;

        public BlockedEvents(String uid) {
            this.uid = uid;
        }

        @Override
        public boolean filterEvent(Event event) {
            return event.blockedBy.contains(uid);
        }
    }

    // show all events in a particular category
    class CategoryEvents implements EventFilter {
        private final String category;

        public CategoryEvents(String category) {
            this.category = category;
        }

        @Override
        public boolean filterEvent(Event event) {
            return event.category.equals(category);
        }
    }
}
