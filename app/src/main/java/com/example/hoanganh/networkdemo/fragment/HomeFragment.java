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
import android.widget.Toast;

import com.example.hoanganh.networkdemo.R;
import com.example.hoanganh.networkdemo.entity.Person;
import com.example.hoanganh.networkdemo.entity.Project;
import com.example.hoanganh.networkdemo.utils.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HoangAnh on 3/30/2016.
 */
public class HomeFragment extends Fragment {
    private static final int CONTAINER = R.id.fragment_container;
    private static final String TAG_PERSON_NAME = "name";
    private static final String TAG_PERSON_GENDER = "gender";
    private static final String TAG_PERSON_PROJECT = "projects";
    private static final String TAG_PROJECT_NAME = "name";
    private static final String TAG_PROJECT_ROLE = "role";
    private static final String URL = "https://api.myjson.com/bins/53x82";

    private ProgressDialog pDialog;
    private Button btnCallAPI, btnAbout;
    private GetContent task;
    private List<Person> personList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnCallAPI = (Button) view.findViewById(R.id.btnCallAPI);
        btnAbout = (Button) view.findViewById(R.id.btnAbout);

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
                    JSONArray jsonArray = new JSONArray(jsonStr);

                    personList.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonPersonObj = jsonArray.getJSONObject(i);

                        String personName = jsonPersonObj.getString(TAG_PERSON_NAME);
                        int gender = jsonPersonObj.getInt(TAG_PERSON_GENDER);

                        // Project la 1 JSONObject
                        JSONObject jsonProjectObj;
                        String projectName = null, role = null;
                        if (!jsonPersonObj.isNull(TAG_PERSON_PROJECT)) {
                            jsonProjectObj = jsonPersonObj.getJSONObject(TAG_PERSON_PROJECT);
                            projectName = jsonProjectObj.getString(TAG_PROJECT_NAME);

                            role = jsonProjectObj.getString(TAG_PROJECT_ROLE);
                        }

                        Project project = new Project(projectName, role);
                        Person person = new Person(personName, gender, project);

                        personList.add(person);
                    }
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

            // TODO Lay du lieu do sang list_person_fragment
            ListPersonFragment fragment = ListPersonFragment.newInstance(personList);

            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(CONTAINER, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
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
