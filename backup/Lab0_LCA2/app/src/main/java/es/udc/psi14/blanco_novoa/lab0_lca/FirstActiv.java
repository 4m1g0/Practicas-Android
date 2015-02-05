package es.udc.psi14.blanco_novoa.lab0_lca;

import android.app.Activity;
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
    private int count = 0;
    Button killButton;
    Button countButton;
    TextView textCount;



    public void onCount(View view){
        textCount.setText(R.string.count_str + ++count);
    }

    public void onKill(View view){
        finish();
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
        Log.d(TAG, "FirstActiv: onCreate");

        killButton = (Button) findViewById(R.id.but_kill);
        countButton = (Button) findViewById(R.id.but_count);
        textCount = (TextView) findViewById(R.id.text_count);
        if (savedInstanceState != null)
            count = savedInstanceState.getInt("count");
        textCount.setText(R.string.count_str + count);
    }



    public FirstActiv() {
        super();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "FirstActiv: onStop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "FirstActiv: onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "FirstActiv: onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "FirstActiv: onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "FirstActiv: onDestory");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "FirstActiv: onPause");
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
