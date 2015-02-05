package es.udc.psi14.blanco_novoa.blanco_novoalab04;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by 4m1g0 on 21/10/14.
 */
public class AsynkThread extends Thread{
    static String TAG = "LAB04";
    static String component = "MyThread";
    Handler handler;

    public AsynkThread(String name, Handler handler){
        super(name);
        this.handler = handler;
    }

    public void run() {
        try {
            for (int i=1; i<=100;i++) {
                Thread.sleep(50);
                Message msg = new Message();
                Bundle b = new Bundle();
                b.putInt("progress", i);
                msg.setData(b);
                handler.sendMessage(msg);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
