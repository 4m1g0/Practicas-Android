package es.udc.psi14.blanco_novoa.blanco_novoalab01;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class ParamActiv extends Activity {
    final String TAG = "LCA_TAG";
    final String Activ = "ParamActiv";
    final int request2 = 998;
    private int count = 0;
    EditText text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_param);
        text1 = (EditText) findViewById(R.id.edit_texto1);
        Intent intent = getIntent();
        String message = intent.getStringExtra("texto");
        Log.d(TAG, Activ + " mensjae recibido en intent: "+ message);
        if (message != null) {
            text1.setText(message);
        }
        if (intent.getAction() == Intent.ACTION_CALL){
            Uri url = intent.getData();
            text1.setText(url.toString());
            Log.d(TAG, Activ + " Recivida intent de llamada para " + url.toString());
        }
        Log.d(TAG, Activ + " onCreate");
    }

    public void onOk(View view){
        Log.d(TAG, Activ + " onOk");
        String texto = text1.getText().toString();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("texto", texto);
        setResult(RESULT_OK,returnIntent);
        finish();
    }

    public void onRecurs(View view){
        Log.d(TAG, Activ + " onRecurs");
        String texto = text1.getText().toString();
        Intent intent=new Intent(this, ParamActiv.class);
        intent.putExtra("texto", texto);

        startActivityForResult(intent, request2);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, Activ + " onActivityResult");
        if (requestCode == request2) {
            if(resultCode == RESULT_OK){
                String texto=data.getStringExtra("texto");
                Log.d(TAG, Activ + " intent recibe "+ texto);
                text1.setText(texto);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.param, menu);
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
}
