package com.laboratoriodos.kevin.laboratorio2_ed2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.laboratoriodos.kevin.laboratorio2_ed2.clases.Archivo;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public Button btnHuffman, btnLZW, btnMisCompresiones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnHuffman = (Button) findViewById(R.id.btnHuffman);
        btnLZW = (Button) findViewById(R.id.btnLzw);
        btnMisCompresiones = (Button) findViewById(R.id.btnMisCompresiones);


        btnHuffman.setOnClickListener(view -> {
            FilesActivity.seleccion = 1;
            startActivity(new Intent(getApplicationContext(), FilesActivity.class));
        });

        btnLZW.setOnClickListener(view -> {
            FilesActivity.seleccion = 2;
            startActivity(new Intent(getApplicationContext(), FilesActivity.class));
        });

        btnMisCompresiones.setOnClickListener(view -> {
            cargarDatos();
        });
    }


    private void cargarDatos() {

        try {
            if (ListFilesActivity.listaArchivos.size() == 0) {
                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                Gson gson = new Gson();
                String json = sharedPreferences.getString("lista archivos", null);
                Type type = new TypeToken<ArrayList<Archivo>>() {
                }.getType();
                ListFilesActivity.listaArchivos = gson.fromJson(json, type);
            } else {
                startActivity(new Intent(getApplicationContext(), ListFilesActivity.class));
            }
        }catch(Exception e){
            Toast.makeText(getApplicationContext(),"No hay ninguna compresion aun",Toast.LENGTH_SHORT).show();
        }
    }
}
