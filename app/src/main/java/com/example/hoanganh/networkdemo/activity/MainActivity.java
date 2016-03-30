package com.example.hoanganh.networkdemo.activity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import com.example.hoanganh.networkdemo.R;
import com.example.hoanganh.networkdemo.fragment.HomeFragment;

public class MainActivity extends Activity {
    private static final int CONTAINER = R.id.fragment_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment = getFragmentManager().findFragmentById(CONTAINER);
        if(fragment == null){
            fragment = new HomeFragment();
            getFragmentManager().beginTransaction().
                    add(CONTAINER, fragment).commit();
        }
    }
}
