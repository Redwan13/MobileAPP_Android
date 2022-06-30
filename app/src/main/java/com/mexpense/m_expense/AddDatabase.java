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

public class AddDatabase extends AppCompatActivity {
    //intializing the object

    Button button;
    EditText editText, editText1, editText2, editText3, editText4, editText5, editText6;
    DBhelper DB;
    TextView textView;
    Boolean insert = true;
    TextInputLayout textInputLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //accesses its' ui i.e activity_add ui of this active.
        setContentView(R.layout.activity_add);

        //used to access the id of the ui components
        editText = findViewById(R.id.editTextTextPersonName);
        editText1 = findViewById(R.id.editTextDest);
        editText2 = findViewById(R.id.editTextDate);
        editText3 = findViewById(R.id.editTextDescription);
        editText4 = findViewById(R.id.editTime);
        editText5 = findViewById(R.id.editClothing);
        editText6 = findViewById(R.id.editMeeting);
        textView = findViewById(R.id.login);
        textInputLayout = findViewById(R.id.editname);
        //text onclick event opens the admin activity
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddDatabase.this, EmployeeLogin.class);
                startActivity(intent);
            }
        });

        button = findViewById(R.id.submit);
        //button add onclick
        //clicked to add data to database sqlite
        //add data to database by calling DBhelper class and access the method insert;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Getting trip details from user and notifying user for empty entries
                DB = new DBhelper(AddDatabase.this);
                //used to get the user input and save in the varibales declared under string buildin.
                String name = editText.getText().toString();
                String namedesc = editText1.getText().toString();
                String namedate = editText2.getText().toString();
                String nameDescription = editText3.getText().toString();
                String nameTime = editText4.getText().toString();
                String nameCloth = editText5.getText().toString();
                String nameMeet = editText6.getText().toString();
                //checks the user inputs and returns error if an error is encountered
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
                    //returns alertdialog for the user to confirm the details before adding to adding databse.
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddDatabase.this);
                    builder.setTitle("Confirm the entities");
                    builder.setMessage("comfirm the data entered\n" +
                            "Trip name : " + editText.getText().toString().trim() + " \n " + "Destination : " + editText1.getText().toString().trim() + " \n " + "Date : " + editText2.getText().toString().trim() +
                            " \n " + "Description : "+ editText3.getText().toString().trim() + " \n " + "Time : " + editText4.getText().toString().trim() + " \n " + "Clothing : " + editText5.getText().toString().trim() +
                            "\n" + "Meeting point : " + editText6.getText().toString().trim());
                    builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(AddDatabase.this, "Working the final page after loading the database", Toast.LENGTH_LONG).show();
                            DB.insertdaatabase(editText.getText().toString().trim(), editText1.getText().toString().trim(), editText2.getText().toString().trim(),
                                    editText3.getText().toString().trim(), editText4.getText().toString().trim(), editText5.getText().toString().trim(),
                                    editText6.getText().toString().trim());
                            editText.setText(null);
                            editText1.setText(null);
                            editText2.setText(null);
                            editText3.setText(null);
                            editText4.setText(null);
                            editText5.setText(null);
                            editText6.setText(null);
                            Intent intent = new Intent(AddDatabase.this, MainActivity2.class);
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
    //onbackpress destroys the activity.

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}