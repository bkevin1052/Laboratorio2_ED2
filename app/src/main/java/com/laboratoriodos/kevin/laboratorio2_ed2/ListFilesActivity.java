package com.laboratoriodos.kevin.laboratorio2_ed2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.codekidlabs.storagechooser.StorageChooser;
import com.google.gson.Gson;
import com.laboratoriodos.kevin.laboratorio2_ed2.clases.Adapter;
import com.laboratoriodos.kevin.laboratorio2_ed2.clases.Archivo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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

        adapterMisCompresiones.setOnClickListener(view ->        {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Esta seguro en descomprimir el archivo?");
            dlgAlert.setTitle("Descomprimir archivo");
            dlgAlert.setPositiveButton("SI", (dialogInterface, i) -> {
                        Archivo archivoSeleccionado = listaArchivos.get(RecyclerViewMisCompresiones.getChildAdapterPosition(view));
                        String binario = lecturaDescompresion(archivoSeleccionado.getRuta() + "/" + archivoSeleccionado.getNombre());
                        String descifrado = FilesActivity.cifrado.decifrar(archivoSeleccionado.getArbol(), binario);
                        StorageChooser chooser = new StorageChooser.Builder()
                                .withActivity(ListFilesActivity.this)
                                .withFragmentManager(getFragmentManager())
                                .withMemoryBar(true)
                                .allowCustomPath(true)
                                .setType(StorageChooser.DIRECTORY_CHOOSER)
                                .build();
                        chooser.show();
                        Toast.makeText(getApplicationContext(),"Seleccione una ruta",Toast.LENGTH_SHORT).show();
                        chooser.setOnSelectListener(path -> {
                            escrituraDescompresion(path, descifrado);
                        });
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


    private String lecturaDescompresion(String path){
        StringBuilder texto = new StringBuilder();
        try {
            InputStream f = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(f));
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                texto.append(" " + inputLine);
            }
            br.close();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "ERROR AL LEER EL ARCHIVO", Toast.LENGTH_SHORT).show();
        }
        return texto.toString();
    }

    private void escrituraDescompresion(String path,String decodificacion){
        try{
            Random r = new Random();
            int h = r.nextInt(100);
            String name = "dataDescomprimida"+h+".txt";
            File f = new File(path, name);
            if(!f.exists()){
            f.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(f);
            OutputStreamWriter file = new OutputStreamWriter(fos);
            file.append(decodificacion);
            file.flush();
            file.close();
            Toast.makeText(getApplicationContext(),"Descomprimido en " + f.getPath(),Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Log.i("ARCHIVO", e.toString());
        } catch (IOException e) {
            Log.i("ARCHIVO", e.toString());
        }
    }
}
