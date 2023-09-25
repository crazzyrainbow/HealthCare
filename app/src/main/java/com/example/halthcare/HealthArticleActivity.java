package com.example.halthcare;

import static com.example.halthcare.R.id.buttonBack;
import static com.example.halthcare.R.id.cardHealthDoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class HealthArticleActivity extends AppCompatActivity {
    Button btnBack1;
    ListView lst;

    private String[][] healthDetails = {
            {"Walking Daily","" ,"" ,"","Click More Details"},
            {"Home care of COVID-19","" ,"" ,"","Click More Details"},
            {"Stop Smoking","" ,"" ,"", "Click More Details"},
            {"Menstrual Cramps","" ,"" ,"","Click More Details"},
            {"Healthy Gut","" ,"" ,"","Click More Details"},
    };
    private int[] healthImages = {
            R.drawable.health1,
            R.drawable.health2,
            R.drawable.health3,
            R.drawable.health4,
            R.drawable.health5
    };
    HashMap<String,String> item;
    ArrayList list;
    SimpleAdapter sa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_article);
        btnBack1 = findViewById(R.id.buttonBackArticle);
        lst = findViewById(R.id.ListViewHealthArticle);
        btnBack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HealthArticleActivity.this,HomeActivity.class));
            }
        });

        list = new ArrayList();

        for (int i = 0; i < healthDetails.length; i++) {
            item = new HashMap<>();
            item.put("line1", healthDetails[i][0]);
            item.put("Line2", healthDetails[i][1]);
            item.put("Line3", healthDetails[i][2]);
            item.put("line4", healthDetails[i][3]);
            item.put("lines", healthDetails[i][4]);
            list.add(item);
        }

        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines, new String[]{"line1", "Line2", "lines", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
        lst.setAdapter(sa);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(HealthArticleActivity.this,HealthArticledetailsActivity.class);
                it.putExtra("text1", healthDetails[i][0]);
                it.putExtra("text2", healthImages[i]);
                startActivity(it);
            }
        });

    }
}