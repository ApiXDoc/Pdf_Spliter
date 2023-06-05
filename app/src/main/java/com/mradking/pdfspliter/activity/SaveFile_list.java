package com.mradking.pdfspliter.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mradking.pdfspliter.R;
import com.mradking.pdfspliter.adapter.SaveFileAdapter;
import com.mradking.pdfspliter.database.DatabaseHelper;
import com.mradking.pdfspliter.modal.Modal;
import com.mradking.pdfspliter.other.Ad_SetUp;
import com.mradking.pdfspliter.other.Utility;

import java.util.ArrayList;
import java.util.List;

public class SaveFile_list extends AppCompatActivity {
    private RecyclerView cart_recycler_view;
    public SaveFileAdapter saveFileAdapter;
    LinearLayout adView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_pdf_list);

        DatabaseHelper db = new DatabaseHelper(this);
        cart_recycler_view =findViewById(R.id.list);
        adView=findViewById(R.id.adView);
        List<Modal> contacts = db.getAllContacts();

        saveFileAdapter = new SaveFileAdapter( SaveFile_list.this,contacts);

        LinearLayoutManager lm1 = new LinearLayoutManager(SaveFile_list.this, LinearLayoutManager.VERTICAL, false);
        lm1.setReverseLayout(true);
        lm1.setStackFromEnd(true);

        cart_recycler_view.setHasFixedSize(true);
        cart_recycler_view.setLayoutManager(lm1);
        cart_recycler_view.setAdapter(saveFileAdapter);



        Ad_SetUp.load__big_banner_ad(SaveFile_list.this,adView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Utility.Rating_us_dailoag(SaveFile_list.this);
            }
        }, 5000);


    }
}
