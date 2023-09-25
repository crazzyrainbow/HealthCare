package com.example.halthcare;

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

public class BuyMedicineActivity extends AppCompatActivity {

    private String[][] packages = {
            {"Uprise-03 1000IU Capsule", "" ,"","","50"},
            {"Healthvit Chromium Picolinate 200mcg Capsule","","","","305"},
            {"vitamin B Complex Capsules","","","","350"},
            {"Inlife Vitamin E Wheat Germ Oil Capsule","","","","445"},
            {"Dolo 658 Tablet","","","","385"},
            {"Crocin 650 Advance Tablet","","","","255"},
            {"Strepsils Medicated Lozenges for Sore Throat","","","","30"},
            {"Tata Ing Calcium Vitamin 03","","","","387"},
            {"Feronia-XT Tablet","","","","375"},
    };

    private String[] Package_Details = {
            "Building and keeping the bones & teeth strong\n"+
                    "Reducing Fatigue/stress and muscular pains\n"+
                    "Boosting immunity and increasing resistance against infection",
            "Chromium is an essential trace mineral that plays an important role in helping insulin regulate",
            "Provides relief from vitamin B deficiencies\n"+
                    "Helps in the formation of red blood cells\n"+
                    "Maintains a healthy nervous system",
            "It promotes health as well as skin benefits\n"+
                    "It helps reduce skin blemishes and pigmentation\n"+
                    "It acts as a safeguard for the skin from harsh UVA and UVB sun rays",
            "Dolo 650 Tablet helps relieve pain and fever by blocking the release of certain chemicals",
            "Helps relieve fever and bring down a high temperature\n"+
                    "Suitable for people with a heart condition or high blood pressure",
            "Relieves the symptoms of a bacterial throat infection and soothes the recovery\n"+
                    "Provides a warm and comforting feeling during a sore throat",
            "Reduces the risk of calcium deficiency, Rickets, and Osteoporosis\n"+
                    "Promotes mobility and flexibility of joints",
            "Helps to reduce iron deficiency due to chronic blood loss or low intake of iron"
    };

    HashMap<String, String> item;
    ArrayList list;
    SimpleAdapter sa;
    ListView lst;
    Button btnBack, btnGoToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine);

        btnBack = findViewById(R.id.ButtonBMCcartBack);
        btnGoToCart = findViewById(R.id.ButtonBMCcartCheckout);
        lst = findViewById(R.id.listViewBMCcart);

        btnGoToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BuyMedicineActivity.this,cartBuyMedicineActivity.class));
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BuyMedicineActivity.this, HomeActivity.class));
            }
        });

        list = new ArrayList();
        for (int i = 0; i < packages.length; i++) {
            item = new HashMap<String,String>();
            item.put("Line1", packages[i][0]);
            item.put("Line2", packages[i][1]);
            item.put("Line3", packages[i][2]);
            item.put("Line4", packages[i][3]);
            item.put("Line5", "Total Cost: " + packages[i][4] + "/-");
            list.add(item);
        }
        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines,
                new String[]{"Line1", "Line2", "Line3", "Line4", "Line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
        lst.setAdapter(sa);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(BuyMedicineActivity.this, BuyMedicineDetailsActivity.class);
                it.putExtra("text1", packages[i][0]);
                it.putExtra("text2", Package_Details[i]);
                it.putExtra("text3", packages[i][4]);
                startActivity(it);
            }
        });
    }
}
