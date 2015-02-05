package es.udc.psi14.blanco_novoa.blanco_novoalab04;

import android.app.ProgressDialog;
import android.os.AsyncTask;

/**
 * Created by 4m1g0 on 21/10/14.
 */
public class LongTask extends AsyncTask<Integer, Integer, Integer> {
    ServiceActiv activ;
    private ProgressDialog dialog;

    public LongTask(ServiceActiv activ) {
        this.activ = activ;

    }

    @Override
    protected void onCancelled() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        super.onCancelled();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(activ);
        dialog.setMessage("Doing something, please wait.");
        dialog.setMax(100);
        dialog.setCancelable(true);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.show();
    }

    @Override
    protected void onPostExecute(Integer i) {
        super.onPostExecute(i);
        if (dialog.isShowing()) {

            dialog.dismiss();
        }
        activ.asynkFinished();
    }

    @Override
    protected Integer doInBackground(Integer... time) {
        try {
            for (int i=1; i<=100;i++) {
                Thread.sleep(50);
                dialog.setProgress(i);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
