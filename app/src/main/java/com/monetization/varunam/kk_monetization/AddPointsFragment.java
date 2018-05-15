package com.monetization.varunam.kk_monetization;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class AddPointsFragment extends Fragment implements
        View.OnClickListener,
        RewardedVideoAdListener {

    private Button watch_ad, watch_video, buy_points;
    private TextView available_points;

    private PointsPrefs pointsPrefs;
    private Context context;
    private RewardedVideoAd rewardedVideoAd;
    private InterstitialAd interstitialAd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        View view = inflater.inflate(R.layout.layout_fragment_add_points, container, false);

        watch_ad = view.findViewById(R.id.watch_ad_id);
        watch_video = view.findViewById(R.id.watch_video_id);
        buy_points = view.findViewById(R.id.buy_points_id);
        available_points = view.findViewById(R.id.available_points_id);

        pointsPrefs = PointsPrefs.getInstance();
        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(context);
        rewardedVideoAd.setRewardedVideoAdListener(this);
        interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        loadRewardedVideoAd();
        loadInterstitialAd();

        watch_video.setOnClickListener(this);
        watch_ad.setOnClickListener(this);
        buy_points.setOnClickListener(this);

        String availablepoints = "Available Points: " + pointsPrefs.getAvailablePoints();
        available_points.setText(availablepoints);

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                pointsPrefs.addPoints(2);
                String availablepoints = "Available Points: " + pointsPrefs.getAvailablePoints();
                available_points.setText(availablepoints);
                loadInterstitialAd();
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                Toast.makeText(context, "InterstialAd Loaded", Toast.LENGTH_LONG).show();
                super.onAdLoaded();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }
        });

        return view;

    }

    private void loadInterstitialAd() {
        interstitialAd.loadAd(new AdRequest.Builder().build());
    }

    private void loadRewardedVideoAd() {
        rewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917", new AdRequest.Builder().build());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.watch_ad_id:
                if (interstitialAd.isLoaded())
                    interstitialAd.show();
                break;
            case R.id.watch_video_id:
                if (rewardedVideoAd.isLoaded())
                    rewardedVideoAd.show();
                Toast.makeText(context, "Showing ad", Toast.LENGTH_LONG).show();
                break;
            case R.id.buy_points_id:
                break;
        }
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        Toast.makeText(context, "AdLoaded", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardedVideoAd();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        Toast.makeText(context, "onRewarded! currency: " + rewardItem.getType() + "  amount: " +
                rewardItem.getAmount(), Toast.LENGTH_SHORT).show();
        pointsPrefs.addPoints(rewardItem.getAmount());
        String availablepoints = "Available Points: " + pointsPrefs.getAvailablePoints();
        available_points.setText(availablepoints);
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {
        loadRewardedVideoAd();
    }

    @Override
    public void onResume() {
        rewardedVideoAd.resume(context);
        super.onResume();
    }

    @Override
    public void onPause() {
        rewardedVideoAd.pause(context);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        rewardedVideoAd.destroy(context);
        super.onDestroy();
    }
}
