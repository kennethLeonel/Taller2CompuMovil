package com.example.taller2compumovil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.provider.ContactsContract;
import android.content.pm.PackageManager;
import android.widget.Toast;

public class Contactos extends AppCompatActivity {
    public  static  final int ID_CONTACTO_PERMISO = 5;
    String permisoContactos = Manifest.permission.READ_CONTACTS;

    String[] mProjection;
    Cursor mCursor;
    AdaptadorDeContactos mContactsAdapter;
    ListView contactosListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);
        contactosListView = findViewById(R.id.lista);
        mProjection = new  String[]{
            ContactsContract.Profile._ID,
            ContactsContract.Profile.DISPLAY_NAME,
        };
        //El cursor esta en null hasta que el usuario acepte el permiso.
        mContactsAdapter = new AdaptadorDeContactos(this, null, 0);
        contactosListView.setAdapter(mContactsAdapter);

        requestPermission(this,
                permisoContactos,
                "Es necesario los contactos para obtenerlos",
                ID_CONTACTO_PERMISO);
        initView();

    }
    private void initView() {
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED){
            mCursor = getContentResolver().query(
                    ContactsContract.Contacts.CONTENT_URI,
                    mProjection,
                    null,
                    null,
                    null);
            mContactsAdapter.changeCursor(mCursor);
        }
    }
    private void requestPermission(Activity context, String permission, String justification, int id){
        if (ContextCompat.checkSelfPermission(context, permission)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                    Manifest.permission.READ_CONTACTS)) {
                Toast.makeText(context, justification, Toast.LENGTH_SHORT).show();
            }
            // request the permission.
            ActivityCompat.requestPermissions(context, new String[]{permission}, id);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == ID_CONTACTO_PERMISO){
            initView();
        }
    }
}