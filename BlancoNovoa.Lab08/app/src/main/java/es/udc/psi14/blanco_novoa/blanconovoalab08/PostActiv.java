package es.udc.psi14.blanco_novoa.blanconovoalab08;

import android.app.Activity;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


public class PostActiv extends Activity {

    Button but_send, but_json;
    TextView tv_result;
    EditText et_username, et_passwd, et_ip;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        but_send = (Button) findViewById(R.id.button);
        but_json = (Button) findViewById(R.id.button2);
        tv_result = (TextView) findViewById(R.id.tv_result);
        et_username = (EditText) findViewById(R.id.et_username);
        et_passwd = (EditText) findViewById(R.id.et_passwd);
        et_ip = (EditText) findViewById(R.id.et_ip);

        lv = (ListView) findViewById(R.id.listView);

        but_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HttpGetTask httpGetTask = new HttpGetTask();

                httpGetTask.execute(et_username.getText().toString(),et_passwd.getText().toString(), et_ip.getText().toString());

            }
        });

        but_json.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpJsonTask httpJsonTask = new HttpJsonTask();
                httpJsonTask.execute();
            }
        });
    }

    private void onFinishPostRequest(String result) {
        tv_result.setText(result);
    }

    private class HttpGetTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... params) {
            HttpClient client = new DefaultHttpClient();

            String Url="http://"+params[2]+"/param.php";
            HttpPost request = new HttpPost(Url);

            ArrayList<BasicNameValuePair> par = new ArrayList<BasicNameValuePair>();
            par.add(new BasicNameValuePair("username", params[0])); // set param
            par.add(new BasicNameValuePair("password", params[1])); // set param
            HttpEntity postEntity = null;
            try {
                postEntity = new UrlEncodedFormEntity(par, HTTP.UTF_8);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            request.setEntity(postEntity);
            HttpResponse response;
            String result = "";
            try {
                response = client.execute(request);
                result = EntityUtils.toString(response.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            }


            return result;
        }
        protected void onPostExecute(String result) {
            Log.d("TAG", "esto es lo que sale: " + result);
            onFinishPostRequest(result);
        }

    }

    class HttpJsonTask extends AsyncTask<String, Void,ArrayList<String>>
    {
        protected ArrayList<String> doInBackground(String... params) {
            AndroidHttpClient client = AndroidHttpClient.newInstance("");
            HttpGet request = new HttpGet("http://datos.gijon.es/doc/transporte/busgijontr.json");
            JSONResponseHandler respHandler = new JSONResponseHandler();
            try { return client.execute(request, respHandler);
            } catch (ClientProtocolException e) { e.printStackTrace();
            } catch (IOException e) { e.printStackTrace(); }
            return null;
        }
        protected void onPostExecute(ArrayList<String> result) {
            updateUi(result);

        }
    }

    private void updateUi(ArrayList<String> result) {

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, result);
        lv.setAdapter(itemsAdapter);

    }

    class JSONResponseHandler implements ResponseHandler<ArrayList<String>> {
        public ArrayList<String> handleResponse(HttpResponse resp) throws ClientProtocolException, IOException {
            ArrayList<String> result = new ArrayList<String>();
            String JSONResp=new BasicResponseHandler().handleResponse(resp);
            try {
                JSONObject object = (JSONObject) new JSONTokener(JSONResp).nextValue();
                JSONObject posiciones = object.getJSONObject("posiciones");
                JSONArray posicion = posiciones.getJSONArray("posicion");
                for (int i = 0; i < posicion.length(); i++) {
                    JSONObject tmp = (JSONObject) posicion.get(i);
                    result.add("id:"+tmp.get("idlinea")+" matricula:"+tmp.getString("matricula")
                            +" parada:" +tmp.getString("ordenparada"));
                }
            } catch (JSONException e) { e.printStackTrace(); }
            return result;
        } }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.post, menu);
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
