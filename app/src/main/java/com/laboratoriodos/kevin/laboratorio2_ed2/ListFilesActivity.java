package com.laboratoriodos.kevin.laboratorio2_ed2;

import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.laboratoriodos.kevin.laboratorio2_ed2.clases.Adapter;
import com.laboratoriodos.kevin.laboratorio2_ed2.clases.Archivo;
import java.util.LinkedList;
import java.util.List;

public class ListFilesActivity extends AppCompatActivity {

    RecyclerView RecyclerViewMisCompresiones;
    Adapter adapterMisCompresiones;
    public static List<Archivo> listaArchivos = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_files);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerViewMisCompresiones = (RecyclerView)findViewById(R.id.RecyclerViewMisCompresiones);
        RecyclerViewMisCompresiones.setLayoutManager(new LinearLayoutManager(this));
        adapterMisCompresiones = new Adapter(this,listaArchivos);
        RecyclerViewMisCompresiones.setAdapter(adapterMisCompresiones);

        guardarDatos();

        adapterMisCompresiones.setOnClickListener(view ->
        {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Esta seguro en descomprimir el archivo?");
            dlgAlert.setTitle("Descomprimir archivo");
            dlgAlert.setPositiveButton("SI", (dialogInterface, i) -> {
                //CORRER ALGORITMO DE DECODIFICACION
            });
            dlgAlert.setNegativeButton("NO", (dialogInterface, i) -> dialogInterface.cancel());
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        });
    }

    private void guardarDatos(){
        if(listaArchivos.size() != 0) {
            SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(ListFilesActivity.listaArchivos);
            editor.putString("lista archivos", json);
            editor.apply();
        }
    }

}
