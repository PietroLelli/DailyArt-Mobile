package com.example.dailyart;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dailyart.MuseumRecyclerView.Museum;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Vector;

public class MapFragment extends Fragment implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private SearchView sv_map;
    private Museum markerMuseum = null;
    private Museum currentMuseum;
    private Museum markerMuseum2;

    private DatabaseReference dbRef;

    private List<MarkerOptions> markers;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    public MapFragment(Museum currentMuseum) {
        this.currentMuseum = currentMuseum;
    }

    public MapFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        sv_map = view.findViewById(R.id.sv_map);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        markers = new Vector<>();
        markerMuseum = null;

        sv_map.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = sv_map.getQuery().toString();
                List<Address> addressList = null;
                if(location != null && !location.equals("")){
                    Geocoder geocoder = new Geocoder(getContext());
                    try {
                        addressList = geocoder.getFromLocationName(location,1);
                        if(addressList != null){
                            Address address = addressList.get(0);
                            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Nessun risultato trovato!", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mapFragment.getMapAsync(this);
        return view;
    }

    @SuppressLint("MissingPermission")
    public void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            return;
        }
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        enableMyLocation();

        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.setPadding(0, 150, 0, 0);

        dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://dailyart-mobile-default-rtdb.firebaseio.com/");
        mMap.setOnMarkerClickListener(this);

        setMarker();

        if(currentMuseum != null){
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentMuseum.getLatitude(), currentMuseum.getLongitude()), 15));
        }
    }

    public void setMarker() {
        if (markers != null) {
            markers.clear();
        }
        dbRef.child("Museum").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Museum museum = dataSnapshot.getValue(Museum.class);
                    MarkerOptions m = new MarkerOptions().title(museum.getName()).position(new LatLng(museum.getLatitude(), museum.getLongitude()));
                    mMap.addMarker(m);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        return false;
    }

    public Museum getMarkerMuseum(String museumName) {
        dbRef.child("Museum").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String getMuseum = dataSnapshot.child("name").getValue(String.class);
                    if (getMuseum.equals(museumName)) {
                        markerMuseum = dataSnapshot.getValue(Museum.class);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        return markerMuseum;
    }

    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {
        markerMuseum2 = getMarkerMuseum(marker.getTitle());
        if(markerMuseum2 != null) {
            Utilities.insertFragment((AppCompatActivity) getActivity(), new MuseumDetailsFragment(markerMuseum), MuseumDetailsFragment.class.getSimpleName());
        }
    }
}