package com.laboratoriodos.kevin.laboratorio2_ed2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    Button btnHuffman, btnLZW,btnMisCompresiones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnHuffman = (Button)findViewById(R.id.btnHuffman);
        btnLZW = (Button)findViewById(R.id.btnLzw);
        btnMisCompresiones = (Button)findViewById(R.id.btnMisCompresiones);

        btnHuffman.setOnClickListener(view ->{
            startActivity(new Intent(getApplicationContext(), FilesActivity.class));
        });

        btnLZW.setOnClickListener(view ->{
            startActivity(new Intent(getApplicationContext(),FilesActivity.class));
        });

        btnMisCompresiones.setOnClickListener(view ->{
            startActivity(new Intent(getApplicationContext(),ListFilesActivity.class));
        });
    }
}
