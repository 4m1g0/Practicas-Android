package es.udc.psi14.blanco_novoa.blanco_novoalab03;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewManager;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


public class EventsActiv extends Activity implements OnClickListener, OnCheckedChangeListener, OnItemSelectedListener, OnSeekBarChangeListener, View.OnLongClickListener {
    ToggleButton togbut1;
    ImageButton imgbut1;
    Button simpbut1;
    TextView tv1;
    CheckBox check1;
    CheckBox check2;
    CheckBox check3;
    TextView text_color;
    RadioButton radio_red;
    RadioButton radio_blue;
    RadioButton radio_green;
    AutoCompleteTextView acTextView;
    Spinner spinnerCars;
    SeekBar seekbar;
    Button but_seek;
    TableRow table_model;
    Spinner spinnerModel;
    Button but_add, but_clear;
    LinearLayout lastLayout, main_layout;
    EditText edit_number;

    private static final String TAG = "EventsActiv";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        togbut1 = (ToggleButton) findViewById(R.id.togbut1);
        imgbut1 = (ImageButton) findViewById(R.id.imgbut1);
        simpbut1 = (Button) findViewById(R.id.simpbut1);
        tv1 = (TextView) findViewById(R.id.tv1);
        togbut1.setOnCheckedChangeListener(this);
        imgbut1.setOnClickListener(this);
        simpbut1.setOnClickListener(this);


        check1 = (CheckBox) findViewById(R.id.check1);
        check2 = (CheckBox) findViewById(R.id.check2);
        check3 = (CheckBox) findViewById(R.id.check3);
        check1.setOnCheckedChangeListener(this);
        check2.setOnCheckedChangeListener(this);
        check3.setOnCheckedChangeListener(this);


        text_color = (TextView) findViewById(R.id.text_color);
        radio_red = (RadioButton) findViewById(R.id.radio_red);
        radio_blue = (RadioButton) findViewById(R.id.radio_blue);
        radio_green = (RadioButton) findViewById(R.id.radio_green);

        radio_red.setOnClickListener(this);
        radio_blue.setOnClickListener(this);
        radio_green.setOnClickListener(this);

        acTextView = (AutoCompleteTextView) findViewById(R.id.acTextView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, NAMES);

        acTextView.setAdapter(adapter);

        spinnerCars = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.cars, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCars.setAdapter(adapter2);

        spinnerCars.setOnItemSelectedListener(this);

        seekbar = (SeekBar) findViewById(R.id.seekBar);
        but_seek = (Button) findViewById(R.id.but_seek);
        but_seek.setOnClickListener(this);
        but_seek.setOnLongClickListener(this);
        seekbar.setOnSeekBarChangeListener(this);

        table_model = (TableRow) findViewById(R.id.table_model);
        table_model.setVisibility(View.INVISIBLE);

        spinnerModel = (Spinner) findViewById(R.id.spinner_model);
        spinnerModel.setOnItemSelectedListener(this);

        but_add = (Button) findViewById(R.id.but_add);
        but_clear = (Button) findViewById(R.id.but_clear);
        but_add.setOnClickListener(this);
        but_clear.setOnClickListener(this);

        main_layout = (LinearLayout) findViewById(R.id.main_layout);

        edit_number = (EditText) findViewById(R.id.edit_number);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private static final String[] NAMES = new String[] {
            "Carlos", "Oscar", "Rodolfo", "Ana", "Bea", "Dionisio", "Carla", "Carlota", "Oswaldo"
    };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.events, menu);
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

    @Override
    public void onClick(View v) {
        if (v == simpbut1) {
            tv1.setText("simple button");
        }
        if (v == imgbut1) {
            tv1.setText("image button");
        }

        if (v == radio_red) {
            text_color.setBackgroundColor(Color.RED);
        }
        if (v == radio_blue) {
            text_color.setBackgroundColor(Color.BLUE);
        }
        if (v == radio_green) {
            text_color.setBackgroundColor(Color.GREEN);
        }

        if (v == but_seek) {
            seekbar.setProgress(50);
        }

        if (v == but_add) {
            but_add.setEnabled(false);
            but_clear.setEnabled(true);
            lastLayout = new LinearLayout(this);
            lastLayout.setOrientation(LinearLayout.VERTICAL);
            main_layout.addView(lastLayout);

            int fin = Integer.parseInt(edit_number.getText().toString());
            for (int i=0; i<fin; i++) {
                LinearLayout ll = new LinearLayout(this);
                ll.setOrientation(LinearLayout.HORIZONTAL);
                TextView tw = new TextView(this);
                tw.setText(i + "");
                ll.addView(tw);
                Button b = new Button(this);
                b.setText("numero");
                final int u = i;
                b.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button b = (Button) v;
                        Toast.makeText(EventsActiv.this, u +"", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Pulsado boton: "+u);
                    }
                });
                ll.addView(b);
                lastLayout.addView(ll);
            }
        }

        if (v == but_clear) {
            but_add.setEnabled(true);
            but_clear.setEnabled(false);
            ((ViewManager)lastLayout.getParent()).removeView(lastLayout);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == togbut1) {
            tv1.setText("Toggle button");
        }
        if (buttonView == check1) {
            togbut1.performClick();
            String mytext = check1.getText().toString();
            Toast.makeText(this, mytext, Toast.LENGTH_SHORT).show();
        }
        if (buttonView == check2) {
            if (isChecked)
                acTextView.setEnabled(false);
            else
                acTextView.setEnabled(true);

            String mytext = check2.getText().toString();
            Toast.makeText(this, mytext, Toast.LENGTH_SHORT).show();
        }
        if (buttonView == check3) {
            if (isChecked)
                acTextView.setSingleLine(true);
            else
                acTextView.setSingleLine(false);
            String mytext = check3.getText().toString();
            Toast.makeText(this, mytext, Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selected = parent.getItemAtPosition(position).toString();
        if (selected.equals("ninguno")) {

            table_model.setVisibility(View.INVISIBLE);
        }
        else {
            ArrayAdapter<CharSequence> adapter;
            if (selected.equals("ford")) {
                adapter= ArrayAdapter.createFromResource(this,R.array.cars_ford, android.R.layout.simple_spinner_item);
            } else if (selected.equals("opel")){
                adapter= ArrayAdapter.createFromResource(this,R.array.cars_opel, android.R.layout.simple_spinner_item);
            } else {
                return;
            }

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerModel.setAdapter(adapter);
            table_model.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        but_seek.setText(progress + "");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    @Override
    public boolean onLongClick(View v) {
        if (v == but_seek) {
            //InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            //imm.showSoftInput(text_color, InputMethodManager.SHOW_IMPLICIT);
            Toast.makeText(this, "prueba", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
