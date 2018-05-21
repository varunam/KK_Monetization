package com.monetization.varunam.kk_monetization;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;

import static com.google.android.gms.ads.AdRequest.ERROR_CODE_INTERNAL_ERROR;
import static com.google.android.gms.ads.AdRequest.ERROR_CODE_INVALID_REQUEST;
import static com.google.android.gms.ads.AdRequest.ERROR_CODE_NETWORK_ERROR;
import static com.google.android.gms.ads.AdRequest.ERROR_CODE_NO_FILL;

public class InterstialAdListener extends AdListener {
    private Context context;
    private TextView textViewToBeUpdated;
    private AdUtils adUtils;
    private InterstitialAd interstitialAd;

    public InterstialAdListener(Context context, TextView textViewToBeUpdated) {
        this.context = context;
        this.textViewToBeUpdated = textViewToBeUpdated;
        adUtils = AdUtils.getInstance();
        this.interstitialAd = adUtils.getInterstitialAd();

    }

    @Override
    public void onAdClosed() {
        PointsPrefs pointsPrefs = PointsPrefs.getInstance();
        pointsPrefs.addPoints(2);
        String availablepoints = "Available Points: " + pointsPrefs.getAvailablePoints();
        textViewToBeUpdated.setText(availablepoints);
        adUtils.loadInterstitialAd(interstitialAd);
        super.onAdClosed();
    }

    @Override
    public void onAdFailedToLoad(int i) {
        super.onAdFailedToLoad(i);
        //TODO Handle below code properly. Toast is not the valid one
        if (i == ERROR_CODE_INTERNAL_ERROR)
            Toast.makeText(context, "Internal Error", Toast.LENGTH_LONG).show();
        else if (i == ERROR_CODE_INVALID_REQUEST)
            Toast.makeText(context, "Invalid Request", Toast.LENGTH_LONG).show();
        else if (i == ERROR_CODE_NETWORK_ERROR)
            Toast.makeText(context, "Network Error", Toast.LENGTH_LONG).show();
        else if (i == ERROR_CODE_NO_FILL)
            Toast.makeText(context, "No ads in inventory", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
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
        //TODO Toast to be removed
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
}
