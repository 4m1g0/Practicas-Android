package es.udc.psi14.blanco_novoa.blanco_novoalab07;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;


public class DbTestActivity extends Activity {

    NotasDataBaseHelper dbNotas;
    ListView lv;
    String orderby = "nombre";

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            dbNotas.deleteNota((int) info.id);
            actualizar_lista(dbNotas.getNotas()); // actualiza la listView
        } else if (item.getItemId() == R.id.edit) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Intent intent = new Intent(this, NuevaNota.class);
            intent.putExtra(NotasDataBaseHelper.COL_ID, (int) info.id);
            startActivity(intent);
        }

        return super.onContextItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_test);

        dbNotas = new NotasDataBaseHelper(this);
        lv = (ListView) findViewById(R.id.listView);
        registerForContextMenu(lv);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.db_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.menu_add) {
            startActivity(new Intent(this, NuevaNota.class));
            return true;
        }
        if (id == R.id.menu_update) {
            actualizar_lista(dbNotas.getNotasOrdered(orderby));
            return true;
        }
        if (id == R.id.ordenar) {
            String[] s = dbNotas.getNombreTodasCol();
            final ArrayAdapter<String> adp = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, s);

            final Spinner sp = new Spinner(this);
            sp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            sp.setAdapter(adp);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(sp);
            builder.setTitle("Ordenar por:");
            builder.setPositiveButton("ASC", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            orderby = sp.getSelectedItem().toString();
                            actualizar_lista(dbNotas.getNotasOrdered(orderby + " ASC"));
                        }
                    });
            builder.setNeutralButton("DESC",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            orderby = sp.getSelectedItem().toString();
                            actualizar_lista(dbNotas.getNotasOrdered(orderby + " DESC"));
                        }
                    });

            builder.setNegativeButton(android.R.string.no,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });

             builder.create().show();
            return true;

        }
        if (id == R.id.filtrar) {
            String[] s = dbNotas.getNombreTodasCol();
            final ArrayAdapter<String> adp = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, s);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = getLayoutInflater().inflate(R.layout.filtro,null);

            final Spinner sp = (Spinner)view.findViewById(R.id.filter_spiner);
            final EditText et = (EditText) view.findViewById(R.id.texto_filtro);
            sp.setAdapter(adp);
            builder.setView(view);
            builder.setTitle("Filtrar por:");
            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    actualizar_lista(dbNotas.getNotas2(sp.getSelectedItem().toString(), et.getText().toString()));
                }
            });
            builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.create().show();
            return true;
        }
        if (id == R.id.cpTest) {
            startActivity(new Intent(this, cpTestActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void actualizar_lista (Cursor cursor) {
        SimpleCursorAdapter adapter2 = new SimpleCursorAdapter(this,
                R.layout.list_row, cursor, new String[]
                {NotasDataBaseHelper.COL_NOMBRE, NotasDataBaseHelper.COL_APELLIDO, NotasDataBaseHelper.COL_MATERIA, NotasDataBaseHelper.COL_MENCION, NotasDataBaseHelper.COL_NOTA},
                new int[] {R.id.textView, R.id.textView2, R.id.textView3, R.id.textView4, R.id.textView6}, 0);
        lv.setAdapter(adapter2);
    }

}
