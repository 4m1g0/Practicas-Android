package es.udc.psi14.blanco_novoa.blanco_novoalab03;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class CreaBotones extends Activity implements View.OnTouchListener {

    RelativeLayout rl;
    int nbotones = 0;
    private static final String TAG = "Creabotones";
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_botones);

        rl = (RelativeLayout) findViewById(R.id.main_layout);
        rl.setOnTouchListener(this);
        sharedpreferences = getSharedPreferences("botones", Context.MODE_PRIVATE);
        if (sharedpreferences.contains("contador")){
            nbotones = sharedpreferences.getInt("contador",0);
        }

        for (int i = 0; i<10; i++) {
            if (sharedpreferences.contains("but"+i+"x")){
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(sharedpreferences.getInt("but"+i+"x",0), sharedpreferences.getInt("but"+i+"y",0), 0, 0);
                Button b = new Button(this);
                b.setText("numero");
                b.setLayoutParams(params);
                final int u = i;
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button b = (Button) v;
                        rl.removeView(b);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.remove("but"+u+"x");
                        editor.remove("but"+u+"y");
                        nbotones--;
                        editor.putInt("contador", nbotones);
                        editor.commit();

                    }
                });
                rl.addView(b);
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.crea_botones, menu);
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
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && nbotones < 10) {

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt("but"+nbotones+"x", (int) event.getX());
            editor.putInt("but"+nbotones+"y", (int) event.getY());
            nbotones++;
            editor.putInt("contador", nbotones);
            editor.commit();
            Log.d(TAG, "ontouch: " +nbotones);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins((int) event.getX(), (int) event.getY(), 0, 0);
            Button b = new Button(this);
            b.setText("numero");
            b.setLayoutParams(params);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button) v;
                    rl.removeView(b);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.remove("but"+nbotones+"x");
                    editor.remove("but"+nbotones+"y");
                    nbotones--;
                    editor.putInt("contador", nbotones);
                    editor.commit();

                }
            });
            rl.addView(b);

            return true;
        }
        else {
            return false;
        }
    }
}
