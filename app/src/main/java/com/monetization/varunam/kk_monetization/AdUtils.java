package com.monetization.varunam.kk_monetization;

import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;

public class AdUtils {
    private static AdUtils adUtils = null;
    private InterstitialAd interstitialAd;
    private RewardedVideoAd rewardedVideoAd;

    private static final String INTERSTIAL_AD_ID = "ca-app-pub-8631195249626572/5601776359";

    private AdUtils(Context context) {
        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(context);
        interstitialAd = new InterstitialAd(context);
        //TODO test id to be changed with production id
        //interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.setAdUnitId(INTERSTIAL_AD_ID);
    }

    public static AdUtils getInstance(Context context) {
        if (adUtils == null)
            adUtils = new AdUtils(context);
        return adUtils;
    }

    public static AdUtils getInstance() {
        if (adUtils != null)
            return adUtils;

        throw new IllegalArgumentException("You should use getInstance(context) atleast once before using getInstance()");
    }

    public InterstitialAd getInterstitialAd() {
        return interstitialAd;
    }

    public void setInterstitialAd(InterstitialAd interstitialAd) {
        this.interstitialAd = interstitialAd;
    }

    public RewardedVideoAd getRewardedVideoAd() {
        return rewardedVideoAd;
    }

    public void setRewardedVideoAd(RewardedVideoAd rewardedVideoAd) {
        this.rewardedVideoAd = rewardedVideoAd;
    }


    public void loadInterstitialAd(InterstitialAd interstitialAd) {
        //TODO to test ads on test devices. need to be removed before pushing to production
        interstitialAd.loadAd(new AdRequest.Builder().addTestDevice("914AF69C3120FBD73C395326F89C5A26").build());
    }

    public void loadRewardedVideoAd(RewardedVideoAd rewardedVideoAd) {
        //TODO test id to be changed with production id
        rewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917", new AdRequest.Builder().build());
    }

}
