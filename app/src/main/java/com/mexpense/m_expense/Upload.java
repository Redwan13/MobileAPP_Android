package com.mexpense.m_expense;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Upload extends AppCompatActivity {

    //This activity is used for uploading the data in the database.
    //volley dependecy is used for data upload since is lightweight and easier to use.
    //it fast checks the connection exists if not it returns an error with the method Response.ErrorListenser,
    //if the connection exists the it fecths the data from the user edittext fields and change it to strings before uploading.
    //it then acccess the the class myclass for Queuing so that it allow easier upload of the next data.

    String id, name, amount, time, comment;
    EditText editText, editText1, editText2, editText3, editText4;
    Button button;
    public static final String server_url = "";//type the url to server is to be uploaded to.
    AlertDialog.Builder builder;
    Tag error;
    EditText editText11, editText111, editText21, editText31, editText41, editText51, editText61;
    public String id1, name1, destination, date, description, time1, clothing, meeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        editText = findViewById(R.id.user);
        editText1 = findViewById(R.id.password);
        editText2 = findViewById(R.id.timeex);
        editText3 = findViewById(R.id.comments);
        editText4 = findViewById(R.id.editid1);
        editText11 = findViewById(R.id.editTextTextPersonName);
        editText111 = findViewById(R.id.editTextDest);
        editText21= findViewById(R.id.editTextDate);
        editText31 = findViewById(R.id.editTextDescription);
        editText41 = findViewById(R.id.editTime);
        editText51 = findViewById(R.id.editClothing);
        editText61 = findViewById(R.id.editMeeting);
        getIntentData();
        button = findViewById(R.id.login);
        builder = new AlertDialog.Builder(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String expense = editText.getText().toString();
                String amount = editText2.getText().toString();
                String time = editText3.getText().toString();
                String comment =editText4.getText().toString();
                String trip = editText11.getText().toString();
                String destination = editText111.getText().toString();
                String date = editText21.getText().toString();
                String description =editText31.getText().toString();
                String time1 = editText41.getText().toString();
                String cloth = editText51.getText().toString();
                String meet = editText61.getText().toString();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                builder.setTitle("Server Response");
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        editText.setText(expense);
                                        editText1.setText(amount);
                                        editText2.setText(time);
                                        editText3.setText(comment);

                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(String.valueOf(error), "Error in connection");
                        Toast.makeText(Upload.this, "Connection error", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();

                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("expense", expense);
                        params.put("amount", amount);
                        params.put("time", time);
                        params.put("comment", comment);
                        params.put("trip", trip);
                        params.put("destination", destination);
                        params.put("date", date);
                        params.put("description", description);
                        params.put("time1", time1);
                        params.put("cloth", cloth);
                        params.put("meet", meet);
                        return params;
                    }
                };
                MyClass myClass = new MyClass(Upload.this);
                myClass.getInstance(Upload.this).addDatabase(stringRequest);

            }
        });

    }
    public  void getIntentData(){
        if (getIntent().hasExtra("name") && getIntent().hasExtra("amount") && getIntent().hasExtra("time")
        && getIntent().hasExtra("comment") && getIntent().hasExtra("name1") && getIntent().hasExtra("dest") && getIntent().hasExtra("date")
                && getIntent().hasExtra("desc") && getIntent().hasExtra("time1") && getIntent().hasExtra("clothing") && getIntent().hasExtra("meeting")) {
            name = getIntent().getStringExtra("name");
            amount = getIntent().getStringExtra("amount");
            time = getIntent().getStringExtra("time");
            comment = getIntent().getStringExtra("comment");
            name1 = getIntent().getStringExtra("name1");
            destination = getIntent().getStringExtra("dest");
            date = getIntent().getStringExtra("date");
            description = getIntent().getStringExtra("desc");
            time1 = getIntent().getStringExtra("time1");
            clothing = getIntent().getStringExtra("clothing");
            meeting = getIntent().getStringExtra("meeting");


            editText.setText(name);
            editText1.setText(amount);
            editText2.setText(time);
            editText3.setText(comment);
            editText11.setText(name1);
            editText111.setText(destination);
            editText21.setText(date);
            editText31.setText(description);
            editText41.setText(time1);
            editText51.setText(clothing);
            editText61.setText(meeting);


        }
        else {
            Toast.makeText(Upload.this, "No data selected", Toast.LENGTH_SHORT).show();
        }
    }
    public class MyClass{
        private  MyClass minstance;
        public RequestQueue requestQueue;
        private  Context context;

        public MyClass(Context context) {
            this.context = context;
            requestQueue = getRequestQueue();
        }
        public   synchronized MyClass getInstance(Context context){
            if (minstance == null){
                minstance = new MyClass(context);
            }
            return minstance;
        }

        private RequestQueue getRequestQueue() {
            if (requestQueue == null){
                requestQueue = Volley.newRequestQueue(context.getApplicationContext());
            }
            return requestQueue;

        }
        public <T>void addDatabase(Request<T> request){
            requestQueue.add(request);
        }
    }

}