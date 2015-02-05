package es.udc.psi14.blanco_novoa.blanco_novoalab03b;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FragmOne#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class FragmOne extends Fragment {

    private onArticleSelectedListener listener;
    private onTextSelectedListener listener2;
    private onClearListener listener3;

    Button but_fic, but_gac, but_clear, but_exit;
    EditText et;
    SeekBar sb;

    public static String TAG = "Lab03b";
    private static String ACTIVITY = "FragmOne";

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmOne.
     *
     *
     */
    // TODO: Rename and change types and number of parameters
    public static FragmOne newInstance(String param1, String param2) {
        Log.d(TAG, ACTIVITY + ": newInstance()");
        FragmOne fragment = new FragmOne();
        return fragment;
    }
    public FragmOne() {
        // Required empty public constructor
    }

    interface onArticleSelectedListener {
        public void onArticleSelected(String string);
    }

    interface onTextSelectedListener {
        public void onTextSelected(String string, int size);
    }

    interface onClearListener {
        public void onClear();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, ACTIVITY + ": onCreate()");
    }

    public void onResume() {
        super.onResume();
        Log.d(TAG, ACTIVITY + ": onResume()");
        but_fic = (Button) getView().findViewById(R.id.but_fic);
        but_gac = (Button) getView().findViewById(R.id.but_gac);
        but_clear = (Button) getView().findViewById(R.id.but_clear);
        sb = (SeekBar) getView().findViewById(R.id.sb_f1);
        et = (EditText) getView().findViewById(R.id.et_f1);
        but_exit = (Button) getView().findViewById(R.id.but_exit);
        but_fic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                listener.onArticleSelected("http://www.fic.udc.es/");
            }
        });

        but_gac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                listener.onArticleSelected("http://gac.udc.es/inicio.html");
            }
        });

        but_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                listener3.onClear();
            }
        });

        but_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                alert.setTitle("Exit");
                alert.setMessage("Quieres salir? :'(");

// Set an EditText view to get user input
                final EditText input = new EditText(getActivity());
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                       getActivity().finish();
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Log.d(TAG, ACTIVITY + "  texto: "+input.getText().toString());
                        Toast.makeText(getActivity().getApplicationContext(), input.getText().toString(),Toast.LENGTH_LONG).show();
                    }
                });

                alert.show();
            }
        });

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, ACTIVITY + ": onCreateView()"+ et.getText());
                if (et.getText() != null && !et.getText().toString().equals("")) {
                    listener2.onTextSelected(et.getText().toString(), seekBar.getProgress());
                }
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, ACTIVITY + ": onCreateView()");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragm_one, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, ACTIVITY + ": onAttach()");

        try {
            listener = (onArticleSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity "+activity.toString()
                    +" must implement onArticleSelectedListener");
        }

        try {
            listener2 = (onTextSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity "+activity.toString()
                    +" must implement onTextSelectedListener");
        }

        try {
            listener3 = (onClearListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity "+activity.toString()
                    +" must implement onClearListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, ACTIVITY + ": onDetach()");
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */


}
