package com.example.tpinmobiliariappdmgn.ui.logout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import com.example.tpinmobiliariappdmgn.databinding.FragmentLogoutBinding;


public class LogoutFragment extends Fragment {

    private FragmentLogoutBinding binding;
    private LogoutViewModel logoutViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        logoutViewModel = new ViewModelProvider(this).get(LogoutViewModel.class);
        binding = FragmentLogoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        logoutViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mostrarDialogoDeSalida();
    }

    private void mostrarDialogoDeSalida() {
        if (getContext() == null) {
            return;
        }

        new AlertDialog.Builder(requireContext())
                .setTitle("Confirmar Salida")
                .setMessage("¿Estás seguro de que deseas salir de la aplicación?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (getActivity() != null) {
                            getActivity().finishAffinity();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (getParentFragment() != null) {
                            NavHostFragment.findNavController(LogoutFragment.this).popBackStack();
                        } else if (getActivity() != null) {
                        }
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}