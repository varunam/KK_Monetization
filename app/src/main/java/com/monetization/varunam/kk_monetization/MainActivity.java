package com.monetization.varunam.kk_monetization;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainFragment mainFragment = new MainFragment();
        getFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout_id, mainFragment)
                .commit();

    }
}
