package com.nahuelcabrera.huellas.ui.fragment;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.nahuelcabrera.huellas.R;

import java.util.Arrays;
import java.util.Objects;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PetLocationFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "PetLocationFragment";

    //MAP
    private SupportMapFragment supportMapFragment;
    private GoogleMap googleMap;

    private FusedLocationProviderClient fusedLocationProviderClient;

    public PetLocationFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pet_location, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frag_gm_pet_location);
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;

        if (PermissionChecker.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && PermissionChecker.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        this.googleMap.setMyLocationEnabled(true);


        LatLng petLatLng1 = new LatLng(-34.6188126, -58.3677217);
        LatLng petLatLng2 = new LatLng(-34.9208142, -57.9518059);

        //Location1
        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.position(petLatLng1).title("Sucursal 1");

        //Location2
        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.position(petLatLng2).title("Sucursal 2");


        this.googleMap.addMarker(markerOptions1);
        this.googleMap.addMarker(markerOptions2);
        moveCamera(this.googleMap, petLatLng1, 15.0F);

        getDeviceLocation(this.googleMap);

    }

    private void getDeviceLocation(GoogleMap map){

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));

        try {
            Task<Location> location = fusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Log.d(TAG, "onComplete: Found Location");

                    Location currentLocation = task.getResult();
                    assert currentLocation != null;
                    moveCamera(map, new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 11.0F);

                } else {
                    Log.d(TAG, "onComplete: Unable to get location");
                    Toast.makeText(getContext(), "Unable to get location", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (SecurityException e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

    }

    private void moveCamera(GoogleMap map, LatLng latLng, float zoom){
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }
}
