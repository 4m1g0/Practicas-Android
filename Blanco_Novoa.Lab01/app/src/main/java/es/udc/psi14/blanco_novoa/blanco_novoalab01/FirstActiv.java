package es.udc.psi14.blanco_novoa.blanco_novoalab01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.Override;


public class FirstActiv extends Activity {
    final String TAG = "LCA_TAG";
    final String Activ = "Actividad1";
    final int request1 = 999;
    private int count = 0;
    Button killButton;
    Button countButton;
    TextView textCount;



    public void onCount(View view){
        textCount.setText(getResources().getString(R.string.count_str) + ++count);
        Log.d(TAG, Activ + " onCount");
    }

    public void onKill(View view){
        Log.d(TAG, Activ + " onKill");
        finish();

    }

    public void onParamActiv(View view){
        Log.d(TAG, Activ + " onParamActiv");
        Intent intent=new Intent(this, ParamActiv.class);
        intent.putExtra("texto", count + "");
        startActivityForResult(intent, request1);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, Activ + " onActivityResult");
        if (requestCode == request1) {
            if(resultCode == RESULT_OK){
                String texto=data.getStringExtra("texto");
                Log.d(TAG, Activ + " intent recibe "+ texto);
                textCount.setText(getResources().getString(R.string.count_str) + texto);
                count = Integer.parseInt(texto);
            }
        }
    }

    public void onSecondActiv(View view){
        Log.d(TAG, Activ + " onSecondActiv");
        Intent intent=new Intent(this, SecondActiv.class);
        startActivity(intent);

    }

    public void onImplicitActiv(View view){
        Log.d(TAG, Activ + " onImplicitActiv");
        Intent intent=new Intent(this, ImplicitActiv.class);
        startActivity(intent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                Log.d(TAG, "La accion ha sido DOWN");
                return true;
            case (MotionEvent.ACTION_MOVE):
                Log.d(TAG, "La accion ha sido MOVE");
                return true;
            case (MotionEvent.ACTION_UP):
                Log.d(TAG, "La accion ha sido UP");
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Log.d(TAG, Activ + " onCreate");

        killButton = (Button) findViewById(R.id.but_kill);
        countButton = (Button) findViewById(R.id.but_count);
        textCount = (TextView) findViewById(R.id.text_count);
        if (savedInstanceState != null)
            count = savedInstanceState.getInt("count");
        textCount.setText(getResources().getString(R.string.count_str) + count);
    }



    public FirstActiv() {
        super();
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
        getMenuInflater().inflate(R.menu.first, menu);
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
