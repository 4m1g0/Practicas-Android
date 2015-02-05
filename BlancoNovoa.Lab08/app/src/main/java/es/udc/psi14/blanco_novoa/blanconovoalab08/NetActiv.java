package es.udc.psi14.blanco_novoa.blanconovoalab08;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;


public class NetActiv extends Activity {

    EditText et_host, et_port, et_msg;
    Button but_send, but_cancel, but_get, but_back, but_forward, but_clear, but_reload;
    TextView tv_display;
    Socket socket;
    HttpGetTask httpGetTask;
    WebView web;

    private class Callback extends WebViewClient {
        @Override public boolean shouldOverrideUrlLoading (
                WebView view, String url) { return false; }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net);
        et_host = (EditText) findViewById(R.id.et_host);
        et_port = (EditText) findViewById(R.id.et_port);
        et_msg = (EditText) findViewById(R.id.et_msg);

        but_send = (Button) findViewById(R.id.but_send);
        but_cancel = (Button) findViewById(R.id.but_cancel);
        but_get = (Button) findViewById(R.id.but_get);
        but_back = (Button) findViewById(R.id.but_back);
        but_forward = (Button) findViewById(R.id.but_forward);
        but_clear = (Button) findViewById(R.id.but_clear);
        but_reload = (Button) findViewById(R.id.but_reload);


        tv_display = (TextView) findViewById(R.id.tv_display);
        web = (WebView) findViewById(R.id.webView);



        but_cancel.setEnabled(false);

        but_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                httpGetTask = new HttpGetTask();

                httpGetTask.execute(et_host.getText().toString(),et_port.getText().toString(), et_msg.getText().toString());
                but_cancel.setEnabled(true);
                but_send.setEnabled(false);

            }
        });

        but_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_display.setText("");
                but_cancel.setEnabled(false);
                if (!httpGetTask.isCancelled()) {
                    httpGetTask.cancel(true);
                }
                try {
                    if (socket != null && !socket.isClosed()) {
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        but_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = et_host.getText().toString();
                if (!url.startsWith("http://www.")) url = "http://www." + url;
                WebSettings webSettings = web.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setBuiltInZoomControls(true);
                web.setWebViewClient(new Callback());//Libro W-M Lee p´ag.290
                web.loadUrl(url);
            }
        });

        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.goBack();
            }
        });

        but_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.goForward();
            }
        });

        but_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.clearHistory();
            }
        });

        but_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.reload();
            }
        });

    }



    private void onFinishGetRequest(String result) {
            tv_display.setText(result);
            web.loadData(tv_display.getText().toString(), "text/html","UTF-8");

        but_send.setEnabled(true);

    }


    private class HttpGetTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... params) {
            StringBuffer data = new StringBuffer(); String rawData;
            Log.d("TAG", "esto es lo que ejecutamos: " + params[2]);
            try { int port = Integer.valueOf(params[1]); // conexion persistente
                if (socket == null || socket.isClosed()){
                    socket = new Socket();
                    socket.connect(new InetSocketAddress(params[0], port), 4000);
                    socket.setSoTimeout(4000);
                }


                OutputStream out = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(out, true); // true for autoflush
                pw.println(params[2]);
                InputStreamReader isr=new InputStreamReader(socket.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                while ((rawData = br.readLine()) != null) {
                    Log.d("TAG", "dentro del bucle " + rawData.toString());
                    data.append(rawData);
                    if (rawData.toString().equals(params[2])) {
                        Log.d("TAG", "break");
                        break;
                    }
                }



            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data.toString();
        }
        protected void onPostExecute(String result) {
            Log.d("TAG", "esto es lo que sale: " + result);
            onFinishGetRequest(result);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.net, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, PostActiv.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_serv) {
            Intent intent = new Intent(this, ServerActiv.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
