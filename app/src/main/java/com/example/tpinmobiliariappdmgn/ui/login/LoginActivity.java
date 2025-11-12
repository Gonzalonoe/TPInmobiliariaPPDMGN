package com.example.tpinmobiliariappdmgn.ui.login;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.tpinmobiliariappdmgn.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity implements SensorEventListener {

    private LoginActivityViewModel vm;
    private ActivityLoginBinding binding;

    private SensorManager sensorManager;
    private Sensor acelerometro;

    private static final int REQUEST_CALL_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        vm = new ViewModelProvider(this).get(LoginActivityViewModel.class);

        vm.getMMensaje().observe(this, s ->
                Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show()
        );

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        acelerometro = sensorManager != null ? sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) : null;

        vm.getLlamar().observe(this, llamar -> {
            if (llamar) vm.hacerLlamada(LoginActivity.this);
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
        }

        binding.btLogin.setOnClickListener(v -> {
            String usu = binding.etEmail.getText().toString();
            String contra = binding.etPassword.getText().toString();
            vm.logueo(usu, contra);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (acelerometro != null) {
            sensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        vm.detectarMovimiento(event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        vm.validarPermisoLlamada(requestCode, grantResults);
    }
}
