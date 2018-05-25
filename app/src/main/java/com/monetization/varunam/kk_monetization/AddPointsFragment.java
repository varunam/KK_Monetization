package com.monetization.varunam.kk_monetization;

import android.app.AlertDialog;
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

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.List;

public class AddPointsFragment extends Fragment implements
        View.OnClickListener,
        PurchasesUpdatedListener {

    private Button watch_ad, watch_video, buy_points;
    private TextView available_points;

    private PointsPrefs pointsPrefs;
    private Context context;

    private InterstitialAd interstitialAd;
    private RewardedVideoAd rewardedVideoAd;
    private AdUtils adUtils;
    private InterstialAdListener interstialAdListener;
    private RewardedVideoAdListener rewardedVideoAdListener;

    private BillingClient billingClient;
    private BillingFlowParams billingFlowParams;

    //TODO Dummy value used. need to remove and use actual one before pushing to production
    private static final String DUMMY_BILLING_PRODUCT = "android.test.purchased";
    private static final String AD_MOD_ID = "ca-app-pub-8631195249626572~3338102335";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        //MobileAds.initialize(context, "ca-app-pub-3940256099942544~3347511713j");
        MobileAds.initialize(context, AD_MOD_ID);

        View view = inflater.inflate(R.layout.layout_fragment_add_points, container, false);

        watch_ad = view.findViewById(R.id.watch_ad_id);
        watch_video = view.findViewById(R.id.watch_video_id);
        buy_points = view.findViewById(R.id.buy_points_id);
        available_points = view.findViewById(R.id.available_points_id);
        adUtils = AdUtils.getInstance(context);
        interstitialAd = adUtils.getInterstitialAd();
        rewardedVideoAd = adUtils.getRewardedVideoAd();

        interstialAdListener = new InterstialAdListener(context, available_points);
        rewardedVideoAdListener = new RewardVideoAdListener(context, available_points);

        billingClient = BillingClient.newBuilder(context).setListener(this).build();
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(int responseCode) {
                if (responseCode == BillingClient.BillingResponse.OK) {
                    Toast.makeText(context, "Billing OK", Toast.LENGTH_LONG).show();
                    consumePurchases();
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                Toast.makeText(context, "Billing Disconnected", Toast.LENGTH_LONG).show();
            }
        });
        billingFlowParams = BillingFlowParams.newBuilder()
                .setSku(DUMMY_BILLING_PRODUCT)
                .setType(BillingClient.SkuType.INAPP)
                .build();


        pointsPrefs = PointsPrefs.getInstance();

        adUtils.loadRewardedVideoAd(rewardedVideoAd);
        adUtils.loadInterstitialAd(interstitialAd);

        watch_video.setOnClickListener(this);
        watch_ad.setOnClickListener(this);
        buy_points.setOnClickListener(this);

        String availablepoints = "Available Points: " + pointsPrefs.getAvailablePoints();
        available_points.setText(availablepoints);

        interstitialAd.setAdListener(interstialAdListener);
        rewardedVideoAd.setRewardedVideoAdListener(rewardedVideoAdListener);

        return view;

    }

    private void consumePurchases() {
        Purchase.PurchasesResult purchasesResult = billingClient.queryPurchases(BillingClient.SkuType.INAPP);

        List<Purchase> ownedItems = purchasesResult.getPurchasesList();
        if (ownedItems != null && ownedItems.size() > 0) {
            Purchase purchase = ownedItems.get(0);
            Toast.makeText(context, purchase.getSku() + " " + purchase.getPurchaseToken(), Toast.LENGTH_LONG).show();
            billingClient.consumeAsync(purchase.getPurchaseToken(), new ConsumeResponseListener() {
                @Override
                public void onConsumeResponse(int responseCode, String purchaseToken) {
                    Toast.makeText(context, "Product Consumed", Toast.LENGTH_LONG).show();
                }
            });
        }
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
                int responseCode = billingClient.launchBillingFlow(getActivity(), billingFlowParams);
                if (responseCode == 0)
                    Toast.makeText(context, "Purchase Success", Toast.LENGTH_LONG).show();
                else if (responseCode == 7)
                    Toast.makeText(context, "Failed\nAlready own this item", Toast.LENGTH_LONG).show();
                break;
        }
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

    @Override
    public void onPurchasesUpdated(int responseCode, @Nullable List<Purchase> purchases) {
        if (responseCode == BillingClient.BillingResponse.OK && purchases != null) {
            for (Purchase purchase : purchases)
                handlePurchase(purchase);
            consumePurchases();
        }
    }

    private void handlePurchase(Purchase purchase) {
        if (purchase.getSku().equals(DUMMY_BILLING_PRODUCT)) {
            pointsPrefs.addPoints(10);
            String availablePoints = "Available Points: " + pointsPrefs.getAvailablePoints();
            available_points.setText(availablePoints);
            new AlertDialog.Builder(context)
                    .setTitle("Purchase Successful")
                    .setMessage("Sku: " + purchase.getSku() + "\n" +
                            "Purchase Token: " + purchase.getPurchaseToken() + "\n" +
                            "Purchase Name: " + purchase.getPackageName() + "\n" +
                            "Purchase Time: " + purchase.getPurchaseTime() + "\n" +
                            "Order ID: " + purchase.getOrderId() + "\n" +
                            "Signature: " + purchase.getSignature() + "\n" +
                            "Original Json: " + purchase.getOriginalJson())
                    .setCancelable(false)
                    .setPositiveButton("ok", null)
                    .create().show();
        }
    }
}
