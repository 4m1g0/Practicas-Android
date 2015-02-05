package es.udc.psi14.blanco_novoa.blanco_novoalab06;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;


public class DataTestActivity extends Activity {
    EditText et_text;
    Button but_hello;
    Button but_write_int, but_read_int, but_read_ext, but_write_ext, but_read_pref, but_write_pref;
    SharedPreferences pref;
    TextView tv_text;
    Spinner spinner;
    String FILENAME = "Patata.txt";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals("Settings")) {
            startActivity(new Intent(this, OptionsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.data_test, menu);
        return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_test);

        et_text = (EditText) findViewById(R.id.et_text);
        but_hello = (Button) findViewById(R.id.but_hello);

        PreferenceManager.setDefaultValues(getBaseContext(),R.xml.options,false);
        pref = PreferenceManager.getDefaultSharedPreferences(this);

        String myData = pref.getString("txt", "");
        tv_text = (TextView)findViewById(R.id.tv_text);
        tv_text.setText(myData);

        but_hello.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!et_text.getText().equals("")) {
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("txt", et_text.getText().toString());
                    editor.commit();
                    et_text.setText("");
                }
            }
        });
        but_write_int = (Button) findViewById(R.id.but_write_int);

        but_write_int.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!et_text.getText().equals("")) {
                    try {

                        FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_APPEND);
                        String txt = et_text.getText().toString() + "\n";
                        et_text.setText("");
                        fos.write(txt.getBytes());
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        });

        final ArrayList<String> spinnerArray = new ArrayList<String>();
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(spinnerArrayAdapter);

        but_read_int = (Button) findViewById(R.id.but_read_int);

        but_read_int.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                try
                {
                    String line = "";
                    FileInputStream fis = openFileInput(FILENAME);
                    InputStreamReader in = new InputStreamReader(fis);
                    BufferedReader br = new BufferedReader(in);
                    spinnerArray.clear();
                    while ((line = br.readLine()) != null) {
                        spinnerArray.add(line);
                    }
                }
                catch
                        (FileNotFoundException e) {
                    e.printStackTrace();
                }
                catch
                        (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        but_write_ext = (Button) findViewById(R.id.but_write_ext);

        but_write_ext.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                String state = Environment.getExternalStorageState();
                if (Environment.MEDIA_MOUNTED.equals(state)) {
                    try
                    {
                        File file = new File(getExternalFilesDir(null), FILENAME);
                        OutputStream os = new FileOutputStream(file);
                        String txt = et_text.getText().toString() + "\n";
                        et_text.setText("");
                        os.write(txt.getBytes());
                        os.close();
                    }
                    catch
                            (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    catch
                            (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        but_read_ext = (Button) findViewById(R.id.but_read_ext);

        but_read_ext.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                String state = Environment.getExternalStorageState();
                if (Environment.MEDIA_MOUNTED.equals(state) || (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))) {
                    try
                    {
                        String textExternalFile = "";
                        String line = "";
                        File file = new File(getExternalFilesDir(null), FILENAME);
                        FileInputStream fis = new FileInputStream(file);
                        InputStreamReader in = new InputStreamReader(fis);
                        BufferedReader br = new BufferedReader(in);
                        spinnerArray.clear();
                        while ((line = br.readLine()) != null) {
                            spinnerArray.add(line);
                        }
                    }
                    catch
                            (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    catch
                            (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        but_write_pref = (Button) findViewById(R.id.but_write_pref);

        but_write_pref.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (pref.getString("storage_type", "").equals("external")){
                    String state = Environment.getExternalStorageState();
                    if (Environment.MEDIA_MOUNTED.equals(state)) {
                        try
                        {
                            File file = new File(getExternalFilesDir(null), pref.getString("Filename", ""));
                            OutputStream os = new FileOutputStream(file);
                            String txt = et_text.getText().toString() + "\n";
                            et_text.setText("");
                            os.write(txt.getBytes());
                            os.close();
                        }
                        catch
                                (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        catch
                                (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    if (!et_text.getText().equals("")) {
                        try {

                            FileOutputStream fos = openFileOutput(pref.getString("Filename", ""), Context.MODE_APPEND);
                            String txt = et_text.getText().toString() + "\n";
                            et_text.setText("");
                            fos.write(txt.getBytes());
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        });

        but_read_pref = (Button) findViewById(R.id.but_read_pref);

        but_read_pref.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (pref.getString("storage_type", "").equals("external")) {
                    String state = Environment.getExternalStorageState();
                    if (Environment.MEDIA_MOUNTED.equals(state) || (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))) {
                        try {
                            String textExternalFile = "";
                            String line = "";
                            File file = new File(getExternalFilesDir(null), pref.getString("Filename", ""));
                            FileInputStream fis = new FileInputStream(file);
                            InputStreamReader in = new InputStreamReader(fis);
                            BufferedReader br = new BufferedReader(in);
                            spinnerArray.clear();
                            while ((line = br.readLine()) != null) {
                                spinnerArray.add(line);
                            }
                        } catch
                                (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch
                                (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    try
                    {
                        String line = "";
                        FileInputStream fis = openFileInput(pref.getString("Filename", ""));
                        InputStreamReader in = new InputStreamReader(fis);
                        BufferedReader br = new BufferedReader(in);
                        spinnerArray.clear();
                        while ((line = br.readLine()) != null) {
                            spinnerArray.add(line);
                        }
                    }
                    catch
                            (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    catch
                            (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public CharSequence onCreateDescription() {
        return super.onCreateDescription();
    }
}
