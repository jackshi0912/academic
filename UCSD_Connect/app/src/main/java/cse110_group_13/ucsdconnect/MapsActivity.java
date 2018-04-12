package cse110_group_13.ucsdconnect;

import android.content.IntentSender;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

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

import java.util.List;

/*
 * Created by Thomas
 *
 * Class Notes / Sources:
 * https://developers.google.com/maps/documentation/android-api/start
 * https://developers.google.com/maps/documentation/android-api/infowindows
 * https://developers.google.com/android/reference/com/google/android/gms/maps/model/Marker
 * http://blog.teamtreehouse.com/beginners-guide-location-android
 *
 * Description: This class initializes the google maps window including a tool bar at the top for
 * easy navigation back to the other parts of the app and an initiall location set at ucsd for
 * ease of viewing ucsd events.
 *
 * Public Methods: onMapReady(GoogleMap googleMap)
 *                 onMarkerClick(final Marker marker)
 *                 onConnected(@Nullable Bundle bundle)
 *                 onConnectionSuspended(int i)
 *                 onConnectionFailed(@NonNull ConnectionResult connectionResult)
 *                 onLocationChanged(Location location)
 *
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private final double UCSDLAT = 32.8801, UCSDLONG = -117.2340;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    public static final String TAG = MapsActivity.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationRequest mLocationRequest;
    private LatLng ucsdLatLng = new LatLng(UCSDLAT,UCSDLONG);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Nearby Events");

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

        mMap.setOnMarkerClickListener(this);

        EventFetcher.fetchEventList(new EventFetcher.EventFetchListener() {
            @Override
            public void eventListFetched(List<Event> events) {
                for(Event e: events){
                    if(e.longitude != null && e.latitude != null){
                        LatLng eLatLng = new LatLng(Double.parseDouble(e.latitude),Double.parseDouble(e.longitude));
                        Marker m = mMap.addMarker(new MarkerOptions().position(eLatLng));
                        m.setTag(e);
                    }
                }
            }
        });
        EventInfoWindowAdapter adapter = new EventInfoWindowAdapter(getLayoutInflater());
        mMap.setInfoWindowAdapter(adapter);
    }

    @Override
    public boolean onMarkerClick(final Marker marker){
        marker.showInfoWindow();
        return false;
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

        }

    }
    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());
        //Marker marker = mMap.addMarker(new MarkerOptions().position(userLatLng).title("UCSD Event").snippet("Event Title Event Location Event Date"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ucsdLatLng));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(17));

    }

    @Override
    public void onConnectionSuspended(int i) {
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
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }
}
