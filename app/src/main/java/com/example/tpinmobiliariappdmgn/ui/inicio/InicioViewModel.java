package com.example.tpinmobiliariappdmgn.ui.inicio;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;

public class InicioViewModel extends ViewModel {

    private final MutableLiveData<LatLng> ubicacionInicial = new MutableLiveData<>();

    public InicioViewModel() {
        ubicacionInicial.setValue(new LatLng(-34.6037, -58.3816));
    }

    public LiveData<LatLng> getUbicacionInicial() {
        return ubicacionInicial;
    }
}
