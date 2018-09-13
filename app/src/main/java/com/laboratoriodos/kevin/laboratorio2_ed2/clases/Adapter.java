package com.laboratoriodos.kevin.laboratorio2_ed2.clases;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.laboratoriodos.kevin.laboratorio2_ed2.R;

import java.util.List;


public class Adapter extends RecyclerView.Adapter<Adapter.ProductoViewHolder> implements View.OnClickListener{

    private Context miContexto;
    private List<Archivo> listaArchivos;
    private View.OnClickListener listener;

    public Adapter(Context miContexto, List<Archivo> listaArchivos) {
        this.miContexto = miContexto;
        this.listaArchivos = listaArchivos;
    }


    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(miContexto);
        View view = inflater.inflate(R.layout.costume_row_miscompresiones,null);
        ProductoViewHolder holder = new ProductoViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {

        Archivo archivo = listaArchivos.get(position);
        holder.textViewFactorReduccion.setText("Hola que hace");
    }

    public void eliminar(List<Archivo> lista)
    {
        listaArchivos = lista;
    }

    @Override
    public int getItemCount() {
        return listaArchivos.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener!= null){
            listener.onClick(view);
        }
    }


    class ProductoViewHolder extends RecyclerView.ViewHolder{
        TextView textViewNombre, textViewRuta, textViewRazonCompresion,textViewFactorCompresion,textViewFactorReduccion;

        ImageView imageView;

        public ProductoViewHolder(View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.txtNombreArchivo);
            textViewRuta = itemView.findViewById(R.id.txtRuta);
            textViewRazonCompresion = itemView.findViewById(R.id.txtRazonCompresion);
            textViewFactorCompresion = itemView.findViewById(R.id.txtFactorCompresion);
            textViewFactorReduccion = itemView.findViewById(R.id.txtFactorDeReduccion);
            imageView = itemView.findViewById(R.id.imageView);
        }

    }
}
