package com.mexpense.m_expense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class EmployeeLogin extends AppCompatActivity {

    TextInputEditText text, text2;
    Button button;
    //Employeelogin  activity validates the user and defines it to open Mainactivity2


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_login);

        text = findViewById(R.id.user);
        text2 = findViewById(R.id.password);
        button = findViewById(R.id.login);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            //Admin login and validation
            public void onClick(View v) {
                String user = text.getText().toString();
                String pass = text2.getText().toString();
                if (user.isEmpty() || pass.isEmpty()){
                    text.setError("User name required!!");
                    text.requestFocus();
                    text2.setError("Password is required !!");
                    text2.requestFocus();
                }
                else if(pass.length() < 6 || user.length() < 4){
                    text.setError("Username less than 4 characters");
                    text.requestFocus();
                    text2.setError("Password is length should be more 6 characters!!");
                    text2.requestFocus();
                }
                else if (user.equals("expense") && !pass.equals("1234567")){
                    text2.setError("Check Your Password");
                    text2.requestFocus();
                }
                else if(!user.equals("expense") && pass.equals("1234567")){
                    text.setError("Check Your Username");
                    text.requestFocus();
                }
                else if(!user.equals("expense") && !pass.equals("1234567")){
                    text.setError("Check Your Username");
                    text.requestFocus();
                    text2.setError("Check Your Password");
                    text2.requestFocus();
                }
                else if(user.equals("expense") && pass.equals("1234567")){
                    text.setError(null);
                    text.requestFocus();
                    text2.setError(null);
                    text2.requestFocus();
                    text.setText("");
                    text2.setText("");
                    Toast.makeText(EmployeeLogin.this, "Login successfull !!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(EmployeeLogin.this, Employee.class);
                    startActivity(intent);

                }
            }
        });

    }
}