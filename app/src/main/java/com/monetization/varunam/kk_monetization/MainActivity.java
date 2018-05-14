package com.monetization.varunam.kk_monetization;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button iwt_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iwt_button = findViewById(R.id.iwtButtonID);
        iwt_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.iwtButtonID:
                Toast.makeText(getApplicationContext(), "Button", Toast.LENGTH_LONG).show();
                break;
            default: break;
        }
    }
}
