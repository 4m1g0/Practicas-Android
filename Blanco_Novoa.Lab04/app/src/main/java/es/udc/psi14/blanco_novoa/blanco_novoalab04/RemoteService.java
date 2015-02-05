package es.udc.psi14.blanco_novoa.blanco_novoalab04;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class RemoteService extends Service {
    public int getCount() {
        Log.d(TAG, component + " getCount() ");
        return count;
    }

    public void setCount(int count) {
        Log.d(TAG, component + " setCount() " + count);
        this.count = count;
    }

    int count, time_wait;
    static String TAG = "LAB04";
    static String component = "RemoteService";
    MyThread thread;
    private final IBinder sBinder=(IBinder) new SimpleBinder();

    public RemoteService() {
    }

    class SimpleBinder extends Binder {
        RemoteService getService() {
            return RemoteService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, component + " onBind() ");

        count = intent.getIntExtra("count",20);
        time_wait = intent.getIntExtra("time_wait", 500);
        Log.d(TAG, component + " onStartCommand() count: " + count + "time_wait: " + time_wait);

        thread = new MyThread("my thread", count, time_wait);
        thread.start();

        return sBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, component + " onDestroy()");
        thread.interrupt();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
