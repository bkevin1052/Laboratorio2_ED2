package com.laboratoriodos.kevin.laboratorio2_ed2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

public class FilesActivity extends AppCompatActivity {

    public final int PICK_CHOOSE_FILE = 1;
    Button elegirArchivo;
    TextView contenido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files);

        elegirArchivo = (Button) findViewById(R.id.btnElegirArchivo);
        contenido = (TextView) findViewById(R.id.textViewContenido);

        elegirArchivo.setOnClickListener(view -> {

            Boolean hasPermission =( ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED);
            if (!hasPermission){
                Log.e("MainActivity", "get permision   ");
                ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }else {
                Log.e("MainActivity", "get permision-- already granted ");
                subirArchivo();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_CHOOSE_FILE:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        Uri uri  = data.getData();
                        String path = Environment.getExternalStorageDirectory() + "/" +uri.getPathSegments().get(0)+"s/"+"dataejemplo.txt";
                        lecturaArchivo(path);
                    } else {
                        Toast.makeText(getApplicationContext(), "ERROR AL LEER EL ARCHIVO", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:{
                if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    subirArchivo();
                }else {
                    // show a msg to user
                }
            }
        }
    }

    private String lecturaArchivo(String path){

        File archivo = new File(path);
        StringBuilder texto = new StringBuilder();
        try{
            InputStream f = new FileInputStream(archivo);
            BufferedReader br = new BufferedReader(new InputStreamReader(f));
            String inputLine;

            while ((inputLine = br.readLine()) != null) {
                texto.append(" " + inputLine);
            }
            br.close();
            contenido.setText(texto);
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "ERROR AL LEER EL ARCHIVO", Toast.LENGTH_SHORT).show();
        }
        return  texto.toString();
    }

    private void subirArchivo() {
        Intent subirArchivo = new Intent(Intent.ACTION_GET_CONTENT);
        subirArchivo.setType("text/*");
        subirArchivo.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(subirArchivo, "Seleccionar un archivo .txt"), PICK_CHOOSE_FILE);
        } catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Por favor seleccion un archivo correcto", Toast.LENGTH_SHORT).show();
        }
    }
}