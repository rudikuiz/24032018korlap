package com.piramidsoft.korlap.fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.piramidsoft.korlap.R;

public class Maps extends Fragment {


    private GoogleMap mMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_maps, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                mMap = googleMap;

                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_user_tie);
                LatLng loc1 = new LatLng(-7.291285, 112.747940);
                LatLng loc2 = new LatLng(-7.285198, 112.745322);
                LatLng loc3 = new LatLng(-7.296862, 112.752918);
                mMap.addMarker(new MarkerOptions().position(loc1).title("Kristina Malik").icon(icon));
                mMap.addMarker(new MarkerOptions().position(loc2).title("Sheila Divens").icon(icon));
                mMap.addMarker(new MarkerOptions().position(loc3).title("Wailon Dalton").icon(icon));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(loc1));
                mMap.animateCamera( CameraUpdateFactory.zoomTo( 15.0f ) );


            }
        });

        return view;
    }
}
