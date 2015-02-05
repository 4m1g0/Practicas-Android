package es.udc.psi14.blanco_novoa.blanco_novoalab01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class SecondActiv extends Activity {
    final String TAG = "LCA_TAG";
    final String Activ = "Actividad2";
    private int count = 0;

    public void onSecondActiv(View view){
        Intent intent=new Intent(this, SecondActiv.class);
        startActivity(intent);
        Log.d(TAG, Activ + " onSecondActiv");
    }

    public void onKill(View view){
        Log.d(TAG, Activ + " onKill");
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.d(TAG, Activ + " onCreate");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, Activ + " onStop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, Activ + " onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, Activ + " onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, Activ + " onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, Activ + " onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, Activ + " onPause");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.second, menu);
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("count", count);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        count = savedInstanceState.getInt("count");
    }
}
