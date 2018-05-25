package com.monetization.varunam.kk_monetization;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainFragment extends Fragment implements View.OnClickListener {

    private Button iwt_button, add_points, add_hundred, alertDialogueButton;
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
        alertDialogueButton = view.findViewById(R.id.dialogueButtonID);

        pointsPrefs = PointsPrefs.getInstance(context);
        iwt_button.setOnClickListener(this);
        add_points.setOnClickListener(this);
        add_hundred.setOnClickListener(this);
        alertDialogueButton.setOnClickListener(this);

        String availablePoints = "Available Points: " + pointsPrefs.getAvailablePoints();
        iwt_points_textview.setText(availablePoints);



        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
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
            case R.id.dialogueButtonID: {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = LayoutInflater.from(context).inflate(R.layout.layout_add_points_dialogue, null);
                TextView pointsText = view.findViewById(R.id.pointsTextID);
                Button button1 = view.findViewById(R.id.button2);
                Button button2 = view.findViewById(R.id.button);
                Button button3 = view.findViewById(R.id.button3);
                ImageView close = view.findViewById(R.id.closeImageID);

                final AlertDialog alertDialog = builder.setView(view).setCancelable(false).create();
                pointsText.setText("" + pointsPrefs.getAvailablePoints());
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                alertDialog.show();
                alertDialog.getWindow().setLayout(1050,950);

                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "Button1 selected", Toast.LENGTH_SHORT).show();
                    }
                });
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "Button2 selected", Toast.LENGTH_SHORT).show();
                    }
                });
                button3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "Button3 selected", Toast.LENGTH_SHORT).show();
                    }
                });
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                        //alertDialog.dismiss();
                    }
                });
            }
                break;
        }
    }
}
