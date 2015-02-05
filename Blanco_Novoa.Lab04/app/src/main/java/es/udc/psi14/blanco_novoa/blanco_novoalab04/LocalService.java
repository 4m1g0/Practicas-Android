package es.udc.psi14.blanco_novoa.blanco_novoalab04;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class LocalService extends Service {

    int count, time_wait;
    static String TAG = "LAB04";
    static String component = "LocalService";
    MyThread thread;

    public LocalService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, component + " onDestroy()");
        thread.interrupt();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        count = intent.getIntExtra("count",20);
        time_wait = intent.getIntExtra("time_wait", 500);
        Log.d(TAG, component + " onStartCommand() count: " + count + "time_wait: " + time_wait);

        thread = new MyThread("my thread", count, time_wait);
        thread.start();


        return super.onStartCommand(intent, flags, startId);
    }
}
