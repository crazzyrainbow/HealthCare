package com.example.halthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText edUserName,edEmail,edPassword,edConfirm;
    Button btn;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edUserName = findViewById(R.id.editTextBMBFullName);
        edEmail = findViewById(R.id.editTextBMBAddress);
        edPassword = findViewById(R.id.editTextBMBPinCode);
        edConfirm = findViewById(R.id.editTextBMBCNumber);
        btn = findViewById(R.id.buttonBMBBooK);
        tv = findViewById(R.id.textViewExistingUser);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edUserName.getText().toString();
                String email = edEmail.getText().toString();
                String password = edPassword.getText().toString();
                String confirm = edConfirm.getText().toString();

                Database db = new Database(getApplicationContext(),"HEALTHCARE",
                        null,1);

                if(username.length()==0 || email.length()==0 || password.length()==0
                        || confirm.length()==0)
                {
                    Toast.makeText(RegisterActivity.this, "Please Fill all the Details."
                            , Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(password.compareTo(confirm) == 0) {
                        if(isvalid(password)){
                            db.register(username,email,password);
                            Toast.makeText(RegisterActivity.this,"User Registered",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this,"Password Must Contain 8 characters," +
                                    "Digits,letters and Special Symbol.",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(RegisterActivity.this,"Password MisMatch",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }
    public static boolean isvalid(String passwordhere)
    {
        int f1 = 0,f2 = 0,f3 = 0;
        if(passwordhere.length() < 8)
        {
            return false;
        }
        else
        {
            for (int p = 0;p<passwordhere.length();p++)
            {
                if(Character.isLetter(passwordhere.charAt(p)))
                {
                    f1 = 1;
                }
            }
            for(int r = 0;r<passwordhere.length();r++)
            {
                if(Character.isDigit(passwordhere.charAt(r)))
                {
                    f2 = 1;
                }
            }
            for(int s = 0;s<passwordhere.length();s++)
            {
                char c = passwordhere.charAt(s);
                if(c>=33 && c < 46 || c == 64)
                {
                    f3 = 1;
                }
            }
            if(f1 ==1 && f2 == 1 && f3 == 1)
            {
                return true;
            }else{
                return false;
            }
        }
    }
}