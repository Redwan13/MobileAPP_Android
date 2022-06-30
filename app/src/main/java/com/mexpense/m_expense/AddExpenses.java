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

public class AddExpenses extends AppCompatActivity {

    //declaring objects

    Button button;
    EditText editText, editText1, editText2, editText3, editText4, editText5;
    DBexpense DB;
    TextView textView;
    TextInputLayout textInputLayout;
    String id, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses2);

        //objects used to access the ui.(user interface)
        editText = findViewById(R.id.user);
        editText1 = findViewById(R.id.password);
        editText2 = findViewById(R.id.timeex);
        editText3 = findViewById(R.id.comments);
        editText4 = findViewById(R.id.editid1);
        button = findViewById(R.id.login);
        //textView = findViewById(R.id.login);
        textInputLayout = findViewById(R.id.editname);
        getIntentData();
        //on click event for the button add expense.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            //Adding expenses to trip details and indicating required fields
            public void onClick(View v) {
                DB = new DBexpense(AddExpenses.this);
                String name = editText.getText().toString();
                String namedesc = editText1.getText().toString();
                String namedate = editText2.getText().toString();
                //checking user inputs before submission
                if(name.isEmpty() || namedesc.isEmpty() || namedate.isEmpty()){
                    editText.setError("Expense type required");
                    editText.requestFocus();
                    editText1.setError("Amount spent required");
                    editText1.requestFocus();
                    editText2.setError("Time spent required");
                    editText2.requestFocus();
                }

                else {
                    editText.setError(null);
                    editText.requestFocus();
                    editText1.setError(null);
                    editText1.requestFocus();
                    editText2.setError(null);
                    editText2.requestFocus();
                    //alerts user inputs for confirmation
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddExpenses.this);
                    builder.setTitle("Confirm the entities");
                    builder.setMessage("comfirm the data entered\n" +
                           "Type : " + editText.getText().toString().trim() + " \n " + "Amount : " + editText1.getText().toString().trim() + " \n " + "Time" + editText2.getText().toString().trim() +
                            " \n " + "Comment : " + editText3.getText().toString().trim() + " \n ");
                    builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DB.insertdaatabase(editText.getText().toString().trim(), editText1.getText().toString().trim(), editText2.getText().toString().trim(),
                                    editText3.getText().toString().trim(),editText4.getText().toString());
                            editText.setText(null);
                            editText1.setText(null);
                            editText2.setText(null);
                            editText3.setText(null);
                            Intent intent = new Intent(AddExpenses.this, Employee.class);
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
    //getting inputs strings from Expenses.
    public  void getIntentData(){
        if (getIntent().hasExtra("id") && getIntent().hasExtra("name")) {
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            Toast.makeText(this, "Add expenses "+ id +" . " + name , Toast.LENGTH_SHORT).show();
            editText4.setText(id);
        }
        else {
            Toast.makeText(AddExpenses.this, "No data selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddExpenses.this, Employee.class);
        startActivity(intent);
        super.onBackPressed();
    }
}