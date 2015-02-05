package es.udc.psi14.blanco_novoa.blanco_novoalab04;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    static String TAG = "LAB04";
    static String component = "MyReceiver";

    public MyReceiver() {
    }

    RemoteService rService;
    boolean mBound = false;
    Context serciveContext;

    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(
                ComponentName arg0, IBinder bind
        ){
            RemoteService.SimpleBinder sBinder=(RemoteService.SimpleBinder) bind;
            rService=
                    sBinder.getService
                            ();
            mBound = true;
        }
        @Override
        public void onServiceDisconnected(ComponentName arg0) { mBound=false; }
    };

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String key = intent.getStringExtra("key");
        if (key == null) {key = "";}
        Log.d(TAG, component + "Action " + action + " Key " + key);

        if (action == Intent.ACTION_USER_PRESENT) {
            Intent intent2 = new Intent(context, RemoteService.class);
            context.bindService(intent2, mConn, Context.BIND_AUTO_CREATE);
            serciveContext = context;
        }

        if (action == Intent.ACTION_POWER_CONNECTED) {
            Intent intent2 = new Intent(context, LocalService.class);
            context.startService(intent2);
        }
    }

    public void stopServices() {
        if (mBound) {
            serciveContext.unbindService(mConn);
            mBound = false;
        }
    }
}
