package com.example.halthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HealthArticledetailsActivity extends AppCompatActivity {

    TextView tv1;
    ImageView img;
    Button Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_articledetails);


        tv1 = findViewById(R.id.textView6);
        img = findViewById(R.id.imageViewArticle);
        Back = findViewById(R.id.buttonArticleDetailsBack);


        Intent intent = getIntent();
        tv1.setText(intent.getStringExtra("text1"));

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int resId = bundle.getInt("text2");
            img.setImageResource(resId);
        }

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HealthArticledetailsActivity.this,HealthArticleActivity.class));
            }
        });

    }
}