package es.udc.psi14.blanco_novoa.blanco_novoalab03b;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.LinearLayout;


public class FragmMainActivity extends Activity implements FragmOne.onArticleSelectedListener, FragmOne.onTextSelectedListener, FragmOne.onClearListener {

    public static String TAG = "Lab03b";
    private static String ACTIVITY = "MainActivity";
    FragmentTransaction transaction;
    String texto;
    int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, ACTIVITY + ": onCreate() -- before SetContentView");
        setContentView(R.layout.activity_fragm_main);
        Log.d(TAG, ACTIVITY + ": onCreate() -- after SetContentView");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        if (savedInstanceState != null) {
            FragmThree fragment = (FragmThree) getFragmentManager().findFragmentByTag("FRAG3");
            if (fragment==null) {
                FragmentManager fmanager = getFragmentManager();
                FragmentTransaction transaction = fmanager.beginTransaction();
                fragment = new FragmThree();
                transaction.add(R.id.container, fragment,"FRAG3").commit();
                fmanager.executePendingTransactions();
            }
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fragm_main, menu);
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

    @Override
    public void onArticleSelected(String name) {
        Log.d(TAG, ACTIVITY + ": onArticleSelected()");
        FragmentManager fmanager=getFragmentManager();
        FragmTwo fragment=(FragmTwo) fmanager.findFragmentById(R.id.fragment2);
        fragment.load(name);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("texto", texto);
        outState.putInt("size", size);
    }

    @Override
    public void onTextSelected(String string, int size) {
        Log.d(TAG, ACTIVITY + ": onTextSelected()");
        FragmThree fragment = (FragmThree) getFragmentManager().findFragmentByTag("FRAG3");
        if (fragment==null) {
            FragmentManager fmanager = getFragmentManager();
            FragmentTransaction transaction = fmanager.beginTransaction();
            fragment = new FragmThree();
            transaction.add(R.id.container, fragment,"FRAG3").commit();
            fmanager.executePendingTransactions();
        }
        texto = string;
        this.size = size;
        fragment.update(string, size);
    }

    @Override
    public void onClear() {
        Log.d(TAG, ACTIVITY + ": onClear()");
        FragmThree fragment = (FragmThree) getFragmentManager().findFragmentByTag("FRAG3");
        if (fragment != null){
            Log.d(TAG, ACTIVITY + ": onClear() efectivo");
            FragmentManager fmanager = getFragmentManager();
            FragmentTransaction transaction = fmanager.beginTransaction();
            transaction.remove(fragment);
            transaction.commit();
            fmanager.executePendingTransactions();
        }

    }
}
