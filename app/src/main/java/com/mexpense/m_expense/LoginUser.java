package com.mexpense.m_expense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginUser extends AppCompatActivity {

    //this activity allows admin to login to Employee activity.

    TextInputEditText text, text2;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        text = findViewById(R.id.user);
        text2 = findViewById(R.id.password);
        button = findViewById(R.id.login);
        //floatingActionButton = findViewById(R.id.floatbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            //Checking user log in credentials and validation
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
                else if (user.equals("other") && !pass.equals("1234567")){
                    text2.setError("Check Your Password");
                    text2.requestFocus();
                }
                else if(!user.equals("other") && pass.equals("1234567")){
                    text.setError("Check Your Username");
                    text.requestFocus();
                }
                else if(!user.equals("other") && !pass.equals("1234567")){
                    text.setError("Check Your Username");
                    text.requestFocus();
                    text2.setError("Check Your Password");
                    text2.requestFocus();
                }
                else if(user.equals("other") && pass.equals("1234567")){
                    text.setError(null);
                    text.requestFocus();
                    text2.setError(null);
                    text2.requestFocus();
                    text.setText(null);
                    text2.setText(null);
                    Toast.makeText(LoginUser.this, "Login successfull !!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginUser.this, MainActivity2.class);
                    startActivity(intent);

                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}