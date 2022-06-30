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

public class Delete extends AppCompatActivity {

    EditText editText, editText1, editText2, editText3, editText4, editText5, editText6;
    DBhelper DB;
    Boolean insert = true;
    TextInputLayout textInputLayout;
    Button button;
    public String id, name, destination, date, description, time, clothing, meeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        editText = findViewById(R.id.editTextTextPersonName);
        editText1 = findViewById(R.id.editTextDest);
        editText2 = findViewById(R.id.editTextDate);
        editText3 = findViewById(R.id.editTextDescription);
        editText4 = findViewById(R.id.editTime);
        editText5 = findViewById(R.id.editClothing);
        editText6 = findViewById(R.id.editMeeting);
        textInputLayout = findViewById(R.id.editname);

        getIntentData();
        button = findViewById(R.id.submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB = new DBhelper(Delete.this);
                //alerts the contents in that culomn before deletion is done.
                AlertDialog.Builder builder = new AlertDialog.Builder(Delete.this);
                builder.setTitle("Confirm the entities");
                builder.setMessage("confirm the data entered\n" +
                            "Trip name : " + editText.getText().toString().trim() + " \n " + "Destination : " + editText1.getText().toString().trim() + " \n " + "Date : " + editText2.getText().toString().trim() +
                            " \n " + "Description : "+ editText3.getText().toString().trim() + " \n " + "Time : " + editText4.getText().toString().trim() + " \n " + "Clothing : " + editText5.getText().toString().trim() +
                            "\n" + "Meeting point : " + editText6.getText().toString().trim());
                builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DB.deletedatabase(id);
                        editText.setText(null);
                        editText1.setText(null);
                        editText2.setText(null);
                        editText3.setText(null);
                        editText4.setText(null);
                        editText5.setText(null);
                        editText6.setText(null);
                        Intent intent = new Intent(Delete.this, Employee.class);
                        startActivity(intent);

                        }
                    });
                    builder.setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();

                }
        });
    }
    //getting extras from the employee activity and setting them to the ui interface of delete activity.
    public  void getIntentData(){
        if (getIntent().hasExtra("id") && getIntent().hasExtra("name") && getIntent().hasExtra("Destination") && getIntent().hasExtra("Date")
                && getIntent().hasExtra("Description") && getIntent().hasExtra("Time") && getIntent().hasExtra("Clothing") && getIntent().hasExtra("Meeting")){
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
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
            Toast.makeText(Delete.this, "No data selected", Toast.LENGTH_SHORT).show();
        }
    }
}