package es.udc.psi14.blanco_novoa.blanco_novoalab07;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class cpTestActivity extends Activity {

    EditText word;
    Button but_search, but_add, but_eliminar, but_editar;
    ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cp_test);

        word = (EditText) findViewById(R.id.et_word);
        but_search = (Button) findViewById(R.id.but_searh);
        but_eliminar = (Button) findViewById(R.id.but_eliminar);
        but_editar = (Button) findViewById(R.id.but_editar);
        but_add = (Button) findViewById(R.id.but_add);

        lv = (ListView) findViewById(R.id.lv);
        registerForContextMenu(lv);

        but_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (word.getText() != null && !word.getText().equals("")) {
                    refrescar_lista(buscarPalabra(word.getText().toString()));
                }

            }
        });

        but_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (word.getText() != null && !word.getText().equals("")) {
                    ContentValues mNewValues = new ContentValues();
                    mNewValues.put(UserDictionary.Words.WORD, word.getText().toString());
                    mNewValues.put(UserDictionary.Words.LOCALE, "es");
                    mNewValues.put(UserDictionary.Words.APP_ID, getPackageName());
                    mNewValues.put(UserDictionary.Words.FREQUENCY, "1");
                    Uri mNewUri = getContentResolver().insert(
                            UserDictionary.Words.CONTENT_URI, // the user dictionary content URI
                            mNewValues);
                    refrescar_lista(buscarPalabra(word.getText().toString()));
                }

            }
        });

        but_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (word.getText() != null && !word.getText().equals("")) {
                    String select = UserDictionary.Words.WORD + " LIKE ? ";
                    String[ ] selectArgs = {"%" + word.getText().toString() + "%"};
                    int numUpdateValues = getContentResolver().delete(UserDictionary.Words.CONTENT_URI, select, selectArgs);
                    refrescar_lista(buscarPalabra(word.getText().toString()));
                }

            }
        });


    }

    Cursor buscarPalabra(String palabra) {
        String[ ] projection = {UserDictionary.Words._ID,
                UserDictionary.Words.WORD,
                UserDictionary.Words.LOCALE};
        String sortOrder = UserDictionary.Words.WORD + " ASC";
        String selectClause = UserDictionary.Words.WORD + " LIKE ? ";
        String [ ] selectArgs = {"%" + palabra + "%"};

        Cursor cursor = getContentResolver().query(
                UserDictionary.Words.CONTENT_URI,
                projection,
                selectClause,
                selectArgs,
                sortOrder);

        return cursor;
    }

    private void refrescar_lista (Cursor cursor) {
        SimpleCursorAdapter adapter2 = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2, cursor, new String[]
                {UserDictionary.Words.WORD, UserDictionary.Words.LOCALE},
                new int[] {android.R.id.text1, android.R.id.text2}, 0);
        lv.setAdapter(adapter2);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete) {

            String mSelectionClause = UserDictionary.Words._ID + " = ?";
            String[] mSelectionArgs = {String.valueOf(((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).id)};
            int mRowsDeleted = getContentResolver().delete(
                    UserDictionary.Words.CONTENT_URI, // user dictionary content URI
                    mSelectionClause, // the column to select on
                    mSelectionArgs); // the value to compare to
            refrescar_lista(buscarPalabra(word.getText().toString()));
        } else if (item.getItemId() == R.id.edit) {

        }

        return super.onContextItemSelected(item);
    }
}
