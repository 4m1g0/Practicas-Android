package es.udc.psi14.blanco_novoa.blanco_novoalab03b;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link FragmThree#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class FragmThree extends Fragment {

    TextView tv3;
    public static String TAG = "Lab03b";
    private static String ACTIVITY = "FragmThree";
    private String texto;
    int size;

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, ACTIVITY + ": onResume()");
        tv3 = (TextView) getView().findViewById(R.id.tv_f3);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.weight = 2;
        getView().setLayoutParams(params);
        if (texto != null) {
            tv3.setText(texto);
            tv3.setTextSize(size);
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmThree.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmThree newInstance(String param1, String param2) {
        FragmThree fragment = new FragmThree();
        Log.d(TAG, ACTIVITY + ": newInstance()");
        return fragment;
    }
    public FragmThree() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, ACTIVITY + ": onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, ACTIVITY + ": onCreateView()");
        if (savedInstanceState != null) {
            texto = savedInstanceState.getString("texto");
            size = savedInstanceState.getInt("size");
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragm_three, container, false);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, ACTIVITY + ": onAttach()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, ACTIVITY + ": onDetach()");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (texto != null) {
            outState.putString("texto", texto);
            outState.putInt("size", size);
        }
    }

    public void update(String txt, int size) {
        Log.d(TAG, ACTIVITY + ": update()");
        Log.d(TAG, ACTIVITY + ": update()" + txt + size);
        tv3.setText(txt);
        tv3.setTextSize((int) size);
        texto = txt;
        this.size = size;
    }

}
