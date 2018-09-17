package com.laboratoriodos.kevin.laboratorio2_ed2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.codekidlabs.storagechooser.StorageChooser;
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
    public final int PICK_FOLDER = 2;
    public static int seleccion = 0;
    public static String ruta;
    Button btnElegirArchivo, btnComprimir;
    TextView contenido;
    Huffman cifrado;
    String data;
    Uri uriName;
    double bytesOriginal, bytesComprimido;
    DecimalFormat df = new DecimalFormat("##.##");
    int[] caracteresContador;


    //METODOS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //ASIGNACION ID
        btnElegirArchivo = (Button) findViewById(R.id.btnElegirArchivo);
        btnComprimir = (Button) findViewById(R.id.btnComprimir);
        contenido = (TextView) findViewById(R.id.textViewContenido);
        btnComprimir.setVisibility(View.INVISIBLE);

        //botones y textviews
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
                    caracteresContador = new int[256];
                    for (char c : cifrado.getCadena().toCharArray()) {
                        caracteresContador[c]++;
                    }
                    data = cifrado.cifrar(cifrado.arbolHuffman(caracteresContador), cifrado.getCadena());
                    contenido.setText(data);

                        StorageChooser chooser = new StorageChooser.Builder()
                                .withActivity(FilesActivity.this)
                                .withFragmentManager(getFragmentManager())
                                .withMemoryBar(true)
                                .allowCustomPath(true)
                                .setType(StorageChooser.DIRECTORY_CHOOSER)
                                .build();
                        chooser.show();
                        chooser.setOnSelectListener(path -> {
                            escrituraArchivo(path);
                            finish();
                            startActivity(new Intent(getApplicationContext(),ListFilesActivity.class));
                        });
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
                        uriName = uri;
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
            btnComprimir.setVisibility(View.VISIBLE);
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
    private void escrituraArchivo(String path) {

        switch (seleccion) {
            case 1:
                try {


                    File f = new File(path, "dataComprimida.huff");
                    ruta = f.getPath();

                    if(!f.exists()){
                        f.createNewFile();
                    }

                    FileOutputStream fos = new FileOutputStream(f);
                    OutputStreamWriter file = new OutputStreamWriter(fos);
                    file.write(data.getBytes().toString());
                    file.flush();
                    file.close();
                    //obtenerFrecuencias(cifrado.arbolHuffman(caracteresContador),new StringBuffer());
                    bytesComprimido = data.getBytes().toString().length();
                    double razonCompresion, factorCompresion, porcentajeReduccion;
                    razonCompresion = Double.parseDouble(df.format(bytesComprimido / bytesOriginal));
                    factorCompresion = Double.parseDouble(df.format(bytesOriginal / bytesComprimido));
                    porcentajeReduccion = Double.parseDouble(df.format((bytesComprimido / bytesOriginal) * 100));
                    Toast.makeText(getApplicationContext(), "Compresion realizada correctamente en " + path, Toast.LENGTH_SHORT).show();
                    ListFilesActivity.listaArchivos.add(new Archivo(
                            "dataComprimida.huff",
                            path,
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


//    public void obtenerFrecuencias(Arbol arbol, StringBuffer prefijo) {
//        try {
//            if (arbol instanceof Hoja) {
//                Hoja hoja = (Hoja) arbol;//casting
//                //ESCRIBIR DATOS
//                FileWriter f = new FileWriter(FilesActivity.ruta,true);
//                BufferedWriter bw = new BufferedWriter(f);
//                bw.write("\n"+hoja.valor +"\t"+hoja.frecuencia+"\t\t"+prefijo);
//                bw.close();
//
//
//            } else if (arbol instanceof Nodo) {
//                Nodo nodo = (Nodo) arbol;
//                prefijo.append('0');
//                obtenerFrecuencias(nodo.izquierda, prefijo);
//                prefijo.deleteCharAt(prefijo.length() - 1);
//                prefijo.append('1');
//                obtenerFrecuencias(nodo.derecha, prefijo);
//                prefijo.deleteCharAt(prefijo.length() - 1);
//            }
//        }catch (Exception e){
//            //mensaje de error
//        }
//    }
}