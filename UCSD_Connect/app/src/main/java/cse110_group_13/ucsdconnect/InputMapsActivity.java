
package cse110_group_13.ucsdconnect;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

/*
 * Created by Thomas
 *
 * Class Notes / Sources:
 * https://developers.google.com/maps/documentation/android-api/start
 * https://developers.google.com/maps/documentation/android-api/infowindows
 * https://developers.google.com/android/reference/com/google/android/gms/maps/model/Marker
 *
 * Description:
 *
 * Public Methods: onMapReady(GoogleMap googleMap)
 *                 onMarkerClick(final Marker marker)
 *                 onInfoWindowClick(Marker marker)
 *                 onDialogPositiveClick(DialogFragment dialog)
 *                 onDialogNegativeClick(DialogFragment dialog)
 *                 onConnected(@Nullable Bundle bundle)
 *                 onConnectionSuspended(int i)
 *                 onConnectionFailed(@NonNull ConnectionResult connectionResult)
 *                 onLocationChanged(Location location)
 *                 onMarkerDragStart(Marker marker)
 *                 onMarkerDrag(Marker marker)
 *                 onMarkerDragEnd(Marker marker)
 */


public class InputMapsActivity extends FragmentActivity
        implements GoogleMap.OnMarkerDragListener, OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener,
        GoogleMap.OnInfoWindowClickListener, MapsDialog.MapsDialogListener {


    private final double UCSDLAT = 32.8801, UCSDLONG = -117.2340; // default map center coords
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    public static final String TAG = MapsActivity.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationRequest mLocationRequest;
    private LatLng userLatLng = new LatLng(UCSDLAT,UCSDLONG);
    public static String locationName = null;
    private LatLng finalMarkerPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_maps2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Set Event Location");

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(32.8801, -117.234);
        mMap.setOnMarkerDragListener(this);
        mMap.setOnInfoWindowClickListener(this);

    }

    @Override
    public boolean onMarkerClick(final Marker marker){

        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker){
        final LatLng eventLatLng = finalMarkerPosition;

        final Dialog dialog = new Dialog(InputMapsActivity.this);
        dialog.setContentView(R.layout.fragment_maps_dialog);
        dialog.setTitle("Enter name of Location");
        Button button = (Button) dialog.findViewById(R.id.EventLocationDialogOkayButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = (EditText) dialog.findViewById(R.id.EventLocationName);
                String text = editText.getText().toString();
                //Create Event Here. Latititude and Longitude stored in eventLatLng, event location title in text.

                // event will have info about created event
                Event event = CreateEventActivity.createdEvent;
                // set to null to remove access, don't want to see this later on somewhere
                CreateEventActivity.createdEvent = null;

                // create mapping to add location name, latitude and longitude
                Map<String, String> map = new HashMap<String, String>();
                map.put("Location", text);
                map.put("Latitude", Double.toString(eventLatLng.latitude));
                map.put("Longitude", Double.toString(eventLatLng.longitude));

                // update info passed to event
                event = event.updateEventLocationInfo(map);
                User.currentUser.addEvent(event);
                Toast.makeText(InputMapsActivity.this, "Event Creation Successful", Toast.LENGTH_LONG).show();
                startActivity(new Intent(InputMapsActivity.this, MenuActivity.class));
            }
        });
        dialog.show();
    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Location location = null;
        try{
            location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (location == null) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
            else {
                handleNewLocation(location);
            };
        }
        catch(SecurityException ex){
            //idk do something
        }

        Log.i(TAG, "Location services connected.");

    }
    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());
        userLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(userLatLng).title("Event Location").snippet("Drag to the location. Click to Finish.").draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userLatLng));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(17));

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended. Please reconnect.");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
           finalMarkerPosition = marker.getPosition();
    }
}

