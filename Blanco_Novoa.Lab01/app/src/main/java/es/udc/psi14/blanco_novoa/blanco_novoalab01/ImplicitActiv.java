package es.udc.psi14.blanco_novoa.blanco_novoalab01;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class ImplicitActiv extends Activity {

    EditText sms_number;
    EditText sms_text;
    EditText call_number;
    EditText map_latitude;
    EditText map_longitude;
    EditText web_page;
    final String TAG = "LCA_TAG";
    final String Activ = "ImplicitActiv";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_implicit);
        sms_number = (EditText) findViewById(R.id.sms_number);
        sms_text = (EditText) findViewById(R.id.sms_text);
        call_number = (EditText) findViewById(R.id.call_number);
        map_latitude = (EditText) findViewById(R.id.map_latitude);
        map_longitude = (EditText) findViewById(R.id.map_longitude);
        web_page = (EditText) findViewById(R.id.web_page);
    }

    public void onSMS(View view){
        Log.d(TAG, Activ + " onSMS");
        String number = sms_number.getText().toString();
        if (number.matches("")) {number = "693344924";}
        String text = sms_text.getText().toString();
        if (text.matches("")) {text = "texto del sms";}
        Intent i = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:"+number));
        i.putExtra("sms_body", text);
        startActivity(i);
    }

    public void onCall(View view){
        Log.d(TAG, Activ + " onCall");
        String number = call_number.getText().toString();
        Log.d(TAG, Activ + " numero de telefono recogido: " + number);
        if (number.matches("")) {number = "693344924";}
        Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+number));
        startActivity(i);
    }

    public void onCamera(View view){
        Log.d(TAG, Activ + " onCamera");
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(i);
    }

    public void onMap(View view){
        Log.d(TAG, Activ + " onMap");
        String latitude = map_latitude.getText().toString();
        if (latitude.matches("")) {latitude = "45.1239261";}
        String longitude = map_longitude.getText().toString();
        if (longitude.matches("")) {longitude = "-123.1135746";}
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:"+latitude+","+longitude));
        startActivity(i);
    }

    public void onWeb(View view){
        Log.d(TAG, Activ + " onWeb");
        String uri = web_page.getText().toString();
        if (uri.matches("")) {uri = "todohacker.com";}
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+uri));
        Intent chooser = Intent.createChooser(i, "choose one");
        startActivity(chooser);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.impicit, menu);
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
