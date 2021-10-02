package com.example.taller2compumovil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.InputStream;

import java.io.FileNotFoundException;


public class Camara extends AppCompatActivity {

    //Definir el id del permiso
    private final int ID_CAMARA_PERMISO = 2;
    private final int ID_GALERIA_PERMISO = 3;
    private static final int SELECCIONAR_IMAGEN_PERMISO = 0;
    private static final int PERMISO_CAMARA = 1;
    String camaraPermiso = Manifest.permission.CAMERA;
    String galeriaPermiso = Manifest.permission.READ_EXTERNAL_STORAGE;
    //Para mostrar imagen de la camara
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);
        imageView = findViewById(R.id.imageView2);
    }
    public void prenderCamnara(View view) {
        if (ActivityCompat.checkSelfPermission(this, camaraPermiso)!= PackageManager.PERMISSION_GRANTED){
            requestPermission(this,camaraPermiso,"",ID_CAMARA_PERMISO);
        }else {
            openCamera();
        }

    }
    private void openCamera() {
        if(ActivityCompat.checkSelfPermission(this,camaraPermiso)==PackageManager.PERMISSION_GRANTED){
            Log.i("Permiso_APP","Dentro del método imagenCamara");
            Intent tomarFotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                startActivityForResult(tomarFotoIntent,PERMISO_CAMARA);
            } catch (ActivityNotFoundException exception){
                Log.e("Permiso_APP", exception.getMessage());
            }
        }
    }
    public void irAGaleria(View view) {
        if(ActivityCompat.checkSelfPermission(this,galeriaPermiso)!= PackageManager.PERMISSION_GRANTED){
            requestPermission(this,galeriaPermiso,"",ID_GALERIA_PERMISO);
        }else{
            openGaleria();
        }
    }
    private void openGaleria (){
        if(ActivityCompat.checkSelfPermission(this,galeriaPermiso)==PackageManager.PERMISSION_GRANTED){
            Intent seleccionarImagen = new Intent(Intent.ACTION_PICK);
            seleccionarImagen.setType("image/*");
            startActivityForResult(seleccionarImagen, SELECCIONAR_IMAGEN_PERMISO);

        }
    }

    private void requestPermission(Activity contexto, String permiso,String justificacion,int id){
        if(ActivityCompat.checkSelfPermission(contexto,permiso)!=PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(contexto,permiso)){
                Toast.makeText(contexto,justificacion,Toast.LENGTH_SHORT).show();
            }
            //Pedir permiso
            ActivityCompat.requestPermissions(contexto,new String[]{permiso},id);
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == ID_CAMARA_PERMISO){
            openCamera();
        } else if(requestCode == ID_GALERIA_PERMISO){
            openGaleria();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case SELECCIONAR_IMAGEN_PERMISO:
                if (resultCode == RESULT_OK){
                    try {
                        final Uri imageUri = data.getData();
                        final InputStream imageStream =getContentResolver().openInputStream(imageUri);
                        final Bitmap imagenSeleccionada = BitmapFactory.decodeStream(imageStream);
                        imageView.setImageBitmap(imagenSeleccionada);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case PERMISO_CAMARA:
                if(resultCode == RESULT_OK){
                    Bundle extras =data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    imageView.setImageBitmap(imageBitmap);
                }
        }
    }

}