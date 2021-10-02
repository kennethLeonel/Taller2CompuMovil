package com.example.taller2compumovil;



import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class AdaptadorDeContactos extends CursorAdapter {
    private final static int CONTACT_ID_INDEX = 0;
    private final static int CONTACT_NAME_INDEX = 1;


    public AdaptadorDeContactos(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(
                R.layout.activity_adaptador_de_contactos,
                parent,
                false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        int idCont = cursor.getInt(CONTACT_ID_INDEX);
        String nameCont = cursor.getString(CONTACT_NAME_INDEX);
        TextView idContactText = view.findViewById(R.id.textView4);
        TextView nameContactText = view.findViewById(R.id.textView5);
        idContactText.setText(String.valueOf(idCont));
        nameContactText.setText(nameCont);
    }
}