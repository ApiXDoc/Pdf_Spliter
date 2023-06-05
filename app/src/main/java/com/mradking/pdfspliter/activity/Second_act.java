package com.mradking.pdfspliter.activity;



import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.mradking.pdfspliter.R;
import com.mradking.pdfspliter.other.Ad_SetUp;
import com.mradking.pdfspliter.other.Utility;

import java.io.File;

public class Second_act extends Activity {

        Uri pdf_uri;
        EditText stating_et,ending_et;
        Button bt;
    Ad_SetUp adSetUp;
    LinearLayout adView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);

        pdf_uri=getIntent().getExtras().getParcelable("key");
        Toast.makeText(this, String.valueOf(pdf_uri), Toast.LENGTH_SHORT).show();

        stating_et=findViewById(R.id.starting_et);
        ending_et=findViewById(R.id.ending_et);
        adView=findViewById(R.id.adView);
        bt=findViewById(R.id.bt);
        Utility utility=new Utility();
        adSetUp=new Ad_SetUp();

        adSetUp.load_banner_ad(Second_act.this,adView);


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String file_name_st=utility.getRandomString(7)+".pdf";

                File file = new File(getFilesDir(), file_name_st);
                String outputFile = file.getPath();

                if(TextUtils.isEmpty(stating_et.getText().toString())){

                    Toast.makeText(getApplicationContext(), "Please Enter Your Starting Page Number", Toast.LENGTH_SHORT).show();

                }else if(TextUtils.isEmpty(ending_et.getText().toString())){

                    Toast.makeText(getApplicationContext(), "Please Enter Your Ending Page Number", Toast.LENGTH_SHORT).show();

                }else {

                    try {
                        utility.splitPdfFile(Second_act.this,pdf_uri,outputFile,
                                Integer.parseInt(stating_et.getText().toString()),
                                Integer.parseInt(ending_et.getText().toString()),getContentResolver(),
                                file_name_st
                        );

                        adSetUp.loadInterstitialAd(Second_act.this);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }




            }
        });



    }
}
