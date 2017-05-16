package com.example.defangfang.schoolinfopublishsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class InfoDetail extends AppCompatActivity {
    private TextView infotitle;
    private TextView infocontent;
    private TextView infowriter;
    private TextView infodate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_detail);
        infotitle = (TextView)findViewById(R.id.info_detail_title);
        infocontent = (TextView)findViewById(R.id.info_detail_content);
        infowriter = (TextView)findViewById(R.id.info_detail_writer);
        infodate = (TextView)findViewById(R.id.info_detail_date);
        Intent intent = getIntent();
        infotitle.setText(intent.getStringExtra("title"));
        infocontent.setText(intent.getStringExtra("content"));
        infowriter.setText(intent.getStringExtra("writer"));
        infodate.setText(intent.getStringExtra("date"));
    }
}
