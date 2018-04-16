package cse110_group_13.ucsdconnect;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;

import cse110_group_13.ucsdconnect.ItemFragment.OnListFragmentInteractionListener;

import java.util.ArrayList;
import java.util.List;

/*
 * Class Notes: Based on boilerplate code auto-generated by Android Studio.
 *
 * Description: RecyclerViewAdapter creates a view that can be reused for displaying events
 * in a list form to make displaying of events much simpler with filtered displays of events.
 *
 * Public Methods: MyItemRecyclerViewAdapter(OnListFragmentInteractionListener listener)
 *                 onCreateViewHolder(ViewGroup parent, int viewType)
 *                 setEvents(List<Event> mEvents)
 *                 setQuery(String mQuery)
 *                 onBindViewHolder(final ViewHolder holder, int position)
 *                 getItemCount()
 *
 */

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private List<Event> mEvents;
    private String mQuery;
    private final List<Event> mFilteredEvents;

    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(OnListFragmentInteractionListener listener) {
        mEvents = new ArrayList<>();
        mQuery = "";
        mFilteredEvents = new ArrayList<>();

        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    public void setEvents(List<Event> mEvents) {
        this.mEvents = mEvents;
        refreshFilteredEvents();
    }

    public void setQuery(String mQuery) {
        this.mQuery = mQuery;
        refreshFilteredEvents();
    }

    private void refreshFilteredEvents() {
        mFilteredEvents.clear();
        for (Event event : mEvents) {
            if (event.match(mQuery)) {
                mFilteredEvents.add(event);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setEvent(mFilteredEvents.get(position));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onEventInfoRequested(holder.mEvent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFilteredEvents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public final TextView mEventName;
        public final TextView mEventDate;
        public final TextView mEventLocation;
        public final TextView mEventTime;

        public final Button mEventInfoButton;
        public final Button mBlockEvent;
        public final Button mPlanEvent;

        private Event mEvent;

        boolean block = false;
        boolean plan = false;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            mEventName = (TextView) view.findViewById(R.id.event_name);
            mEventDate = (TextView) view.findViewById(R.id.event_date);
            mEventTime =  (TextView) view.findViewById(R.id.event_time);
            mEventLocation = (TextView) view.findViewById(R.id.event_location);

            mEventInfoButton = (Button) view.findViewById(R.id.event_info_button);
            mBlockEvent = (Button) view.findViewById(R.id.event_hide_button);
            mPlanEvent = (Button) view.findViewById(R.id.event_plan_button);

            mEventInfoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onEventInfoRequested(mEvent);
                }
            });

            mBlockEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    block = User.currentUser.isEventBlocked(mEvent);

                    if (!block) {
                        User.currentUser.addBlocked(mEvent);
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(mEvent.name.replaceAll("\\s+",""));
                    }

                    else {
                        User.currentUser.removeBlocked(mEvent);
                    }
                }
            });

            mPlanEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    plan = User.currentUser.isEventPlanned(mEvent);

                    if (!plan) {
                        User.currentUser.addAttend(mEvent);
                        FirebaseMessaging.getInstance().subscribeToTopic(mEvent.name.replaceAll("\\s+",""));
                    }

                    else {
                        User.currentUser.removeAttend(mEvent);
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(mEvent.name.replaceAll("\\s+",""));
                    }
                }
            });


        }

        @Override
        public String toString() {
            return super.toString() + " '" + mEventName.getText() + "'";
        }

        public void setEvent(Event event) {
            mEvent = event;

            Log.d("Changing Event", mEvent.name);
            mEventName.setText(mEvent.name);
            mEventDate.setText(mEvent.date);
            mEventLocation.setText(mEvent.location);

            mPlanEvent.setText(User.currentUser.isEventPlanned(mEvent) ? "Unplan" : "Plan");
            mBlockEvent.setText(User.currentUser.isEventBlocked(mEvent) ? "Unhide" : "Hide");
        }
    }
}