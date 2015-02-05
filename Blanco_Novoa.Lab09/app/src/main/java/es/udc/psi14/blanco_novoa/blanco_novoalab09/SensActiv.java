package es.udc.psi14.blanco_novoa.blanco_novoalab09;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SensActiv extends Activity implements SensorEventListener {

    Button but_check, but_unreg;
    TextView tv_name, tv_x, tv_y, tv_z;
    ListView lv;
    SensorManager sm;
    List<Sensor> allSens;
    Sensor mySens;
    EditText et_time, et_url;
    ToggleButton tb;
    AlarmManager as;
    PendingIntent pIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sens);

        but_check = (Button) findViewById(R.id.but_check);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_x = (TextView) findViewById(R.id.tv_x);
        tv_y = (TextView) findViewById(R.id.tv_y);
        tv_z = (TextView) findViewById(R.id.tv_z);
        lv = (ListView) findViewById(R.id.lv);
        et_time = (EditText) findViewById(R.id.et_time);
        et_url = (EditText) findViewById(R.id.et_url);
        tb = (ToggleButton) findViewById(R.id.tb);
        but_unreg = (Button) findViewById(R.id.but_unreg);


        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        but_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                allSens = sm.getSensorList(Sensor.TYPE_ALL);

                ArrayAdapter<Sensor> adapter = new ArrayAdapter<Sensor>(SensActiv.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, allSens);

                lv.setAdapter(adapter);

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mySens = allSens.get(position);
                sm.registerListener(SensActiv.this,mySens,SensorManager.SENSOR_DELAY_NORMAL);
                tv_name.setText(mySens.getName().toString());
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mySens = allSens.get(position);
                sm.unregisterListener(SensActiv.this,mySens);
                tv_name.setText("");
                return true;
            }
        });

        as = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    int time;
                    String url;
                    if (et_time.getText().toString().equals("")) time = 5000;
                    else time = Integer.parseInt(et_time.getText().toString())*1000;

                    if (et_url.getText().toString().equals("")) url = "http://www.fic.udc.es";
                    else url = et_url.getText().toString();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    pIntent = PendingIntent.getActivity(SensActiv.this, 0, intent, 0);
                    as.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime()+time,pIntent);

                }else {
                    as.cancel(pIntent);
                }
            }
        });

        but_unreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sm.unregisterListener(SensActiv.this);
                tv_name.setText("");
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sens, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(SensActiv.this);
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
    public void onSensorChanged(SensorEvent event) {
        tv_name.setText(event.sensor.getName());
        tv_x.setText("" + event.values[0]);
        if (event.values.length >= 3) {
            tv_y.setText(""+event.values[1]);
            tv_z.setText(""+event.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
