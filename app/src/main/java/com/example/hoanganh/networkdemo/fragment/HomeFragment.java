package com.example.hoanganh.networkdemo.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoanganh.networkdemo.R;
import com.example.hoanganh.networkdemo.utils.ServiceHandler;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by HoangAnh on 3/30/2016.
 */
public class HomeFragment extends Fragment {
    private static final int CONTAINER = R.id.fragment_container;
    private static final String TAG_CONTENT = "content";
    private static final String URL = "https://api.myjson.com/bins/19px8";
    private static final String SAVE_TEXT = "text";
    private static boolean isRunning = true;

    private ProgressDialog pDialog;
    private Button btnCallAPI, btnAbout;
    private TextView txtContent;
    private String content;
    private GetContent task;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            content = savedInstanceState.getString(SAVE_TEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnCallAPI = (Button) view.findViewById(R.id.btnCallAPI);
        btnAbout = (Button) view.findViewById(R.id.btnAbout);

        txtContent = (TextView) view.findViewById(R.id.textView);
        if (content != null) txtContent.setText(content);

        btnCallAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkConnected()) {
                    task = new GetContent();
                    task.execute();
                } else {
                    Toast.makeText(getActivity(), "No network", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutFragment fragment = new AboutFragment();

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(CONTAINER, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager manager = (ConnectivityManager) getActivity().
                getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (txtContent.getText() != null)
            outState.putString(SAVE_TEXT, txtContent.getText().toString());
    }

    private class GetContent extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ServiceHandler handler = new ServiceHandler();

            String jsonStr = handler.makeServiceCall(URL, ServiceHandler.GET);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    content = jsonObj.getString(TAG_CONTENT);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the URL");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }

            if (content != null)
                txtContent.setText(content);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("AAA", "Pause");

        if (task != null)
            task.cancel(true);
    }
}
