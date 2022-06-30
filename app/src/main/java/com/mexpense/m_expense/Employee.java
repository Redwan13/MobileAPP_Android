package com.mexpense.m_expense;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.cardview.widget.CardView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import com.mexpense.m_expense.Update;

public class Employee extends AppCompatActivity {

    RecyclerView recyclerView;
    DBhelper dBhelper;
    ArrayList<String> trip_id, trip_name, trip_desc, trip_date, trip_descr, trip_time, trip_cloth, trip_meet;
    CustomerAdapter customerAdapter;
    TextView textView;
    ImageView imageView;
    CharSequence charSequence;

    public String id, name, destination, date, description, time, clothing, meeting;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //creating an object of class dBhelper.

        dBhelper = new DBhelper(Employee.this);
        //creating arraylist fields
        trip_id = new ArrayList<>();
        trip_name = new ArrayList<>();
        trip_desc = new ArrayList<>();
        trip_date = new ArrayList<>();
        trip_descr = new ArrayList<>();
        trip_time = new ArrayList<>();
        trip_cloth = new ArrayList<>();
        trip_meet = new ArrayList<>();
        charSequence = new CharSequence() {
            @Override
            public int length() {
                return 0;
            }

            @Override
            public char charAt(int index) {
                return 0;
            }

            @NonNull
            @Override
            public CharSequence subSequence(int start, int end) {
                return null;
            }
        };

