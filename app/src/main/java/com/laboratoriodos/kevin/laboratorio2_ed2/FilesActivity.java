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

import com.laboratoriodos.kevin.laboratorio2_ed2.clases.Huffman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FilesActivity extends AppCompatActivity {

    public final int PICK_CHOOSE_FILE = 1;
    public static int seleccion = 0;
    Button btnElegirArchivo,btnCifrar;
    TextView contenido;

    Huffman cifrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files);

        btnElegirArchivo = (Button) findViewById(R.id.btnElegirArchivo);
        btnCifrar = (Button)findViewById(R.id.btnCifrar);
        contenido = (TextView) findViewById(R.id.textViewContenido);

        btnElegirArchivo.setOnClickListener(view -> {

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

        btnCifrar.setOnClickListener(view ->{
            switch (seleccion){
                case 1:
                    cifrado = new Huffman(contenido.getText().toString());
                    int[] caracteresContador = new int[256];
                    for(char c: cifrado.getCadena().toCharArray()){
                        caracteresContador[c]++;
                    }
                    contenido.setText(cifrado.cifrar(cifrado.arbolHuffman(caracteresContador),cifrado.getCadena()));
                    crearArchivo();
                    break;
                case 2:
                    //LZW
                    break;
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
                        contenido.setText(lecturaArchivo(uri));
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
                    Toast.makeText(getApplicationContext(), "ERROR AL LEER EL ARCHIVO", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private String lecturaArchivo(Uri uri){

        StringBuilder texto = new StringBuilder();
        try{
            InputStream f = getContentResolver().openInputStream(uri);
            BufferedReader br = new BufferedReader(new InputStreamReader(f));
            String inputLine;

            while ((inputLine = br.readLine()) != null) {
                texto.append(" " + inputLine);
            }
            br.close();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "ERROR AL LEER EL ARCHIVO", Toast.LENGTH_SHORT).show();
        }
        return  texto.toString();
    }

    private void subirArchivo() {
        Intent subirArchivo = new Intent("com.sec.android.app.myfiles.PICK_DATA");
        subirArchivo.putExtra("CONTENT_TYPE", "*/*");
        subirArchivo.addCategory(Intent.CATEGORY_DEFAULT);
        try {
            startActivityForResult(Intent.createChooser(subirArchivo, "Seleccionar un archivo .txt"), PICK_CHOOSE_FILE);
        } catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Por favor seleccion un archivo correcto", Toast.LENGTH_SHORT).show();
        }
    }

    private void crearArchivo(){
        try {
            File nuevaCarpeta = new File(Environment.getExternalStorageDirectory(), "CarpetaDePrueba");
            if (!nuevaCarpeta.exists()) {
                nuevaCarpeta.mkdir();
            }
            try {
                File file = new File(nuevaCarpeta, "dataejemplo" + ".huff");
                file.createNewFile();
                FileOutputStream
            } catch (Exception ex) {
                Log.e("Error", "ex: " + ex);
            }
        } catch (Exception e) {
            Log.e("Error", "e: " + e);
        }
    }
}