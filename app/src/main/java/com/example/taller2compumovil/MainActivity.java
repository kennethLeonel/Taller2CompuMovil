package com.example.taller2compumovil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void irACamara(View view) {
        Intent intent = new Intent(this, Camara.class);
        startActivity(intent);
    }

    public void irAContactos(View view) {
        Intent intent = new Intent(this, Contactos.class);
        startActivity(intent);
    }

    public void irAMapa(View view) {
        Intent intent = new Intent(this, Mapa.class);
        startActivity(intent);
    }
}