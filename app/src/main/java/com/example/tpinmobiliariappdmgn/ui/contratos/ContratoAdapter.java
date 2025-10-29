package com.example.tpinmobiliariappdmgn.ui.contratos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tpinmobiliariappdmgn.R;
import com.example.tpinmobiliariappdmgn.models.Contrato;
import com.example.tpinmobiliariappdmgn.models.Inmueble;
import com.bumptech.glide.Glide;
import com.example.tpinmobiliariappdmgn.request.ApiClient;

import java.util.List;

public class ContratoAdapter extends RecyclerView.Adapter<com.example.tpinmobiliariappdmgn.ui.contratos.ContratoAdapter.viewHolderContrato> {
    private Context context;
    private List<Inmueble> listado;
    private LayoutInflater li;

    public ContratoAdapter(Context context, List<Inmueble> listado, LayoutInflater li) {
        this.context = context;
        this.listado = listado;
        this.li = li;
    }

    @NonNull
    @Override
    public ContratoAdapter.viewHolderContrato onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = li.inflate(R.layout.item_inmueble, parent, false);
        return new ContratoAdapter.viewHolderContrato(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContratoAdapter.viewHolderContrato holder, int position) {
        Inmueble inmuebleActual = listado.get(position);
        holder.direccion.setText("Dirección: " + inmuebleActual.getDireccion());
        Glide.with(context)
                .load(ApiClient.BASE_URL + inmuebleActual.getImagen())
                .placeholder(R.drawable.listarinmuebles)
                .error("null")
                .into(holder.imagen);
        ((ContratoAdapter.viewHolderContrato) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("inmuebleBundle", inmuebleActual);
                Navigation.findNavController((Activity)context, R.id.nav_host_fragment_content_main).navigate(R.id.detalleContratoFragment, bundle);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listado.size();
    }

    public class viewHolderContrato extends RecyclerView.ViewHolder {

        TextView direccion;
        ImageView imagen;

        public viewHolderContrato(@NonNull View itemView) {
            super(itemView);
            direccion = itemView.findViewById(R.id.tvDireccion);
            imagen = itemView.findViewById(R.id.imgPortada);
        }
    }
}

