package es.udc.psi14.blanco_novoa.blanco_novoalab07;

import android.app.Activity;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class NuevaNota extends Activity {

    Button but_add, but_mod;
    int id = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_nota);

        but_add = (Button) findViewById(R.id.but_add);
        but_mod = (Button) findViewById(R.id.but_mod);


        but_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Notas notas = new Notas();

                    NotasDataBaseHelper dbh = new NotasDataBaseHelper(NuevaNota.this);
                    notas.setNombre(((EditText) findViewById(R.id.nombre)).getText().toString());
                    notas.setApellido(((EditText) findViewById(R.id.apellido)).getText().toString());
                    notas.setMateria(((EditText) findViewById(R.id.materia)).getText().toString());
                    notas.setMencion(((EditText) findViewById(R.id.mencion)).getText().toString());
                    notas.setNota(Integer.parseInt(((EditText) findViewById(R.id.nota)).getText().toString()));

                    long code = dbh.insertNota(notas);
                    if (code != -1)
                        Toast.makeText(NuevaNota.this, "Nuevo dato", Toast.LENGTH_LONG).show();

                    finish();
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Error: Nota debe ser un entero)",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        but_mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Notas notas = new Notas();

                    NotasDataBaseHelper dbh = new NotasDataBaseHelper(NuevaNota.this);
                    notas.setNombre(((EditText) findViewById(R.id.nombre)).getText().toString());
                    notas.setApellido(((EditText)findViewById(R.id.apellido)).getText().toString());
                    notas.setMateria(((EditText) findViewById(R.id.materia)).getText().toString());
                    notas.setMencion(((EditText) findViewById(R.id.mencion)).getText().toString());
                    notas.setNota(Integer.valueOf(((EditText) findViewById(R.id.nota)).getText().toString()));

                    notas.setId(id);
                    dbh.updateNota(notas);

                    finish();
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Error: Nota debe ser un entero)",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        Bundle extra = getIntent().getExtras(); // check if not null
        if (extra != null) {
            but_mod.setVisibility(View.VISIBLE);
            NotasDataBaseHelper dbNotas = new NotasDataBaseHelper(this);
            id = extra.getInt(NotasDataBaseHelper.COL_ID,0);
            Cursor cursor = dbNotas.getNotaId(id);
            cursor.moveToFirst();
            Log.d("Lab07", "id " + id + " count " + cursor.getCount() + "");

            ((EditText)findViewById(R.id.nombre)).setText(cursor.getString(cursor.getColumnIndex(NotasDataBaseHelper.COL_NOMBRE)));
            ((EditText)findViewById(R.id.apellido)).setText(cursor.getString(cursor.getColumnIndex(NotasDataBaseHelper.COL_APELLIDO)));
            ((EditText)findViewById(R.id.materia)).setText(cursor.getString(cursor.getColumnIndex(NotasDataBaseHelper.COL_MATERIA)));
            ((EditText)findViewById(R.id.mencion)).setText(cursor.getString(cursor.getColumnIndex(NotasDataBaseHelper.COL_MENCION)));
            ((EditText)findViewById(R.id.nota)).setText(cursor.getString(cursor.getColumnIndex(NotasDataBaseHelper.COL_NOTA)));

        }else {
            but_mod.setVisibility(View.INVISIBLE);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nueva_nota, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
