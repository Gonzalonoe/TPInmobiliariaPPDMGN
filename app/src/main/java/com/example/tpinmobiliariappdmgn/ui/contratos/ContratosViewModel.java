package com.example.tpinmobiliariappdmgn.ui.contratos;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.tpinmobiliariappdmgn.models.Contrato;
import com.example.tpinmobiliariappdmgn.models.Inmueble;
import com.example.tpinmobiliariappdmgn.request.ApiClient;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratosViewModel extends AndroidViewModel {
    private MutableLiveData<List<Inmueble>> listaInmueblesContratos = new MutableLiveData<>();
    private  List<Contrato> listaContratos;
    List<Inmueble> inmueblesConContrato;
    private Contrato contrato;
    public ContratosViewModel(@NonNull Application application) {
        super(application);
        listaContratos = new ArrayList<>();
        inmueblesConContrato = new ArrayList<>();
        contrato = new Contrato();
    }
    public LiveData <List<Inmueble>> getListaInmueblesContratos(){
        return listaInmueblesContratos;
    }

    public void obtenerListaInmueblesContratos() {
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmoServicio api = ApiClient.getInmoServicio();
        Call<List<Contrato>> call = api.getContratos("Bearer " + token);

        call.enqueue(new Callback<List<Contrato>>() {
            @Override
            public void onResponse(Call<List<Contrato>> call, Response<List<Contrato>> response) {
                if (response.isSuccessful()) {

                    listaContratos.clear();
                    inmueblesConContrato.clear();

                    listaContratos.addAll(response.body());
                    for (Contrato c : listaContratos) {
                        inmueblesConContrato.add(c.getInmueble());
                    }
                    listaInmueblesContratos.postValue(inmueblesConContrato);
                    Toast.makeText(getApplication(),
                            "Cantidad de inmuebles con contratos activos " + inmueblesConContrato.size(),
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplication(),
                            "No se obtuvieron Contratos",
                            Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<Contrato>> call, Throwable throwable) {
                Log.d("errorContrato", throwable.getMessage());
                Toast.makeText(getApplication(),
                        "Error al obtener Contrato",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}

