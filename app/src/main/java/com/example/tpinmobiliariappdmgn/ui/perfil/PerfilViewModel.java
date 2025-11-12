package com.example.tpinmobiliariappdmgn.ui.perfil;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tpinmobiliariappdmgn.models.Propietario;
import com.example.tpinmobiliariappdmgn.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> mEstado = new MutableLiveData<>();
    private MutableLiveData<String> mNombre = new MutableLiveData<>();
    private MutableLiveData<Propietario> propietario = new MutableLiveData<>();

    public PerfilViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Boolean> getmEstado() { return mEstado; }

    public LiveData<String> getmNombre() { return mNombre; }

    public LiveData<Propietario> getPropietario() { return propietario; }

    public void cambioboton(String nombreboton, String nombre, String apellido, String dni, String telefono, String email) {
        if (nombreboton.equalsIgnoreCase("EDITAR")) {
            mEstado.setValue(true);
            mNombre.setValue("GUARDAR");
        } else {
            if (!validarCampos(nombre, apellido, dni, email)) {
                return;
            }

            mEstado.setValue(false);
            mNombre.setValue("EDITAR");

            Propietario actualizado = new Propietario();
            actualizado.setIdpropietario(propietario.getValue().getIdpropietario());
            actualizado.setNombre(nombre);
            actualizado.setApellido(apellido);
            actualizado.setDni(dni);
            actualizado.setTelefono(telefono);
            actualizado.setEmail(email);

            String token = ApiClient.leerToken(getApplication());
            ApiClient.InmoServicio api = ApiClient.getInmoServicio();
            Call<Propietario> call = api.ActualizarPropietario("Bearer " + token, actualizado);

            call.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplication(), "ACTUALIZADO CON ÉXITO", Toast.LENGTH_SHORT).show();
                        propietario.postValue(response.body());
                    } else {
                        Toast.makeText(getApplication(), "⚠ERROR AL ACTUALIZAR (" + response.code() + ")", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Propietario> call, Throwable throwable) {
                    Toast.makeText(getApplication(), "ERROR EN LA API: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean validarCampos(String nombre, String apellido, String dni, String email) {
        if (nombre.trim().isEmpty() || apellido.trim().isEmpty() || dni.trim().isEmpty() || email.trim().isEmpty()) {
            Toast.makeText(getApplication(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            int dniNumero = Integer.parseInt(dni);
            if (dniNumero <= 0) {
                Toast.makeText(getApplication(), "El DNI debe ser un número válido", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getApplication(), "El DNI debe ser numérico", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getApplication(), "Ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void obtenerPerfil() {
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmoServicio api = ApiClient.getInmoServicio();
        Call<Propietario> call = api.obtenerPerfil("Bearer " + token);

        call.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()) {
                    propietario.postValue(response.body());
                } else {
                    Log.d("Perfil", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable throwable) {
                Log.e("Perfil", "Error:" + throwable.getMessage());
            }
        });
    }
}
