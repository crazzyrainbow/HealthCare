package com.example.halthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LabTestDetailsActivity extends AppCompatActivity {

    TextView tvPackageName,tvTotalCost;
    EditText edDetails;
    Button btnAddToCart,btnBack;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_details);

        tvPackageName = findViewById(R.id.TextViewBMCTitle);
        tvTotalCost = findViewById(R.id.textViewBMCcartTotalCost);
        edDetails = findViewById(R.id.editTextBMDTextMultiLine);
        btnBack = findViewById(R.id.ButtonBMCcartBack);
        btnAddToCart = findViewById(R.id.ButtonBMCcartCheckout);

        edDetails.setKeyListener(null);
        Intent intent = getIntent();
        tvPackageName.setText(intent.getStringExtra("Text1"));
        edDetails.setText(intent.getStringExtra("Text2"));
        tvTotalCost.setText("Total Cost : " + intent.getStringExtra("Text3")+"/-");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LabTestDetailsActivity.this,LabTestActivity.class));
            }
        });
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String Username = sharedPreferences.getString("username","").toString();
                Intent intent = getIntent();
                String Product = tvPackageName.getText().toString();
                float price = Float.parseFloat(intent.getStringExtra("Text3").toString());
                Database db = new Database(getApplicationContext(),"HealthCare",null,1);

                if(db.checkCartItem(Username,Product)==1)
                {
                    Toast.makeText(getApplicationContext(),"Product Already Added",Toast.LENGTH_SHORT).show();
                }
                else {
                    db.addCart(Username,Product,price,"lab");
                    Toast.makeText(getApplicationContext(),"Record Inserted to Cart",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LabTestDetailsActivity.this,LabTestActivity.class));
                }
            }
        });

    }
}