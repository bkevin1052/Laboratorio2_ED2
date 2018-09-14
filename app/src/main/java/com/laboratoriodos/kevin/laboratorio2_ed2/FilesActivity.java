package com.laboratoriodos.kevin.laboratorio2_ed2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.laboratoriodos.kevin.laboratorio2_ed2.clases.Archivo;
import com.laboratoriodos.kevin.laboratorio2_ed2.clases.Huffman;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;

public class FilesActivity extends AppCompatActivity {

    //VARIABLES
    public final int PICK_CHOOSE_FILE = 1;
    public static int seleccion = 0;
    Button btnElegirArchivo, btnComprimir;
    TextView contenido;
    Huffman cifrado;
    String data;
    double bytesOriginal, bytesComprimido;
    DecimalFormat df = new DecimalFormat("##.##");


    //METODOS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files);

        btnElegirArchivo = (Button) findViewById(R.id.btnElegirArchivo);
        btnComprimir = (Button) findViewById(R.id.btnComprimir);
        contenido = (TextView) findViewById(R.id.textViewContenido);

        contenido.setMovementMethod(new ScrollingMovementMethod());

        btnElegirArchivo.setOnClickListener(view -> {

            //VERIFICAR PERMISOS
            Boolean hasPermission = (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED);
            if (!hasPermission) {
                Log.e("MainActivity", "get permision   ");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                Log.e("MainActivity", "get permision-- already granted ");
                subirArchivo();
            }
        });

        btnComprimir.setOnClickListener(view -> {
            switch (seleccion) {
                case 1:
                    cifrado = new Huffman(contenido.getText().toString());
                    int[] caracteresContador = new int[256];
                    for (char c : cifrado.getCadena().toCharArray()) {
                        caracteresContador[c]++;
                    }
                    data = cifrado.cifrar(cifrado.arbolHuffman(caracteresContador), cifrado.getCadena());
                    contenido.setText(data);
                    //VERIFICAR PERMISOS
                    Boolean hasPermission = (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED);
                    if (!hasPermission) {
                        Log.e("MainActivity", "get permision   ");
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                    } else {
                        Log.e("MainActivity", "get permision-- already granted ");
                        escrituraArchivo();
                    }
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
                        Uri uri = data.getData();
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
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    subirArchivo();
                } else {
                    Toast.makeText(getApplicationContext(), "ERROR AL LEER EL ARCHIVO", Toast.LENGTH_SHORT).show();
                }
            }

            case 2: {
                if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    escrituraArchivo();
                } else {
                    Toast.makeText(getApplicationContext(), "ERROR AL CREAR EL ARCHIVO", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private String lecturaArchivo(Uri uri) {
        StringBuilder texto = new StringBuilder();
        try {
            InputStream f = getContentResolver().openInputStream(uri);
            BufferedReader br = new BufferedReader(new InputStreamReader(f));
            String inputLine;

            while ((inputLine = br.readLine()) != null) {
                texto.append(" " + inputLine);
            }
            br.close();

            bytesOriginal = texto.toString().getBytes().length;

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "ERROR AL LEER EL ARCHIVO", Toast.LENGTH_SHORT).show();
        }
        return texto.toString();
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

    //FALTA SELECCION DE RUTA
    private void escrituraArchivo() {

        switch (seleccion) {
            case 1:
                //Obtiene ruta de sdcard
                File pathToExternalStorage = Environment.getExternalStorageDirectory();

                //agrega directorio /Documents
                File appDirectory = new File(pathToExternalStorage.getAbsolutePath() + "/Documents/");

                //Si no existe la estructura, se crea usando mkdirs()
                appDirectory.mkdirs();

                //Crea archivo
                File saveFilePath = new File(appDirectory, "dataejemplo.huff");

                try {
                    FileOutputStream fos = new FileOutputStream(saveFilePath);
                    OutputStreamWriter file = new OutputStreamWriter(fos);
                    file.write(data.getBytes().toString());
                    file.flush();
                    file.close();
                    bytesComprimido = data.getBytes().toString().length();
                    double razonCompresion, factorCompresion, porcentajeReduccion;
                    razonCompresion = Double.parseDouble(df.format(bytesComprimido / bytesOriginal));
                    factorCompresion = Double.parseDouble(df.format(bytesOriginal / bytesComprimido));
                    porcentajeReduccion = Double.parseDouble(df.format((bytesComprimido / bytesOriginal) * 100));
                    Toast.makeText(getApplicationContext(), "Compresion realizada correctamente en " + saveFilePath.getPath(), Toast.LENGTH_SHORT).show();
                    ListFilesActivity.listaArchivos.add(new Archivo(
                            saveFilePath.getName(),
                            saveFilePath.getPath(),
                            razonCompresion,
                            factorCompresion,
                            porcentajeReduccion,
                            "HUFFMAN",
                            R.drawable.iconolista));
                } catch (FileNotFoundException e) {
                    Log.i("ARCHIVO", e.toString());
                } catch (IOException e) {
                    Log.i("ARCHIVO", e.toString());
                }
                break;
            case 2:
                //LZW
                break;
        }
    }
}