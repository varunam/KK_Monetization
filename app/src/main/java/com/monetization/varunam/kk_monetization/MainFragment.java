package com.monetization.varunam.kk_monetization;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainFragment extends Fragment implements View.OnClickListener {

    private Button iwt_button, add_points, add_hundred;
    private TextView iwt_points_textview;

    private PointsPrefs pointsPrefs;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        View view = inflater.inflate(R.layout.layout_fragment_main, container, false);

        iwt_button = view.findViewById(R.id.iwtButtonID);
        add_points = view.findViewById(R.id.add_points_id);
        iwt_points_textview = view.findViewById(R.id.iwtPointsID);
        add_hundred = view.findViewById(R.id.add_hundred_id);

        pointsPrefs = PointsPrefs.getInstance(context);
        iwt_button.setOnClickListener(this);
        add_points.setOnClickListener(this);
        add_hundred.setOnClickListener(this);

        String availablePoints = "Available Points: " + pointsPrefs.getAvailablePoints();
        iwt_points_textview.setText(availablePoints);

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.iwtButtonID:
                if (pointsPrefs.deductPoints(1)) {
                    String availablePoints = "Available Points: " + pointsPrefs.getAvailablePoints();
                    iwt_points_textview.setText(availablePoints);
                } else
                    Toast.makeText(context, "Insufficient Points", Toast.LENGTH_LONG).show();
                break;
            case R.id.add_points_id:
                AddPointsFragment fragment = new AddPointsFragment();
                getFragmentManager().beginTransaction().replace(R.id.frame_layout_id, fragment, null).addToBackStack(null).commit();
                break;
            case R.id.add_hundred_id:
                pointsPrefs.addPoints(100);
                String availablePoints = "Available Points: " + pointsPrefs.getAvailablePoints();
                iwt_points_textview.setText(availablePoints);
                break;
        }
    }
}
