package com.monetization.varunam.kk_monetization;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class RewardVideoAdListener implements RewardedVideoAdListener {

    private Context context;
    private AdUtils adUtils;
    private RewardedVideoAd rewardedVideoAd;
    private TextView textViewToBeUpdated;

    public RewardVideoAdListener(Context context, TextView textViewToBeUpdated) {
        this.context = context;
        adUtils = AdUtils.getInstance();
        this.rewardedVideoAd = adUtils.getRewardedVideoAd();
        this.textViewToBeUpdated = textViewToBeUpdated;
    }


    @Override
    public void onRewardedVideoAdLoaded() {
        //TODO to be removed
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
        adUtils.loadRewardedVideoAd(rewardedVideoAd);
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        //TODO below toast may not be required. check and remove
        Toast.makeText(context, "onRewarded! currency: " + rewardItem.getType() + "  amount: " +
                rewardItem.getAmount(), Toast.LENGTH_SHORT).show();
        PointsPrefs pointsPrefs = PointsPrefs.getInstance();
        pointsPrefs.addPoints(rewardItem.getAmount());
        String availablepoints = "Available Points: " + pointsPrefs.getAvailablePoints();
        textViewToBeUpdated.setText(availablepoints);
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {
        adUtils.loadRewardedVideoAd(rewardedVideoAd);
    }

}
