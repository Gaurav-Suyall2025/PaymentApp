package com.suyal.itclimiteds.payment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.suyal.itclimiteds.R;

import org.json.JSONException;
import org.json.JSONObject;


public class PaymentProfile extends AppCompatActivity implements PaymentResultListener{

    EditText name,city,state,mobile,aadhar;
    private InterstitialAd mInterstitialAd;

    Button btPay;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_profile);

        btPay = findViewById(R.id.bt_pay);
        String sAmount = "10";

        name = findViewById(R.id.fullName);
        city = findViewById(R.id.cityName);
        state = findViewById(R.id.stateName);
        mobile = findViewById(R.id.phoneNumber);
        aadhar = findViewById(R.id.adharCardNumber);

        Intent intent = getIntent();
        String user_name = intent.getStringExtra("name");
        String user_city = intent.getStringExtra("city");
        String user_state = intent.getStringExtra("state");
        String user_mobile = intent.getStringExtra("phone");
        String user_aadhar = intent.getStringExtra("aadharNumber");

        name.setText(user_name);
        city.setText(user_city);
        state.setText(user_state);
        mobile.setText(user_mobile);
        aadhar.setText(user_aadhar);
        name.setKeyListener(null);
        name.setEnabled(false);

        city.setKeyListener(null);
        city.setEnabled(false);

        state.setKeyListener(null);
        state.setEnabled(false);

        mobile.setKeyListener(null);
        mobile.setEnabled(false);

        aadhar.setKeyListener(null);
        aadhar.setEnabled(false);


        int amount = Math.round(Float.parseFloat(sAmount)*100);

        btPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Checkout checkout = new Checkout();
                    checkout.setKeyID("rzp_test_MWftw5SRYkzprb");
                    checkout.setImage(R.drawable.rajorpay_logo);
                    JSONObject object = new JSONObject();
                    try {
                        object.put("name","Britannia Distributor");
                        object.put("description","Test Payment");
                        object.put("theme.color","#0093DD");
                        object.put("currency","INR");
                        object.put("amount",amount);
                        object.put("prefill.contact","XXXXXXXXXX");
                        object.put("prefill.email","itc.limited202@gmail.com");

                        checkout.open(PaymentProfile.this,object);

                    }catch (JSONException e){
                        e.printStackTrace();
                    }

            }
        });


        //Add
        MobileAds.initialize(PaymentProfile.this);

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(PaymentProfile.this, getString(R.string.inter_ads_unit_id), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.e("Error",loadAdError.toString());
            }

            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                mInterstitialAd = interstitialAd;

                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                    }

                    @Override
                    public void onAdImpression() {
                        super.onAdImpression();
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent();
                        mInterstitialAd = null;
                    }
                });
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mInterstitialAd != null){
                    mInterstitialAd.show(PaymentProfile.this);
                }else {
                    Log.e("AdPending","Ad is not ready yet!");
                }
            }
        },10000);


    }

    @Override
    public void onPaymentSuccess(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Payment ID");

        builder.setMessage(s);

        builder.show();
    }

    @Override
    public void onPaymentError(int i, String s) {

        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}