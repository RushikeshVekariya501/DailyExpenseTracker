package com.example.dailyexpensetracker.summary;

import android.os.Bundle;

import com.example.dailyexpensetracker.MainActivity;
import com.example.dailyexpensetracker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dailyexpensetracker.summary.ui.main.SectionsPagerAdapter;

public class SummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        setTitle(R.string.title_activity_summary);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.summary_view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.summary_tabs);
        tabs.setupWithViewPager(viewPager);
        ImageView imgBack = (ImageView) findViewById(R.id.summary_imgBack);
        ImageView imgHome = (ImageView) findViewById(R.id.summary_imgHome);
        ImageView imgReport = (ImageView) findViewById(R.id.imgReport);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imgReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SummaryActivity.this, "FUCTIONALITY PENDING", Toast.LENGTH_LONG).show();
            }
        });
    }
}