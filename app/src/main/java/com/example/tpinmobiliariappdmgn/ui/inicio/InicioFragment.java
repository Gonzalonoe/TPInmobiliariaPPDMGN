package com.example.tpinmobiliariappdmgn.ui.inicio;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.tpinmobiliariappdmgn.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

public class InicioFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private InicioViewModel vm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_inicio, container, false);

        vm = new ViewModelProvider(this).get(InicioViewModel.class);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        return root;
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        vm.getUbicacionInicial().observe(getViewLifecycleOwner(), latLng -> {
            if (mMap != null) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Ubicación inicial"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f));
            }
        });
    }
}
