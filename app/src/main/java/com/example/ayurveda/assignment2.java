package com.example.ayurveda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class assignment2 extends AppCompatActivity {
    Button next1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment2);

        next1 = findViewById(R.id.next_btn_2);
        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(assignment2.this,assignment3.class);
                startActivity(intent);
            }
        });
    }
}