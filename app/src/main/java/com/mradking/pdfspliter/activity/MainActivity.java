package com.mradking.pdfspliter.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mradking.pdfspliter.R;
import com.mradking.pdfspliter.other.Ad_SetUp;
import com.mradking.pdfspliter.other.Utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;



public class MainActivity extends Activity {
    public static final int PICK_PDF_REQUEST = 1;
    Uri pdfUri;
    Utility utility;
    Ad_SetUp adSetUp;
    LinearLayout selcet,save_files,share_bt,rate_bt,adView;
    private static final int UPI_PAYMENT = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_split_dassboard);

         selcet=findViewById(R.id.select);
         save_files=findViewById(R.id.save_files);
         share_bt=findViewById(R.id.share_bt);
         rate_bt=findViewById(R.id.rate_us);
        adView=findViewById(R.id.adView);

         utility=new Utility();

        utility.requestStoragePermission(MainActivity.this);

         adSetUp=new Ad_SetUp();

         adSetUp.load_banner_ad(MainActivity.this,adView);


        selcet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,payment.class);
                startActivity(intent);


            }
        });


        save_files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,SaveFile_list.class);
                startActivity(intent);
            }
        });

        rate_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                utility.rateApp(MainActivity.this);

            }
        });

        share_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                utility.shareApp(MainActivity.this);
            }
        });


    }

    private void selectVideo()
    {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF"),
                PICK_PDF_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode,
                data);
        // Check condition
        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {



            pdfUri = data.getData();

            Intent intent=new Intent(MainActivity.this,Second_act.class);
            intent.putExtra("key",pdfUri);
            startActivity(intent);



        }
    }









}