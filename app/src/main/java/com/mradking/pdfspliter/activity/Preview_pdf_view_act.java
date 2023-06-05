package com.mradking.pdfspliter.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.mradking.pdfspliter.R;
import com.mradking.pdfspliter.other.Ad_SetUp;
import com.mradking.pdfspliter.other.Utility;

import java.io.File;

public class Preview_pdf_view_act extends Activity {
    PDFView pdfView;
    Uri pdf_uri;
    Button button;
    LinearLayout adView;

    @Override
    public void onBackPressed() {


        File file = new File(getIntent().getExtras().getString("output_path"));
        file.delete();
        super.onBackPressed();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_preview_pdf);

        pdfView = (PDFView) findViewById(R.id.pdfView);
        button=findViewById(R.id.bt);
        adView=findViewById(R.id.adView);

        pdf_uri=getIntent().getExtras().getParcelable("path");



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Ad_SetUp.load_banner_ad(Preview_pdf_view_act.this,adView);

            }
        }, 3000);

        pdfView.fromUri(pdf_uri)

                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .password(null)
                .pageFitPolicy(FitPolicy.WIDTH)

                .scrollHandle(new DefaultScrollHandle(this))
                .fitEachPage(true) // fit each page to the view, else smaller pages are scaled relative to largest page.
                .pageSnap(true) // snap pages to screen boundaries
                .pageFling(true) // make a fling change only a single page like ViewPager
                .nightMode(false)
                .load();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utility.save_file_in_sql(getIntent().getExtras().getString("key"),
                        getIntent().getExtras().getString("output_path"),Preview_pdf_view_act.this);

                finish();
                Ad_SetUp.loadInterstitialAd(Preview_pdf_view_act.this);

            }
        });

    }
}
