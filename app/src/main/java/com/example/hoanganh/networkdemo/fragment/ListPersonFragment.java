package com.example.hoanganh.networkdemo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.hoanganh.networkdemo.R;
import com.example.hoanganh.networkdemo.adapter.PersonAdapter;
import com.example.hoanganh.networkdemo.entity.Person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HoangAnh on 4/1/2016.
 */
public class ListPersonFragment extends Fragment {
    private static final String BUNDLE_PERSON_LIST = "save_bundle";
    private ListView listView;
    private List<Person> personList = new ArrayList<>();

    public static ListPersonFragment newInstance(List<Person> personList) {
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_PERSON_LIST, (Serializable) personList);

        ListPersonFragment fragment = new ListPersonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getDataFromBundle(getArguments());
        } else {
            getDataFromBundle(savedInstanceState);
        }
    }

    private void getDataFromBundle(Bundle savedInstanceState) {
        personList = (List<Person>) savedInstanceState.getSerializable(BUNDLE_PERSON_LIST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_person_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.listView1);
        PersonAdapter adapter = new PersonAdapter(getActivity(), R.layout.fragment_person_list, personList);
        listView.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(BUNDLE_PERSON_LIST, (Serializable) personList);
    }
}
