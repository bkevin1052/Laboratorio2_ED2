package com.laboratoriodos.kevin.laboratorio2_ed2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.laboratoriodos.kevin.laboratorio2_ed2.clases.Archivo;
import com.laboratoriodos.kevin.laboratorio2_ed2.clases.PreferenceManager;

public class MainActivity extends AppCompatActivity {

    Button btnHuffman, btnLZW,btnMisCompresiones;

    private PreferenceManager configuracion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnHuffman = (Button)findViewById(R.id.btnHuffman);
        btnLZW = (Button)findViewById(R.id.btnLzw);
        btnMisCompresiones = (Button)findViewById(R.id.btnMisCompresiones);
        configuracion = new PreferenceManager(getApplicationContext());

        btnHuffman.setOnClickListener(view ->{
            FilesActivity.seleccion = 1;
            startActivity(new Intent(getApplicationContext(), FilesActivity.class));
        });

        btnLZW.setOnClickListener(view ->{
            FilesActivity.seleccion = 2;
            startActivity(new Intent(getApplicationContext(),FilesActivity.class));
        });

        btnMisCompresiones.setOnClickListener(view ->{

            if(ListFilesActivity.listaArchivos.size() != 0){
                guardarDatos();
            }
            else{
                agregarDatos();
            }
            startActivity(new Intent(getApplicationContext(),ListFilesActivity.class));
        });
    }

    private void guardarDatos(){

        if(ListFilesActivity.listaArchivos != null) {
            for (Archivo archivo : ListFilesActivity.listaArchivos) {
                configuracion.setNombre(archivo.getNombre());
                configuracion.setRuta(archivo.getRuta());
                configuracion.setRazonCompresion(String.valueOf(archivo.getRazonCompresion()));
                configuracion.setFactorCompresion(String.valueOf(archivo.getFactorCompresion()));
                configuracion.setPorcentajeReduccion(String.valueOf(archivo.getPorcentajeReduccion()));
                configuracion.setAlgoritmoCompresion(archivo.getNombre());
                configuracion.setImagen(String.valueOf(archivo.getImagen()));
            }
        }
    }

    private void agregarDatos(){
        ListFilesActivity.listaArchivos.add(new Archivo(configuracion.getNombre(),
                configuracion.getRuta(),
                Double.parseDouble(configuracion.getRazonCompresion()),
                Double.parseDouble(configuracion.getFactorCompresion()),
                Double.parseDouble(configuracion.getPorcentajeReduccion()),
                configuracion.getAlgoritmoCompresion(),
                Integer.parseInt(configuracion.getImagen())));
    }
}
