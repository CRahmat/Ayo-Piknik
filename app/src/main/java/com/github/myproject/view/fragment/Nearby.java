package com.github.myproject.view.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.myproject.R;
import com.github.myproject.nearby_adapter.GetNearPlaces;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Nearby extends Fragment implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private static final int Request_User_Location_Code = 99;
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentUserLocationMarker;
    private double latitide, longitude;
    private int ProximityRadius = 10000;
    private Button searchAddress;
    private TextView vacationNearby;
    private TextView hotelNearby;
    private TextView restaurantNearby;
    private MapView mapView;
    private ImageView showCategories;
    private LinearLayout categoriesLayout;
    private boolean showCategoriesBoolean = false;

    public Nearby() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nearby, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkUserLocationPermission();
        }

        //Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapView = view.findViewById(R.id.map);
        searchAddress = view.findViewById(R.id.search_address);
        vacationNearby = view.findViewById(R.id.vacation_nearby);
        hotelNearby = view.findViewById(R.id.hotel_nearby);
        restaurantNearby = view.findViewById(R.id.restaurant_nearby);
        showCategories = view.findViewById(R.id.nearby_categories_show);
        categoriesLayout = view.findViewById(R.id.nearby_layout_categories);
        mapView.onCreate(null);
        mapView.onResume();
        mapView.getMapAsync(this);
        final String tourist_attraction = "tourist_attraction", lodging = "lodging", restaurant = "restaurant", atm = "atm";
        final Object transferData[] = new Object[2];
        final GetNearPlaces getNearbyPlaces = new GetNearPlaces();

        showCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showCategoriesBoolean == false) {
                    categoriesLayout.setVisibility(View.VISIBLE);
                    categoriesLayout.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_scale_animation));
                    showCategories.setImageResource(R.drawable.ic_chevron_up);
                    showCategoriesBoolean = true;
                } else {
                    categoriesLayout.setVisibility(View.GONE);
                    showCategories.setImageResource(R.drawable.ic_chevron_buttom);
                    showCategoriesBoolean = false;
                }
            }
        });
        searchAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText addressField = (EditText) view.findViewById(R.id.location_search);
                String address = addressField.getText().toString();

                List<Address> addressList = null;
                MarkerOptions userMarkerOptions = new MarkerOptions();

                if (!TextUtils.isEmpty(address)) {
                    Geocoder geocoder = new Geocoder(getContext());

                    try {
                        addressList = geocoder.getFromLocationName(address, 6);

                        if (addressList != null) {
                            for (int i = 0; i < addressList.size(); i++) {
                                Address userAddress = addressList.get(i);
                                LatLng latLng = new LatLng(userAddress.getLatitude(), userAddress.getLongitude());

                                userMarkerOptions.position(latLng);
                                userMarkerOptions.title(address);
                                userMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                                mMap.addMarker(userMarkerOptions);
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
                            }
                        } else {
                            Toast.makeText(getContext(), R.string.not_found_nearby, Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), R.string.write_search_nearby, Toast.LENGTH_SHORT).show();
                }
            }
        });
        vacationNearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                String url = getUrl(latitide, longitude, tourist_attraction);
                transferData[0] = mMap;
                transferData[1] = url;
                try {
                    Toast.makeText(getContext(), R.string.search_destination_nearby, Toast.LENGTH_SHORT).show();
                    getNearbyPlaces.execute(transferData);
                    Toast.makeText(getContext(), R.string.result_destination_nearby, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getContext(), R.string.please_wait, Toast.LENGTH_SHORT).show();
                }
            }
        });
        vacationNearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                String url = getUrl(latitide, longitude, atm);
                transferData[0] = mMap;
                transferData[1] = url;
                try {

                    Toast.makeText(getContext(), R.string.search_atm_nearby, Toast.LENGTH_SHORT).show();
                    getNearbyPlaces.execute(transferData);
                    Toast.makeText(getContext(), R.string.result_atm_nearby, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getContext(), R.string.please_wait, Toast.LENGTH_SHORT).show();
                }
            }
        });

        hotelNearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                String url = getUrl(latitide, longitude, lodging);
                transferData[0] = mMap;
                transferData[1] = url;
                try {

                    Toast.makeText(getContext(), R.string.search_hotel_nearby, Toast.LENGTH_SHORT).show();
                    getNearbyPlaces.execute(transferData);
                    Toast.makeText(getContext(), R.string.result_hotel_nearby, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getContext(), R.string.please_wait, Toast.LENGTH_SHORT).show();
                }
            }
        });

        restaurantNearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                String url = getUrl(latitide, longitude, restaurant);
                transferData[0] = mMap;
                transferData[1] = url;
                try {

                    Toast.makeText(getContext(), R.string.search_restaurant_nearby, Toast.LENGTH_SHORT).show();
                    getNearbyPlaces.execute(transferData);
                    Toast.makeText(getContext(), R.string.result_restaurant_nearby, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getContext(), R.string.please_wait, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private String getUrl(double latitide, double longitude, String nearbyPlace) {
        StringBuilder googleURL = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googleURL.append("location=" + latitide + "," + longitude);
        googleURL.append("&radius=" + ProximityRadius);
        googleURL.append("&type=" + nearbyPlace);
        googleURL.append("&sensor=true");
        googleURL.append("&key=" + "AIzaSyADWlvOFdw2hxpdhhNFJVjwcs8vQ3zwU14");

        Log.d("GoogleMapsActivity", "url = " + googleURL.toString());

        return googleURL.toString();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }


    }

    public boolean checkUserLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Request_User_Location_Code:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (googleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(getContext(), "Permission Denied...", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
    }


    @Override
    public void onLocationChanged(Location location) {
        latitide = location.getLatitude();
        longitude = location.getLongitude();

        lastLocation = location;

        if (currentUserLocationMarker != null) {
            currentUserLocationMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Lokasi Anda Sekarang");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));

        currentUserLocationMarker = mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(12));

        if (googleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1100);
        locationRequest.setFastestInterval(1100);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
