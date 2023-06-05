package com.mradking.pdfspliter.other;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.mradking.pdfspliter.R;
import com.mradking.pdfspliter.activity.MainActivity;

import java.util.Arrays;

public class Ad_SetUp extends Activity {


    public  static  void load_banner_ad(Context context,LinearLayout linearLayout){

        AdView mAdView = new AdView(context);
        mAdView.setAdSize(AdSize.LARGE_BANNER);
        mAdView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        MobileAds.setRequestConfiguration(
                new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("ABCDEF012345"))
                        .build());
        AdRequest adRequest = new AdRequest.Builder()
                .build();

        if(mAdView.getAdSize() != null || mAdView.getAdUnitId() != null)
            mAdView.loadAd(adRequest);
        // else Log state of adsize/adunit
        linearLayout.addView(mAdView);

    }

    public  static  void load__big_banner_ad(Context context,LinearLayout linearLayout){

        AdView mAdView = new AdView(context);
        mAdView.setAdSize(AdSize.MEDIUM_RECTANGLE);
        mAdView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        MobileAds.setRequestConfiguration(
                new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("ABCDEF012345"))
                        .build());
        AdRequest adRequest = new AdRequest.Builder()
                .build();

        if(mAdView.getAdSize() != null || mAdView.getAdUnitId() != null)
            mAdView.loadAd(adRequest);
        // else Log state of adsize/adunit
        linearLayout.addView(mAdView);

    }
    public static void loadInterstitialAd(Context context) {
        ProgressDialog progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Please Wait Ad is Loading....");
        progressDialog.show();

        final InterstitialAd[] interstitialAd = new InterstitialAd[1];
       String AD_UNIT_ID ="ca-app-pub-3940256099942544/1033173712";

        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(
                context,
                AD_UNIT_ID,
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                       interstitialAd = interstitialAd;


                       interstitialAd_show(interstitialAd,context);

                        interstitialAd.show((Activity) context);

                        progressDialog.dismiss();

                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.


                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        // Make sure to set your reference to null so you don't
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when fullscreen content is shown.
                                        Log.d("TAG", "The ad was shown.");
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        interstitialAd[0] = null;

                        String error =
                                String.format(
                                        "domain: %s, code: %d, message: %s",
                                        loadAdError.getDomain(), loadAdError.getCode(), loadAdError.getMessage());

                    }
                });
    }

    private static void interstitialAd_show(InterstitialAd interstitialAd,Context context) {

        if(interstitialAd!=null){

            interstitialAd.show((Activity) context);
        }else {
            Toast.makeText(context, "ad not loading", Toast.LENGTH_SHORT).show();
        }

    }

}
