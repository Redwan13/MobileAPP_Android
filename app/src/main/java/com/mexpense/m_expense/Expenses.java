package com.mexpense.m_expense;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Expenses extends AppCompatActivity {

    RecyclerView recyclerView;
    DBexpense dBhelper;
    ArrayList<String> trip_id, trip_name, trip_desc, trip_date, trip_descr;
    Expenses.CustomerAdapter customerAdapter;
    TextView textView;
    ImageView imageView;

    public String id, name1, destination, date, description, time1, clothing, meeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenses);

        dBhelper = new DBexpense(Expenses.this);
        trip_id = new ArrayList<>();
        trip_name = new ArrayList<>();
        trip_desc = new ArrayList<>();
        trip_date = new ArrayList<>();
        trip_descr = new ArrayList<>();

        textView = findViewById(R.id.nodata);
        imageView = findViewById(R.id.datashow);
        getIntentData();

        displayData();
        recyclerView = findViewById(R.id.recyle);
        customerAdapter = new Expenses.CustomerAdapter(Expenses.this, trip_id, trip_name, trip_desc, trip_date, trip_descr);
        recyclerView.setAdapter(customerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Expenses.this));

    }
    //method for displaying the data from the expense database and displaying the recyclerview container.
    public  void displayData(){
        Cursor cursor = dBhelper.getData(id);
        if (cursor.getCount() == 0){
            Toast.makeText(Expenses.this, "No data in the database", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Expenses.this, AddExpenses.class);
            startActivity(intent);
        }
        else{
            while(cursor.moveToNext()){
                trip_id.add(cursor.getString(0));
                trip_name.add(cursor.getString(1));
                trip_desc.add(cursor.getString(2));
                trip_date.add(cursor.getString(3));
                trip_descr.add(cursor.getString(4));

            }
        }
    }
    //adapter for holding and displaying data in the recyclerview container.
    public class CustomerAdapter extends RecyclerView.Adapter<Expenses.CustomerAdapter.DataHolder>{

        Context context;
        ArrayList tripid, tripexpense, tripamount, triptime, tripadd;
        int position;

        public CustomerAdapter(Context context, ArrayList tripid, ArrayList tripexpense, ArrayList tripamount, ArrayList triptime, ArrayList tripadd) {
            this.context = context;
            this.tripid = tripid;
            this.tripexpense = tripexpense;
            this.tripamount = tripamount;
            this.triptime = triptime;
            this.tripadd = tripadd;
        }




        @NonNull
        @Override
        public Expenses.CustomerAdapter.DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.expenses_row, parent, false);
            return new Expenses.CustomerAdapter.DataHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull Expenses.CustomerAdapter.DataHolder holder, @SuppressLint("RecyclerView") int position) {
            this.position = position;
            holder.textid.setText(String.valueOf(tripid.get(position)));
            holder.textname.setText(String.valueOf(tripexpense.get(position)));
            holder.textdesc.setText(String.valueOf(tripamount.get(position)));
            holder.textdate.setText(String.valueOf(triptime.get(position)));
            holder.textdescr.setText(String.valueOf(tripadd.get(position)));
            holder.textname1.setText("Type of expense :");
            holder.textdesc1.setText("Amount of expense in $:");
            holder.textdate1.setText("Time of expense :");
            holder.textdescr1.setText("Comments :");
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Expenses.this, Upload.class);
                    intent.putExtra("name", String.valueOf(tripexpense.get(position)));
                    intent.putExtra("amount",String.valueOf(tripamount.get(position)));
                    intent.putExtra("time", String.valueOf(triptime.get(position)));
                    intent.putExtra("comment", String.valueOf(tripadd.get(position)));
                    intent.putExtra("name1", name1);
                    intent.putExtra("dest", destination);
                    intent.putExtra("date", date);
                    intent.putExtra("desc", description);
                    intent.putExtra("time1", time1);
                    intent.putExtra("clothing", clothing);
                    intent.putExtra("meeting", meeting);
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return tripid.size();
        }
        public  class DataHolder extends RecyclerView.ViewHolder{

            TextView textid, textname, textdesc, textdate, textdescr, textname1, textdesc1, textdate1, textdescr1;
            CardView cardView;

            public DataHolder(View view) {
                super(view);
                textid = view.findViewById(R.id.tripid);
                textname = view.findViewById(R.id.tripname);
                textdesc = view.findViewById(R.id.destination);
                textdate = view.findViewById(R.id.date);
                textdescr = view.findViewById(R.id.description);
                textname1 = view.findViewById(R.id.tripname1);
                textdesc1 = view.findViewById(R.id.destination1);
                textdate1 = view.findViewById(R.id.date1);
                textdescr1 = view.findViewById(R.id.description1);
                cardView = view.findViewById(R.id.card);

            }
        }
    }
    public  void getIntentData(){
        if (getIntent().hasExtra("id") && getIntent().hasExtra("name1") &&
                getIntent().hasExtra("Destination") && getIntent().hasExtra("Date") &&
                getIntent().hasExtra("Description") && getIntent().hasExtra("Time") && getIntent().hasExtra("Clothing") && getIntent().hasExtra("Meeting")){
            id = getIntent().getStringExtra("id");
            name1 = getIntent().getStringExtra("name1");
            destination = getIntent().getStringExtra("Destination");
            date = getIntent().getStringExtra("Date");
            description = getIntent().getStringExtra("Description");
            time1 = getIntent().getStringExtra("Time");
            clothing = getIntent().getStringExtra("Clothing");
            meeting = getIntent().getStringExtra("Meeting");
            Toast.makeText(Expenses.this, destination + clothing + meeting, Toast.LENGTH_SHORT).show();


        }
        else {
            Toast.makeText(Expenses.this, "No data selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Expenses.this, Employee.class);
        startActivity(intent);
        super.onBackPressed();
    }
}