        textView = findViewById(R.id.nodata);
        imageView = findViewById(R.id.datashow);
        displayData();
        recyclerView = findViewById(R.id.recyle);
        customerAdapter = new CustomerAdapter(Employee.this, trip_id, trip_name, trip_desc, trip_date, trip_descr, trip_time, trip_cloth, trip_meet);
        recyclerView.setAdapter(customerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Employee.this));

    }
    //method for displaying data to recycler view and assessing data from the database.
    public  void displayData(){
        Cursor cursor = dBhelper.getData();
        if (cursor.getCount() == 0){
            textView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
            Toast.makeText(Employee.this, "No data in the database", Toast.LENGTH_SHORT).show();
        }
        //checks if the data exists in the databas thus displays it.
        else{
            while(cursor.moveToNext()){
               trip_id.add(cursor.getString(0));
                trip_name.add(cursor.getString(1));
                trip_desc.add(cursor.getString(2));
                trip_date.add(cursor.getString(3));
                trip_descr.add(cursor.getString(4));
                trip_time.add(cursor.getString(5));
                trip_cloth.add(cursor.getString(6));
                trip_meet.add(cursor.getString(7));

            }
        }
    }

    //Adapter for displaying the data from the database.
    //extends recyclerview adapter for displaying data using recycler adapter.
    //class dataholder holds the data form the custom adapter.

    public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.DataHolder> implements Filterable{

        Context context;
        ArrayList<String> tripid, tripname, tripdest, tripdate, tripdesc, triptime, tripcloth, tripmeet;
        ArrayList<String> tripname1;
        int position;

        //custamerAdapter constructor for accesiing arrays.

        public CustomerAdapter(Context context, ArrayList tripid, ArrayList tripname, ArrayList tripdest, ArrayList tripdate, ArrayList tripdesc, ArrayList triptime, ArrayList tripcloth, ArrayList tripmeet) {
            this.context = context;
            this.tripid = tripid;
            this.tripname = tripname;
            tripname1 = new ArrayList<>(tripname);
            this.tripdest = tripdest;
            this.tripdate = tripdate;
            this.tripdesc = tripdesc;
            this.triptime = triptime;
            this.tripcloth = tripcloth;
            this.tripmeet = tripmeet;
        }


        @NonNull
        @Override
        public CustomerAdapter.DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.database_ror, parent, false);
            return new DataHolder(view);
        }

        //onbindviewholder returns the data from the database for display.
        @Override
        public void onBindViewHolder(@NonNull CustomerAdapter.DataHolder holder, @SuppressLint("RecyclerView") int position) {
            this.position = position;
            holder.textid.setText(String.valueOf(tripid.get(position)));
            holder.textname.setText(String.valueOf(tripname.get(position)));
            holder.textdesc.setText(String.valueOf(tripdest.get(position)));
            holder.textdate.setText(String.valueOf(tripdate.get(position)));
            holder.textdescr.setText(String.valueOf(tripdesc.get(position)));
            holder.texttime.setText(String.valueOf(triptime.get(position)));
            holder.textcloth.setText(String.valueOf(tripcloth.get(position)));
            holder.textmeet.setText(String.valueOf(tripmeet.get(position)));
            //cardview when clicked opens the Expenses activity.
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Expenses.class);
                    intent.putExtra("id",String.valueOf(tripid.get(position)));
                    intent.putExtra("name1", String.valueOf(tripname.get(position)));
                    intent.putExtra("Destination", String.valueOf(tripdest.get(position)));
                    intent.putExtra("Date", String.valueOf(tripdate.get(position)));
                    intent.putExtra("Description", String.valueOf(tripdesc.get(position)));
                    intent.putExtra("Time", String.valueOf(triptime.get(position)));
                    intent.putExtra("Clothing", String.valueOf(tripcloth.get(position)));
                    intent.putExtra("Meeting", String.valueOf(tripmeet.get(position)));
                    startActivity(intent);

                }
            });
            //popupmenu displays the pop_menu in menu folder under resource directory which has view expense and delete.
            //view expenses displays expenses from a given column
            //delete, deletes the data in the column.

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(context, v);
                    popupMenu.getMenuInflater().inflate(R.menu.pop_menu, popupMenu.getMenu());
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @SuppressLint("NonConstantResourceId")
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.expenseview:{
                                    Toast.makeText(Employee.this, "Show expenses", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(Employee.this, Expenses.class);
                                    intent.putExtra("id",String.valueOf(tripid.get(position)));
                                    intent.putExtra("name1", String.valueOf(tripname.get(position)));
                                    intent.putExtra("Destination", String.valueOf(tripdest.get(position)));
                                    intent.putExtra("Date", String.valueOf(tripdate.get(position)));
                                    intent.putExtra("Description", String.valueOf(tripdesc.get(position)));
                                    intent.putExtra("Time", String.valueOf(triptime.get(position)));
                                    intent.putExtra("Clothing", String.valueOf(tripcloth.get(position)));
                                    intent.putExtra("Meeting", String.valueOf(tripmeet.get(position)));
                                    startActivity(intent);
                                    break;
                                }
                                case R.id.delete:{
                                    Intent intent = new Intent(Employee.this, Delete.class);
                                    intent.putExtra("id",String.valueOf(tripid.get(position)));
                                    intent.putExtra("name", String.valueOf(tripname.get(position)));
                                    intent.putExtra("Destination", String.valueOf(tripdest.get(position)));
                                    intent.putExtra("Date", String.valueOf(tripdate.get(position)));
                                    intent.putExtra("Description", String.valueOf(tripdesc.get(position)));
                                    intent.putExtra("Time", String.valueOf(triptime.get(position)));
                                    intent.putExtra("Clothing", String.valueOf(tripcloth.get(position)));
                                    intent.putExtra("Meeting", String.valueOf(tripmeet.get(position)));
                                    Toast.makeText(Employee.this, id, Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                    break;
                                }
                            }
                            return true;
                        }
                    });

                }
            });

        }

        //getItemCount returns the total number of items in the database.

        @Override
        public int getItemCount() {
            return tripid.size();
        }

        @Override
        public Filter getFilter() {
            return filtered;
        }
        Filter filtered = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<String> filtered = new ArrayList<>();
                if (charSequence.toString().isEmpty()){
                    filtered.addAll(tripname1);
                }
                else {
                    for (String trip : tripname1){
                        if (trip.toLowerCase().contains(charSequence.toString().toLowerCase())){
                            tripname1.add(trip);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filtered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                tripname.clear();
                tripname.addAll((Collection<? extends String>) results.values);
                notifyDataSetChanged();

            }
        };

        //dataHolder class access the user interface database_ror in resource layout.
        //it access ids in the ui.
        //extends RecyclerView .ViewHolder class.

        public  class DataHolder extends RecyclerView.ViewHolder{

            TextView textid, textname, textdesc, textdate, textdescr, texttime, textcloth, textmeet;
            CardView cardView;
            ImageView imageView;

            public DataHolder(View view) {
                super(view);
                textid = view.findViewById(R.id.tripid);
                textname = view.findViewById(R.id.tripname);
                textdesc = view.findViewById(R.id.destination);
                textdate = view.findViewById(R.id.date);
                textdescr = view.findViewById(R.id.description);
                texttime = view.findViewById(R.id.time);
                textcloth = view.findViewById(R.id.cloth);
                textmeet = view.findViewById(R.id.meet);
                cardView = view.findViewById(R.id.card);
                imageView = view.findViewById(R.id.buttonmore);

            }
        }
    }
    //access the menu in the menu activity.
    //delete_all clears the datbase by calling the delteall method in the dbhelper class.
    //it alerts before deletion is done using the alertDialog class.

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Do you want to delete all ?");
            builder.setMessage("Are you sure you want to delete all ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DBhelper db = new DBhelper(Employee.this);
                    db.deleteAll();
                    Intent intent = new  Intent(Employee.this, Employee.class);
                    startActivity(intent);
                    finish();

                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();


        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.delete_manu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint("Search data..");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(Employee.this, SearchResults.class);
                intent.putExtra("name", query);
                startActivity(intent);
                Toast.makeText(Employee.this, text, Toast.LENGTH_LONG).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //customerAdapter.getFilter().filter(newText);
                text = newText;
               return false;
            }
        });

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}