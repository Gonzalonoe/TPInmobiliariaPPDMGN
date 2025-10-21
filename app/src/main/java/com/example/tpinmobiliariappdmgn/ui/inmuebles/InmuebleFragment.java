package com.example.tpinmobiliariappdmgn.ui.inmuebles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;


import com.example.tpinmobiliariappdmgn.databinding.FragmentInmueblesBinding;
import com.example.tpinmobiliariappdmgn.models.Inmueble;

import java.util.List;

public class InmuebleFragment extends Fragment {

    private FragmentInmueblesBinding binding;
    private InmuebleViewModel vm;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        vm = new ViewModelProvider(this).get(InmuebleViewModel.class);
        binding = FragmentInmueblesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        vm.getListaInmuebles().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                InmuebleAdapter adapter = new InmuebleAdapter(getContext(), inmuebles,getLayoutInflater());
                GridLayoutManager glm=new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
                binding.listarinmuebles.setLayoutManager(glm);
                binding.listarinmuebles.setAdapter(adapter);
            }
        });
        vm.obtenerListaInmuebles();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}