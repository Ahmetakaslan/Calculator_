package com.asn.newcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity2Detail extends AppCompatActivity {
RecyclerView recyclerView;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2_detail);
         recyclerView=findViewById(R.id.recyclerView);

         intent=getIntent();
         Gecmis gecmis= (Gecmis) intent.getSerializableExtra("kilit");
    }
    public void History(View view){

    }
}