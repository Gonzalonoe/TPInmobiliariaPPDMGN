package com.example.tpinmobiliariappdmgn.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tpinmobiliariappdmgn.MainActivity;
import com.example.tpinmobiliariappdmgn.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<String> mMensaje;

    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        context = getApplication();
    }

    public LiveData<String> getMMensaje() {
        if (mMensaje == null) {
            mMensaje = new MutableLiveData<>();
        }
        return mMensaje;
    }

    public void logueo(String usuario, String contrasenia) {
        if (usuario.isEmpty() || contrasenia.isEmpty()) {
            mMensaje.setValue("Error, campos vacíos");
            return;
        }

        ApiClient.InmoServicio api = ApiClient.getInmoServicio();
        Call<String> call = api.loginForm(usuario, contrasenia);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String token = response.body();
                    ApiClient.guardarToken(getApplication(), token);
                    ApiClient.InmoServicio api2 = ApiClient.getInmoServicio();
                    Call<com.example.tpinmobiliariappdmgn.models.Propietario> perfilCall =
                            api2.obtenerPerfil("Bearer " + token);

                    perfilCall.enqueue(new Callback<com.example.tpinmobiliariappdmgn.models.Propietario>() {
                        @Override
                        public void onResponse(Call<com.example.tpinmobiliariappdmgn.models.Propietario> call,
                                               Response<com.example.tpinmobiliariappdmgn.models.Propietario> response) {
                            if (response.isSuccessful()) {
                                com.example.tpinmobiliariappdmgn.models.Propietario propietario = response.body();
                                ApiClient.guardarPropietario(getApplication(), propietario);

                                Intent intent = new Intent(getApplication(), MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                getApplication().startActivity(intent);
                            } else {
                                mMensaje.postValue("Error al obtener el perfil");
                            }
                        }

                        @Override
                        public void onFailure(Call<com.example.tpinmobiliariappdmgn.models.Propietario> call, Throwable t) {
                            mMensaje.postValue("Error de conexión al obtener perfil");
                            Log.e("Perfil", "Error: " + t.getMessage());
                        }
                    });

                } else {
                    mMensaje.postValue("Usuario o contraseña incorrectos");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                mMensaje.postValue("Error de conexión con el servidor");
                Log.e("Login", throwable.getMessage());
            }
        });
    }
}








