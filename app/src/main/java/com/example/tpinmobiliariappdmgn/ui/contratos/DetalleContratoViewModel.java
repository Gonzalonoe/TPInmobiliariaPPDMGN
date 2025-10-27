package com.example.tpinmobiliariappdmgn.ui.contratos;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tpinmobiliariappdmgn.models.Contrato;
import com.example.tpinmobiliariappdmgn.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleContratoViewModel extends AndroidViewModel {
    private MutableLiveData<Contrato> mContrato;
    public DetalleContratoViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<Contrato> getMContrato(){
        if (mContrato == null){
            mContrato = new MutableLiveData<>();
        }
        return mContrato;
    }
    public void recuperarContrato(Bundle bundle){
        Contrato contrato = (Contrato) bundle.get("contratoBundle");
        if (contrato!= null){
            mContrato.setValue(contrato);
        }
    }
}