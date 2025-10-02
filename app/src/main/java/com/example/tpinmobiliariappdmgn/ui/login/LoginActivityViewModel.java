package com.example.tpinmobiliariappdmgn.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tpinmobiliariappdmgn.MainActivity;
import com.example.tpinmobiliariappdmgn.R;
import com.example.tpinmobiliariappdmgn.ui.models.Usuario;

public class LoginActivityViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<String> userMutable;
    private MutableLiveData<String> errorMutable;
    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        context = getApplication();
    }

    public LiveData<String> getUserMutableLiveData(){
        if (userMutable == null){
            userMutable = new MutableLiveData<>();
        }
        return userMutable;
    }
    public LiveData<String> getErrorMutableLiveData(){
        if (errorMutable == null){
            errorMutable = new MutableLiveData<>();
        }
        return errorMutable;
    }

    public void validarUsuario(String email, String password){
        if (email.isEmpty() || password.isEmpty()) {
            errorMutable.setValue("Todos los campos son obligatorios");
            return;
        }

        if ( email.equals("Gonza") && password.equals("12345")) {
            Usuario usuario = new Usuario();
            usuario.setEmail(email);
            usuario.setPassword(password);
            usuario.setNombre("Gonzalo");
            usuario.setFoto(R.drawable.foto1);

            Intent intent = new Intent(getApplication(), MainActivity.class);
            intent.putExtra("User", usuario);
            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
            getApplication().startActivity(intent);
        }
    }
}
