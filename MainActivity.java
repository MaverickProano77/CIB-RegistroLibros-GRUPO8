package com.example.grupo8app;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity  extends AppCompatActivity {

    EditText matricula, isbn, nombreLibro, nombreAutor, apellidoAutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        matricula = (EditText) findViewById(R.id.edtMatricula);
        isbn = (EditText) findViewById(R.id.edtISBN);
        nombreLibro = (EditText) findViewById(R.id.edtLibro);
        nombreAutor = (EditText) findViewById(R.id.edtNombreAutor);
        apellidoAutor = (EditText) findViewById(R.id.edtApellidoAutor);
    }
        public void registro (View v){
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                    "administracion", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            String matriculaText = matricula.getText().toString();
            String isbnText = isbn.getText().toString();
            String nLibroText = nombreLibro.getText().toString();
            String nAutorText = nombreAutor.getText().toString();
            String aAutorText = apellidoAutor.getText().toString();
            if(!matriculaText.isEmpty()||!isbnText.isEmpty()||!nLibroText.isEmpty()
            ||!nAutorText.isEmpty()||!aAutorText.isEmpty()){
                bd.execSQL("insert into tablaLibro (id_isbn, matricula, nombreLibro, nombreAutor, apellidoAutor)"+
                        "values ("+isbnText+",'"+matriculaText+"','"+nLibroText+"','"+nAutorText+"','"+aAutorText+"')");
                bd.close();
                matricula.setText("");
                isbn.setText("");
                nombreLibro.setText("");
                nombreAutor.setText("");
                apellidoAutor.setText("");
                Toast.makeText(this, "Datos del libro ingresados",
                        Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Llene TODOS los campos",
                        Toast.LENGTH_SHORT).show();
            }
        }
        public void consultarLibro(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getReadableDatabase();
        String isbn_text = isbn.getText().toString();
        if(!isbn_text.isEmpty()){
            Cursor fila = bd.rawQuery("select  matricula,nombreLibro,nombreAutor,apellidoAutor from tablaLibro where "+
                    "id_isbn=" +isbn_text,null);
            if(fila.moveToFirst()){
                matricula.setText(fila.getString(0));
                nombreLibro.setText(fila.getString(1));
                nombreAutor.setText(fila.getString(2));
                apellidoAutor.setText(fila.getString(3));
                Toast.makeText(this,"Consulta exitosa",
                        Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(this,"No existe el libro con dicho ISBN",
                        Toast.LENGTH_SHORT).show();
            } bd.close();
        } else{
            Toast.makeText(this,"Ingrese el ISBN del LIBRO",
                    Toast.LENGTH_SHORT).show();
        }

        }
    public void modificarInformacion (View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String matriculaText = matricula.getText().toString();
        String isbnText = isbn.getText().toString();
        String nLibroText = nombreLibro.getText().toString();
        String nAutorText = nombreAutor.getText().toString();
        String aAutorText = apellidoAutor.getText().toString();

        if(!isbnText.isEmpty()){
            if(matriculaText.isEmpty() || nLibroText.isEmpty() || nAutorText.isEmpty() || aAutorText.isEmpty()){
                Toast.makeText(this, "Ingrese TODOS los datos", Toast.LENGTH_SHORT).show();
            }

            bd.execSQL("update tablaLibro set id_isbn="+isbnText+",matricula='"+
                    matriculaText+"',nombreLibro='"+nLibroText+"',nombreAutor='"+nAutorText+"',apellidoAutor='"
                    +aAutorText+"' where id_isbn="+isbnText);
            matricula.setText("");
            isbn.setText("");
            nombreAutor.setText("");
            nombreLibro.setText("");
            apellidoAutor.setText("");
            bd.close();
            Toast.makeText(this, "Se modificaron los datos", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"Ingrese un ISBN", Toast.LENGTH_SHORT).show();
        }
    }
    public void eliminarLibro(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String isbn_Text = isbn.getText().toString();

        if(!isbn_Text.isEmpty()){

            bd.execSQL("delete from tablaLibro where id_isbn=" +isbn_Text);
            bd.close();
            matricula.setText("");
            isbn.setText("");
            nombreLibro.setText("");
            nombreAutor.setText("");
            apellidoAutor.setText("");
            Toast.makeText(this,"Libro eliminado de la base de datos",
                    Toast.LENGTH_SHORT).show();
        }else{

            Toast.makeText(this,"Ingrese un ISBN",
                    Toast.LENGTH_SHORT).show();
        }
    }
    }