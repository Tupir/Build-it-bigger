package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.androidjoker.DisplayJokeActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;


public class MainActivityFragment extends Fragment {
    String LOG_TAG = this.getClass().getSimpleName();

    public MainActivityFragment() {
    }

    private ProgressBar progressBar;
    // ADs from https://developers.google.com/mobile-ads-sdk/docs/dfp/android/interstitial
    //BANNER from https://developers.google.com/admob/android/banner
    private PublisherInterstitialAd mPublisherInterstitialAd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        progressBar = root.findViewById(R.id.joke_progressbar);
        AdView mAdView = root.findViewById(R.id.adView);


        mPublisherInterstitialAd = new PublisherInterstitialAd(getContext());
        mPublisherInterstitialAd.setAdUnitId("/6499/example/interstitial");

        Button button =  root.findViewById(R.id.joke_btn);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mPublisherInterstitialAd.isLoaded()) {
                    mPublisherInterstitialAd.show();
                } else {
                    Log.i(LOG_TAG, "The interstitial wasn't loaded yet.");
                }
            }
        });

        mPublisherInterstitialAd.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.i(LOG_TAG, "onAdLoaded");
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                loadNextAD();
                progressBar.setVisibility(View.VISIBLE);
                new EndpointAsyncTask().execute(MainActivityFragment.this);
            }

        });

        loadNextAD();

        mAdView.loadAd(new AdRequest.Builder().build());
        return root;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    public void displayJokeActivity(String joke){
        if(!isOnline()){
            Toast.makeText(getContext(), R.string.no_connection, Toast.LENGTH_LONG).show();
        }else {
            Intent intent = new Intent(getActivity(), DisplayJokeActivity.class);
            intent.putExtra(getActivity().getString(R.string.joke_intent), joke);
            // add extra intent for paid version
            intent.putExtra("version", BuildConfig.FLAVOR.equals("paid"));
            getActivity().startActivity(intent);
        }
        progressBar.setVisibility(View.GONE);
    }


    private void loadNextAD() {
        mPublisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());
    }

}