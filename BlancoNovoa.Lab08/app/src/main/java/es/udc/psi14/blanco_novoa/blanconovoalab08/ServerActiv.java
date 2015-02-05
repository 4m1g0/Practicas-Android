package es.udc.psi14.blanco_novoa.blanconovoalab08;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.conn.util.InetAddressUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;


public class ServerActiv extends Activity {

    Button but_listen, but_close;
    EditText et_port;
    TextView tv_ip, tv_display;
    private ServerSocket serverSocket;

    Handler updateConversationHandler;

    Thread serverThread = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        but_close = (Button) findViewById(R.id.but_close);
        but_listen = (Button) findViewById(R.id.but_listen);
        et_port = (EditText) findViewById(R.id.et_port);
        tv_ip = (TextView) findViewById(R.id.tv_ip);
        tv_display = (TextView) findViewById(R.id.tv_display);
        updateConversationHandler = new Handler();


        but_listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int puerto;
                try {

                    puerto = Integer.parseInt(et_port.getText().toString());
                } catch (NumberFormatException e)
                {
                   puerto = 6000;
                }
                but_listen.setEnabled(false);
                but_close.setEnabled(true);

                serverThread = new Thread(new ServerThread(puerto));
                serverThread.start();
            }
        });

        but_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (serverThread != null){
                        serverThread.interrupt();
                    }
                    if (serverSocket != null && !serverSocket.isClosed())
                        serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                but_close.setEnabled(false);
                but_listen.setEnabled(true);
            }
        });

        List<NetworkInterface> interfaces = null;
        try {
            interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());

            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress().toUpperCase();
                        boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        if (isIPv4) {
                            tv_ip.setText(sAddr);
                        } else {
                            int delim = sAddr.indexOf('%'); // drop ip6 port suffix
                            tv_ip.setText(delim<0 ? sAddr : sAddr.substring(0, delim));
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

    class ServerThread implements Runnable {
        int port;
        public ServerThread(int port){

            super();
            this.port = port;
        }

        public void run() {
            Socket socket = null;
            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (!Thread.currentThread().isInterrupted()) {

                try {

                    socket = serverSocket.accept();

                    new Thread(new CommunicationThresd(socket)).start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class CommunicationThresd implements Runnable {

        private Socket clientSocket;

        private BufferedReader input;

        public CommunicationThresd(Socket clientSocket) {

            this.clientSocket = clientSocket;

            try {

                this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {

            while (!Thread.currentThread().isInterrupted()) {

                try {

                    String read = input.readLine();
                    OutputStream out = clientSocket.getOutputStream();
                    PrintWriter pw = new PrintWriter(out, true); // true for autoflush
                    pw.println(read);

                    updateConversationHandler.post(new updateUIThread(read));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    class updateUIThread implements Runnable {
        private String msg;

        public updateUIThread(String str) {
            this.msg = str;
        }

        @Override
        public void run() {
            tv_display.setText(msg);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.server, menu);
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
