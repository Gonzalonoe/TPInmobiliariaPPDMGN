package com.example.tpinmobiliariappdmgn.ui.inicio;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.tpinmobiliariappdmgn.R;
import com.google.android.gms.maps.SupportMapFragment;

public class InicioFragment extends Fragment {

    private InicioViewModel vm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_inicio, container, false);

        vm = new ViewModelProvider(this).get(InicioViewModel.class);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

            vm.inicializarMapa(mapFragment, getViewLifecycleOwner());

        return root;
    }
}