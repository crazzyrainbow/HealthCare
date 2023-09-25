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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Book_Appointmnrt_Activity extends AppCompatActivity {
    EditText ed1,ed2,ed3,ed4;
    TextView tv;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button dateButton,timeButton,btnBook,btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointmnrt);

        tv = findViewById(R.id.textViewBMBPackageName);
        ed1 = findViewById(R.id.editTextBMBFullName);
        ed2 = findViewById(R.id.editTextBMBAddress);
        ed3 = findViewById(R.id.editTextBMBPinCode);
        ed4 = findViewById(R.id.editTextBMBCNumber);
        dateButton = findViewById(R.id.buttonBMCartDate);
        timeButton = findViewById(R.id.buttonCartTimePicker);
        btnBack = findViewById(R.id.buttonBack);
        btnBook = findViewById(R.id.buttonBook);

        ed1.setKeyListener(null);
        ed2.setKeyListener(null);
        ed3.setKeyListener(null);
        ed4.setKeyListener(null);

        Intent it = getIntent();
        String title = it.getStringExtra("text1");
        String FullName = it.getStringExtra("text2");
        String Address = it.getStringExtra("text3");
        String Contact = it.getStringExtra("text4");
        String Fees = it.getStringExtra("text5");

        tv.setText(title);
        ed1.setText(FullName);
        ed2.setText(Address);
        ed3.setText(Contact);
        ed4.setText("Cons Fees:" + Fees + "/");


        //Date picker
        initDatePicker();
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                datePickerDialog.show();
            }
        });

        //Time picker
        initTimePicker();
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                timePickerDialog.show();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Book_Appointmnrt_Activity.this,FindDoctorActivity.class));
            }
        });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Database db = new Database(getApplicationContext(),"HealthCare",null,1);
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username","").toString();
                if(db.CheckAppointmentExists(username, title + " => " + FullName, Address, Contact, dateButton.getText().toString(), timeButton.getText().toString()) == 1)
                {
                    Toast.makeText(getApplicationContext(),"Appointment already Booked.",Toast.LENGTH_LONG).show();
                }
                else{
                    db.AddOrder(username,title +" => " + FullName,Address,Contact,0,dateButton.getText().toString(),timeButton.getText().toString(),Float.parseFloat(Fees),"Appointment");
                    Toast.makeText(getApplicationContext(),"Your Appointment is Booked Successfully.",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Book_Appointmnrt_Activity.this,HomeActivity.class));
                }
            }
        });
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1 = i1 + 1;
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
    private void initTimePicker()
    {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                timeButton.setText(i+":"+i1);
            }
        };
        Calendar cal = Calendar.getInstance();
        int hrs = cal.get(Calendar.HOUR);
        int mins = cal.get(Calendar.MINUTE);
        int style = AlertDialog.THEME_HOLO_DARK;
        timePickerDialog = new TimePickerDialog(this,style,timeSetListener,hrs,mins,true);

    }

}