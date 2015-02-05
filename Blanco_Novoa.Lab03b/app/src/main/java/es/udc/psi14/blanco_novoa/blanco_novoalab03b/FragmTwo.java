package es.udc.psi14.blanco_novoa.blanco_novoalab03b;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FragmTwo#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class FragmTwo extends Fragment {
    WebView webView;

    public static String TAG = "Lab03b";
    private static String ACTIVITY = "FragmTwo";
    private String url;





    public void onResume() {
        super.onResume();
        Log.d(TAG, ACTIVITY + ": onResume()");
        webView = (WebView)getView().findViewById(R.id.webView);
        webView.setWebViewClient(new Callback());

        if (url != null) {
            webView.loadUrl(url);
        }
    }

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading ( WebView view, String url) {
            return false;
        }
    }

    public void load(String url) {
        Log.d(TAG, ACTIVITY + ": load()");
        webView.loadUrl(url);
        this.url = url;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmTwo.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmTwo newInstance(String param1, String param2) {
        Log.d(TAG, ACTIVITY + ": newInstance()");
        FragmTwo fragment = new FragmTwo();
        return fragment;
    }
    public FragmTwo() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, ACTIVITY + ": onCreate()");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("url", url);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (savedInstanceState != null){
            url = savedInstanceState.getString("url");
        }
        return inflater.inflate(R.layout.fragment_fragm_two, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Activity activity) {
        Log.d(TAG, ACTIVITY + ": onAttach()");
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        Log.d(TAG, ACTIVITY + ": onDetach()");
        super.onDetach();

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
