
package es.udc.psi14.blanco_novoa.blanconovoalab10;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;


public class LocActiv extends Activity implements LocationListener {

    TextView tv_prov, tv_loc;
    LocationManager locateManager;
    int networkcount, gpscount;
    Button but_gps, but_network, but_last;
    int estado = 0;



    @Override
    protected void onResume() {
        super.onResume();
        if (estado == 0) return;
        if (estado == 1) locateManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10, this);
        if (estado == 2) locateManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 10, this);
        if (estado == 3) {
            locateManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10, this);
            locateManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 10, this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loc);

        locateManager =(LocationManager)getSystemService(LOCATION_SERVICE);
        boolean enabled = locateManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // check if enabled and if not send user to the GPS settings
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }



        tv_loc = (TextView) findViewById(R.id.tv_loc);
        tv_prov = (TextView) findViewById(R.id.tv_prov);

        but_gps = (Button) findViewById(R.id.but_gps);
        but_network = (Button) findViewById(R.id.but_network);
        but_last = (Button) findViewById(R.id.but_last);

        but_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                but_gps.setEnabled(false);
                but_network.setEnabled(true);
                but_last.setEnabled(true);
                estado = 1;
                locateManager.removeUpdates(LocActiv.this);
                locateManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10, LocActiv.this);
            }
        });

        but_network.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                but_gps.setEnabled(true);
                but_network.setEnabled(false);
                but_last.setEnabled(true);
                estado = 2;
                locateManager.removeUpdates(LocActiv.this);
                locateManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 10, LocActiv.this);
            }
        });

        but_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                but_gps.setEnabled(true);
                but_network.setEnabled(true);
                but_last.setEnabled(false);
                estado = 3;
                locateManager.removeUpdates(LocActiv.this);
                locateManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 10, LocActiv.this);
                locateManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10, LocActiv.this);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.loc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, MapActiv.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
        tv_prov.setText(location.getProvider().toString());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd;HH:mm:ss.SSSZ");
        tv_loc.setText(location.getLatitude() + " " + location.getLongitude() + " " + sdf.format(location.getTime()));
    }

    @Override
    protected void onPause() {
        super.onPause();
        locateManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
