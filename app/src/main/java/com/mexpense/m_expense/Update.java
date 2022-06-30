package com.mexpense.m_expense;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class Update extends AppCompatActivity {
    //This activity is used for updating the data entered by the user,
    //it accesses the database
    //alters the databse to change what the user thinks she/he might be wrong to right,
    //this is done after the user has confirmed everything in the edittext fields,
    //this activity also accesses the dBhelper activity before displaying the data in the edittext fields.

    Button button;
    EditText editText, editText1, editText2, editText3, editText4, editText5, editText6;
    DBhelper DB;
    TextView textView;
    Boolean insert = true;
    TextInputLayout textInputLayout;
    public String id, name, destination, date, description, time, clothing, meeting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        editText = findViewById(R.id.editTextTextPersonName);
        editText1 = findViewById(R.id.editTextDest);
        editText2 = findViewById(R.id.editTextDate);
        editText3 = findViewById(R.id.editTextDescription);
        editText4 = findViewById(R.id.editTime);
        editText5 = findViewById(R.id.editClothing);
        editText6 = findViewById(R.id.editMeeting);

        textView = findViewById(R.id.login);
        textInputLayout = findViewById(R.id.editname);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Update.this, EmployeeLogin.class);
                startActivity(intent);
            }
        });

        getIntentData();
        button = findViewById(R.id.submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB = new DBhelper(Update.this);
                String name = editText.getText().toString();
                String namedesc = editText1.getText().toString();
                String namedate = editText2.getText().toString();
                String nameDescription = editText3.getText().toString();
                String nameTime = editText4.getText().toString();
                String nameCloth = editText5.getText().toString();
                String nameMeet = editText6.getText().toString();
                if(name.isEmpty() || namedesc.isEmpty() || namedate.isEmpty()  || nameTime.isEmpty() || nameCloth.isEmpty() || nameMeet.isEmpty()){
                    editText.setError("Trip  name Required!!");
                    editText.requestFocus();
                    editText1.setError("Destination Required!!");
                    editText1.requestFocus();
                    editText2.setError("Date ot the trip Required!!");
                    editText2.requestFocus();
                    editText4.setError("Time Required!!");
                    editText4.requestFocus();
                    editText5.setError("Clothing type Required!!");
                    editText5.requestFocus();
                    editText6.setError("Meeting point Required!!");
                    editText6.requestFocus();
                }

                else {
                    editText.setError(null);
                    editText.requestFocus();
                    editText1.setError(null);
                    editText1.requestFocus();
                    editText2.setError(null);
                    editText2.requestFocus();
                    editText4.setError(null);
                    editText4.requestFocus();
                    editText5.setError(null);
                    editText5.requestFocus();
                    editText6.setError(null);
                    editText6.requestFocus();
                    AlertDialog.Builder builder = new AlertDialog.Builder(Update.this);
                    builder.setTitle("Confirm the entities");
                    builder.setMessage("confirm the data entered\n" +
                            "Trip name : " + editText.getText().toString().trim() + " \n " + "Destination : " + editText1.getText().toString().trim() + " \n " + "Date : " + editText2.getText().toString().trim() +
                            " \n " + "Description : "+ editText3.getText().toString().trim() + " \n " + "Time : " + editText4.getText().toString().trim() + " \n " + "Clothing : " + editText5.getText().toString().trim() +
                            "\n" + "Meeting point : " + editText6.getText().toString().trim());
                    builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DB.updatedatabase(id, name, namedesc, namedate, nameDescription, nameTime, nameCloth, nameMeet);
                            editText.setText(null);
                            editText1.setText(null);
                            editText2.setText(null);
                            editText3.setText(null);
                            editText4.setText(null);
                            editText5.setText(null);
                            editText6.setText(null);
                            Intent intent = new Intent(Update.this, MainActivity2.class);
                            startActivity(intent);

                        }
                    });
                    builder.setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            builder.setCancelable(true);

                        }
                    });
                    builder.show();

                }

            }
        });
    }
    public  void getIntentData(){
        if (getIntent().hasExtra("id") && getIntent().hasExtra("name1") && getIntent().hasExtra("Destination") && getIntent().hasExtra("Date")
                && getIntent().hasExtra("Description") && getIntent().hasExtra("Time") && getIntent().hasExtra("Clothing") && getIntent().hasExtra("Meeting")){
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name1");
            destination = getIntent().getStringExtra("Destination");
            date = getIntent().getStringExtra("Date");
            description = getIntent().getStringExtra("Description");
            time = getIntent().getStringExtra("Time");
            clothing = getIntent().getStringExtra("Clothing");
            meeting = getIntent().getStringExtra("Meeting");

            editText.setText(name);
            editText1.setText(destination);
            editText2.setText(date);
            editText3.setText(description);
            editText4.setText(time);
            editText5.setText(clothing);
            editText6.setText(meeting);


        }
        else {
            Toast.makeText(Update.this, "No data selected", Toast.LENGTH_SHORT).show();
        }
    }
}