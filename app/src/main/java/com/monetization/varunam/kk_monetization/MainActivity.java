package com.monetization.varunam.kk_monetization;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713j");

        MainFragment mainFragment = new MainFragment();
        getFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout_id, mainFragment)
                .commit();

    }
}
