package es.udc.psi14.blanco_novoa.blanco_novoalab04;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;


import es.udc.psi14.blanco_novoa.blanco_novoalab04.RemoteService.SimpleBinder;


public class ServiceActiv extends Activity {
    RemoteService rService;
    boolean mBound = false;
    ToggleButton toggle1, toggle2;
    static String TAG = "LAB04";
    static String component = "ServiceActiv";
    Button butSend, butGet, butBcast, butTask, butThread;
    EditText etCount, etCount2, etTimeWait;
    TextView tv;
    boolean asink;
    ProgressBar pb;

    MyReceiver receiver;


    public ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(
                ComponentName arg0, IBinder bind
        ){
            SimpleBinder sBinder=(SimpleBinder) bind;
            rService=
                    sBinder.getService
                            ();
            mBound = true;
        }
        @Override
        public void onServiceDisconnected(ComponentName arg0) { mBound=false; }
    };


    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        Log.d(TAG, component + " onCreate()");
        etCount = (EditText) findViewById(R.id.et_count);
        etCount2 = (EditText) findViewById(R.id.et_count2);
        etTimeWait = (EditText) findViewById(R.id.et_time_wait);
        tv = (TextView) findViewById(R.id.tv);
        butGet = (Button) findViewById(R.id.but_get);
        butSend= (Button) findViewById(R.id.but_send);

        toggle1 = (ToggleButton) findViewById(R.id.tb_localService);
        toggle1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.d(TAG, component + " onCreate() buton checked");
                    Intent intent = new Intent(getBaseContext(), LocalService.class);
                    try {
                        intent.putExtra("count", Integer.parseInt(etCount.getText().toString()));
                        intent.putExtra("time_wait", Integer.parseInt(etTimeWait.getText().toString()));
                    } catch (NumberFormatException e) { }
                    startService(intent);
                } else {
                    Log.d(TAG, component + " onCreate() buton not checked");
                    Intent intent = new Intent(getBaseContext(), LocalService.class);
                    stopService(intent);
                }
            }
        });

        toggle2 = (ToggleButton) findViewById(R.id.tb_remoteService);
        toggle2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.d(TAG, component + " onCreate() buton checked");
                    Intent intent = new Intent(getBaseContext(), RemoteService.class);
                    try {
                        intent.putExtra("count", Integer.parseInt(etCount.getText().toString()));
                        intent.putExtra("time_wait", Integer.parseInt(etTimeWait.getText().toString()));
                    } catch (NumberFormatException e) { }
                    bindService(intent, mConn, Context.BIND_AUTO_CREATE);
                } else {
                    Log.d(TAG, component + " onCreate() buton not checked");
                    unbindService(mConn);
                }
            }
        });
        butGet.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(rService.getCount() + "");
            }
        });

        butSend.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    rService.setCount(Integer.parseInt(etCount2.getText().toString()));
                } catch (NumberFormatException e) { }
            }
        });

        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(receiver, filter);

        butBcast = (Button) findViewById(R.id.but_bcast);
        butBcast.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBroadcast(new Intent("package.MyBroadcast1").
                        putExtra("key", "button bcast"));
            }
        });

        butTask = (Button) findViewById(R.id.but_task);
        butTask.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!asink) {
                    LongTask task = new LongTask(ServiceActiv.this);
                    task.execute();
                    asink = true;
                }

            }
        });

        pb = (ProgressBar) findViewById(R.id.pb);

        butThread = (Button) findViewById(R.id.but_thread);
        butThread.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        // get the bundle and extract data by key
                        Bundle b = msg.getData();
                        int progress = b.getInt("progress");
                        Log.d(TAG, "progress " + progress);
                        pb.setProgress(progress);
                    }
                };
                AsynkThread thread = new AsynkThread("AsynkThread", handler);
                thread.start();
            }
        });
    }

    public void asynkFinished() {
        asink = false;
    }



    @Override
    protected void onStop() {
        if (mBound) {
            unbindService(mConn);
            mBound = false;
        }
        receiver.stopServices();
        unregisterReceiver(receiver);
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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
