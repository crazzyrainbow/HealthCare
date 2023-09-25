package com.example.halthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BuyMedicineBookActivity extends AppCompatActivity {

    EditText edname, edaddress, edcontact, edpinCode;
    Button btnBMBBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine_book);

        edname = findViewById(R.id.editTextBMBFullName);
        edaddress = findViewById(R.id.editTextBMBAddress);
        edcontact = findViewById(R.id.editTextBMBCNumber);
        edpinCode = findViewById(R.id.editTextBMBPinCode);
        btnBMBBooking = findViewById(R.id.buttonBMBBooK);

        Intent intent = getIntent();
        String []price = intent.getStringExtra("price").toString().split(java.util.regex.Pattern.quote(":"));
        String date = intent.getStringExtra("date");

        btnBMBBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "");
                Database db = new Database(getApplicationContext(), "HealthCare", null, 1);

                db.AddOrder(username, edname.getText().toString(),edaddress.getText().toString(), edcontact.getText().toString(),
                        Integer.parseInt(edpinCode.getText().toString()), date.toString(),"",
                        Float.parseFloat(price[1].toString()), "MEDICINE");
                db.RemoveCartItem(username, "MEDICINE");

                Toast.makeText(getApplicationContext(), "Booking Confirmed", Toast.LENGTH_LONG).show();
                startActivity(new Intent(BuyMedicineBookActivity.this, HomeActivity.class));
            }
        });
    }
}