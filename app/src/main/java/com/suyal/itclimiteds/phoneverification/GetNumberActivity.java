package com.suyal.itclimiteds.phoneverification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hbb20.CountryCodePicker;
import com.suyal.itclimiteds.R;
import com.suyal.itclimiteds.databinding.ActivityGetNumberBinding;

public class GetNumberActivity extends AppCompatActivity {

    CountryCodePicker ccp;
    ActivityGetNumberBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGetNumberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ccp.registerCarrierNumberEditText(binding.editNumber);

        binding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GetNumberActivity.this,VerifyOtpActivity.class);
                intent.putExtra("mobile",binding.ccp.getFullNumberWithPlus().trim());
                startActivity(intent);
            }
        });
    }
}