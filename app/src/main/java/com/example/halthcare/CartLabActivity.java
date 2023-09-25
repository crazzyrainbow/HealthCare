package com.example.halthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CartLabActivity extends AppCompatActivity {
    HashMap<String,String> item;
    ArrayList list;
    SimpleAdapter sa;
    TextView tvTotal;
    ListView lst;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button CartDateButton,Checkout,btnBack;
    private String username;
    private String[][] packages = {};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_lab);
        CartDateButton = findViewById(R.id.buttonBMCartDate);
        btnBack = findViewById(R.id.ButtonBMCcartBack);
        Checkout = findViewById(R.id.ButtonBMCcartCheckout);
        tvTotal = findViewById(R.id.textViewBMCcartTotalCost);
        ListView lst = findViewById(R.id.listViewBMCcart);

        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","").toString();
        Database db = new Database(getApplicationContext(),"HealthCare",null,1);

        float TotalAmount = 0;
        ArrayList dbData = db.getCartData(username,"lab");
        //Toast.makeText(getApplicationContext(),""+dbData,Toast.LENGTH_LONG).show();

        packages = new String[dbData.size()][];
        for(int i = 0;i<packages.length;i++)
        {
            packages[i] = new String[5];
        }

        for(int i = 0;i<dbData.size();i++)
        {
            String ArrData = dbData.get(i).toString();
            String[] strData = ArrData.split(java.util.regex.Pattern.quote("$"));
            packages[i][0] = strData[0];
            packages[i][4] = "Cost :" + strData[1] + "/-";
            TotalAmount = TotalAmount + Float.parseFloat(strData[1]);
        }
        //tvTotal = findViewById(R.id.textViewCartTotalCost);
        tvTotal.setText("Total Cost : "+ TotalAmount);

        list = new ArrayList();
        for(int i = 0;i<packages.length;i++)
        {
            item = new HashMap<String,String>();
            item.put( "Line1",packages[i][0]);
            item.put( "Line2",packages[i][1]);
            item.put( "Line3",packages[i][2]);
            item.put( "Line4",packages[i][3]);
            item.put( "Line5",packages[i][4]);
            list.add(item);
        }
        sa = new SimpleAdapter(this,list,
                R.layout.multi_lines,
                new String[]{"Line1", "Line2", "Line3", "Line4", "Line5"},
                new int[] {R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e});
        lst.setAdapter(sa);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartLabActivity.this,LabTestActivity.class));
            }
        });

        Checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartLabActivity.this,LabTestBookActivity.class));
                Intent it = new Intent(CartLabActivity.this, LabTestBookActivity.class);
                it.putExtra("price", tvTotal.getText());
                it.putExtra("date", CartDateButton.getText().toString());
                //it.putExtra("time", CartTimeButton.getText().toString());
                startActivity(it);
            }
        });

        //Date picker
        initDatePicker();
        CartDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                datePickerDialog.show();
            }
        });
    }
    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1 = i1 + 1;
                CartDateButton.setText(i + "/" + i1 + "/" + i2);

            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;
        datePickerDialog = new DatePickerDialog(this,style,dateSetListener,year,month,day);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis()+86400000);
    }

}