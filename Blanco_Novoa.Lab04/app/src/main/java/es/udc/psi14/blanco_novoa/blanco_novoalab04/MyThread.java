package es.udc.psi14.blanco_novoa.blanco_novoalab04;

import android.util.Log;

/**
 * Created by 4m1g0 on 14/10/14.
 */
public class MyThread extends Thread{
    int count, time_wait;
    static String TAG = "LAB04";
    static String component = "MyThread";

    public MyThread(String name, int count, int time_wait){
        super(name);
        this.count = count;
        this.time_wait = time_wait;
    }

    public void run() {
        for (int i = 0; i < count && !Thread.interrupted(); i++) {
            try {
                Thread.sleep(time_wait);
                Log.d(TAG, component + " esperamos");
            } catch (InterruptedException e) {
                Log.d(TAG, component + " break");
                break;
            }
        }
    }
}
