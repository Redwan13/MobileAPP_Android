package com.mexpense.m_expense;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class LocationFragment extends Fragment implements OnMapReadyCallback {

    SupportMapFragment mapView;
    TextClock textClock;
    TextView textView, textView2, textView3, textView4, textView5;
    LocationRequest locationRequest;
    String local;
    GoogleMap map;
    List<Address> addresses;
    public double latitude;
    public double longitude;
    FusedLocationProviderClient fusedLocationProviderClient;

    private static final int REQUEST_CODE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        mapView = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        textClock = view.findViewById(R.id.timeclock);
        textView = view.findViewById(R.id.latitude);
        textView2 = view.findViewById(R.id.longitude);
        textView3 = view.findViewById(R.id.country);
        textView4 = view.findViewById(R.id.locality);
        textView5 = view.findViewById(R.id.address);
        mapView.getMapAsync(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
        //buildGoogleApiClient();
        return view;
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null){
                    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                    try {
                        //getting location from the user.
                        //fast checks the permession if allowed then proceed from the same.
                        //geocoder returns user loction
                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        latitude = addresses.get(0).getLatitude();
                        longitude =  addresses.get(0).getLongitude();
                        local = addresses.get(0).getCountryName();
                        //Html allows it return the text in css format.
                        textView.setText(Html.fromHtml("<font color='#6200EE'><b>Latitude :</b></br></font>" + latitude));
                        textView2.setText(Html.fromHtml("<font color='#6200EE'><b>Longitude :</b></br></font>" + longitude));
                        textView3.setText(Html.fromHtml("<font color='#6200EE'><b>Country :</b></br></font>" + local));
                        textView4.setText(Html.fromHtml("<font color='#6200EE'><b>Locality :</b></br></font>" + addresses.get(0).getLocality()));
                        textView5.setText(Html.fromHtml("<font color='#6200EE'><b>Address :</b></br></font>" + addresses.get(0).getAddressLine(0)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        //returns marker to where the user is on the map.
        map = googleMap;
        LatLng view = new LatLng(latitude, longitude);
        map.addMarker(new MarkerOptions().position(view).title(local));
        map.moveCamera(CameraUpdateFactory.newLatLng(view));

    }
}