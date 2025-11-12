package com.example.tpinmobiliariappdmgn.ui.inicio;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class InicioViewModel extends ViewModel implements OnMapReadyCallback {

    private MutableLiveData<LatLng> ubicacionInicial = new MutableLiveData<>();
    private GoogleMap mMap;

    public InicioViewModel() {
        ubicacionInicial.setValue(new LatLng(-32.346615913819285, -65.00322983307319));
    }

    public MutableLiveData<LatLng> getUbicacionInicial() {
        return ubicacionInicial;
    }

    public void inicializarMapa(SupportMapFragment mapFragment, LifecycleOwner owner) {
        mapFragment.getMapAsync(this);

        ubicacionInicial.observe(owner, latLng -> {
            if (mMap != null) {
                actualizarMapa(latLng);
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.mMap = googleMap;
        LatLng posicion = ubicacionInicial.getValue();
        if (posicion != null) {
            actualizarMapa(posicion);
        }
    }

    private void actualizarMapa(LatLng latLng) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("Ubicación inicial"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f));
    }

    public void actualizarUbicacion(double lat, double lng) {
        ubicacionInicial.setValue(new LatLng(lat, lng));
    }
}
