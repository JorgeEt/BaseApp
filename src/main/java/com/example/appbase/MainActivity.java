package com.example.appbase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etId, etNombre, etTelefono;

    Button btnConsultar, btnAlta, btnEditar, btnBorrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etId = findViewById(R.id.etidUsuario);
        etNombre = findViewById(R.id.etNombre);
        etTelefono = findViewById(R.id.etTelefono);

       btnConsultar = findViewById(R.id.btnConsultar);
       btnAlta = findViewById(R.id.btnAlta);
       btnEditar = findViewById(R.id.btnEditar);
       btnBorrar = findViewById(R.id.btnBorrar);

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consulta();
            }
        });

        btnAlta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               alta();
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar();
            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrar();
            }
        });

    }

    public void consulta(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1 );
        SQLiteDatabase db = admin.getWritableDatabase();

        String idUsuario = etId.getText().toString();

        Cursor fila = db.rawQuery("SELECT nombre, telefono FROM usuarios WHERE idUsuario="+idUsuario, null);

        if(fila.moveToFirst()){
            etNombre.setText(fila.getString(0));
            etTelefono.setText(fila.getString(1));
        }else{
            Toast.makeText(this, "No existe ningun usuarios con ese ID", Toast.LENGTH_LONG).show();
        }


    }

    public void alta(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1 );
        SQLiteDatabase db = admin.getWritableDatabase();

        String idUsuario = etId.getText().toString();
        String nombre = etNombre.getText().toString();
        String telefono = etTelefono.getText().toString();

        ContentValues registro = new ContentValues();

        registro.put("idUsuario", idUsuario);
        registro.put("nombre", nombre);
        registro.put("telefono", telefono);

        db.insert("usuarios", null, registro);

        db.close();

        etId.setText("");
        etNombre.setText("");
        etTelefono.setText("");

        Toast.makeText(this, "datos del ususario registrados correctamente", Toast.LENGTH_LONG).show();
    }

    public void borrar(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String idUsuario = etId.getText().toString();

        int cant = db.delete("usuarios", "idUsuario="+idUsuario, null);

        db.close();

        etId.setText("");
        etNombre.setText("");
        etTelefono.setText("");

        if(cant == 1){
            Toast.makeText(this, "usuario eliminado", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "No existe", Toast.LENGTH_LONG).show();
        }
    }

    public void editar(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String idUsuario = etId.getText().toString();
        String nombre = etNombre.getText().toString();
        String telefono = etTelefono.getText().toString();

        ContentValues registro = new ContentValues();

        registro.put("nombre", nombre);
        registro.put("telefono", telefono);

        int cant = db.update("usuarios", registro, "idUsuario="+idUsuario, null);

        db.close();

        etId.setText("");
        etNombre.setText("");
        etTelefono.setText("");

        if(cant == 1){
            Toast.makeText(this, "Usuario modificado exitosamente", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "No existe el usuario", Toast.LENGTH_LONG).show();
        }
    }
}