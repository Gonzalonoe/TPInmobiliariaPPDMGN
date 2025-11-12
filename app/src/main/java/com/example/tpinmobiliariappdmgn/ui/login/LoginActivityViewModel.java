package com.example.tpinmobiliariappdmgn.ui.login;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.SensorEvent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tpinmobiliariappdmgn.MainActivity;
import com.example.tpinmobiliariappdmgn.models.Propietario;
import com.example.tpinmobiliariappdmgn.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityViewModel extends AndroidViewModel {

    private MutableLiveData<String> mMensaje = new MutableLiveData<>();
    private MutableLiveData<Boolean> llamar = new MutableLiveData<>(false);

    private static final int SHAKE_THRESHOLD = 800;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;

    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMMensaje() {
        return mMensaje;
    }

    public LiveData<Boolean> getLlamar() {
        return llamar;
    }

    public void logueo(String usuario, String contrasenia) {
        if (usuario.trim().isEmpty() || contrasenia.trim().isEmpty()) {
            mMensaje.setValue("Error: campos vacíos.");
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

                    obtenerPerfilYRedirigir(token);
                } else {
                    mMensaje.postValue("Usuario o contraseña incorrectos.");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                mMensaje.postValue("Error de conexión con el servidor.");
                Log.e("Login", throwable.getMessage());
            }
        });
    }

    private void obtenerPerfilYRedirigir(String token) {
        ApiClient.InmoServicio api = ApiClient.getInmoServicio();
        Call<Propietario> call = api.obtenerPerfil("Bearer " + token);

        call.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()) {
                    ApiClient.guardarPropietario(getApplication(), response.body());
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    getApplication().startActivity(intent);
                } else {
                    mMensaje.postValue("Error al obtener el perfil.");
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable throwable) {
                mMensaje.postValue("Error de conexión al obtener perfil.");
                Log.e("Perfil", "Error: " + throwable.getMessage());
            }
        });
    }

    public void detectarMovimiento(SensorEvent event) {
        if (event.sensor.getType() != android.hardware.Sensor.TYPE_ACCELEROMETER) return;

        long curTime = System.currentTimeMillis();
        if ((curTime - lastUpdate) > 100) {
            long diffTime = curTime - lastUpdate;
            lastUpdate = curTime;

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;

            if (speed > SHAKE_THRESHOLD) {
                llamar.postValue(true);
            }

            last_x = x;
            last_y = y;
            last_z = z;
        }
    }

    public void hacerLlamada(Context context) {
        String telefono = "tel:1122334455";
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(telefono));

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            context.startActivity(intent);
        } else {
            mMensaje.setValue("Permiso de llamada no concedido.");
        }
        llamar.setValue(false);
    }

    public void validarPermisoLlamada(int requestCode, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mMensaje.setValue("Permiso de llamada concedido.");
            } else {
                mMensaje.setValue("Permiso de llamada denegado.");
            }
        }
    }
}
