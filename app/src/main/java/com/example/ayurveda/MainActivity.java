package com.example.ayurveda;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mrecyclerView;
    LinearLayoutManager layoutManager;
    List<ModelClass>userList;
    Adapter adapter;
    Button next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        next = findViewById(R.id.next_btn_1);
        initData();
        initRecyclerView();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,assignment2.class);
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView() {
        mrecyclerView=findViewById(R.id.RecyclerView);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mrecyclerView.setLayoutManager(layoutManager);
        adapter=new Adapter(userList);
        mrecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }
    private void initData() {
        userList = new ArrayList<>();

        userList.add(new ModelClass(R.drawable.relaxi_oil,"Dr. Relaxi Oil","Joint pain, arthritis","MRP: 260 Rs.","_______________________________________"));

        userList.add(new ModelClass(R.drawable.platesyrup,"Plate Plus","Helpful to increase platelet count","MRP: 260 Rs.","_______________________________________"));

        userList.add(new ModelClass(R.drawable.herbalvita,"Herbal Vita","Daily health supplement","MRP: 260 Rs.","_______________________________________"));

        userList.add(new ModelClass(R.drawable.immunoboost,"Immunoboost","Immunomodulator","MRP: 260 Rs.","_______________________________________"));

        userList.add(new ModelClass(R.drawable.liv333,"Liv 333 Syrup","Liver cirrhosis","MRP: 260 Rs.","_______________________________________"));

        userList.add(new ModelClass(R.drawable.wellheartcapsule,"Well Heart","For heart disease","MRP: 260 Rs.","_______________________________________"));
    }
}
