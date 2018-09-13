package com.laboratoriodos.kevin.laboratorio2_ed2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

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
        RecyclerViewMisCompresiones = (RecyclerView)findViewById(R.id.RecyclerViewMisCompresiones);
        RecyclerViewMisCompresiones.setLayoutManager(new LinearLayoutManager(this));
        adapterMisCompresiones = new Adapter(this,listaArchivos);
        RecyclerViewMisCompresiones.setAdapter(adapterMisCompresiones);

    }
}
