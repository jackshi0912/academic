package cse110_group_13.ucsdconnect;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/*
 * Class Notes: Based on boilerplate code auto-generated by Android Studio.
 *
 * Description: This class creates an activity where the event details can be viewed.
 *
 * Public Methods: createIntent(Context context, Event event)
 *                 onFragmentInteraction(Uri uri)
 */

public class EventActivity extends AppCompatActivity implements EventActivityFragment.OnFragmentInteractionListener {
    private static final String ARG_EVENT = "event";

    public static Intent createIntent(Context context, Event event) {
        final Intent intent = new Intent(context, EventActivity.class);
        final Bundle params = new Bundle();
        params.putSerializable(ARG_EVENT, event);
        intent.putExtras(params);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Bundle params = getIntent().getExtras();
        if (params != null) {
            final Event event = (Event) params.getSerializable(ARG_EVENT);
            setTitle(event.name);

            final FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_content, EventActivityFragment.newInstance(event)).commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {}
}