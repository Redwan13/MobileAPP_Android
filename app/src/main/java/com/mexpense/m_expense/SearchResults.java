package com.mexpense.m_expense;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;

public class SearchResults extends AppCompatActivity {

    //Activty which opens when the search results is called.
    //it uses the select * from table name where columnname like %text% query.
    //after checking it displays data from the database.
    RecyclerView recyclerView;
    DBhelper dBhelper;
    ArrayList<String> trip_id, trip_name, trip_desc, trip_date, trip_descr, trip_time, trip_cloth, trip_meet;
    SearchResults.CustomerAdapter customerAdapter;
    TextView textView;
    ImageView imageView;
    CharSequence charSequence;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        dBhelper = new DBhelper(SearchResults.this);
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
        getIntentData();
        textView = findViewById(R.id.nodata);
        imageView = findViewById(R.id.datashow);
        displayData();
        recyclerView = findViewById(R.id.recyle);
        customerAdapter = new SearchResults.CustomerAdapter(SearchResults.this, trip_id, trip_name, trip_desc, trip_date, trip_descr, trip_time, trip_cloth, trip_meet);
        recyclerView.setAdapter(customerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchResults.this));

    }
    //calls the method getSearch method in the dBhelper class.
    public  void displayData(){
        Cursor cursor = dBhelper.getSearch(name);
        if (cursor.getCount() == 0){
            textView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
            Toast.makeText(SearchResults.this, "No data in the database", Toast.LENGTH_SHORT).show();
        }
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

    public class CustomerAdapter extends RecyclerView.Adapter<SearchResults.CustomerAdapter.DataHolder> implements Filterable {

        Context context;
        ArrayList<String> tripid, tripname, tripdest, tripdate, tripdesc, triptime, tripcloth, tripmeet;
        ArrayList<String> tripname1;
        int position;

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
        public SearchResults.CustomerAdapter.DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.database_ror, parent, false);
            return new SearchResults.CustomerAdapter.DataHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchResults.CustomerAdapter.DataHolder holder, @SuppressLint("RecyclerView") int position) {
            this.position = position;
            holder.textid.setText(String.valueOf(tripid.get(position)));
            holder.textname.setText(String.valueOf(tripname.get(position)));
            holder.textdesc.setText(String.valueOf(tripdest.get(position)));
            holder.textdate.setText(String.valueOf(tripdate.get(position)));
            holder.textdescr.setText(String.valueOf(tripdesc.get(position)));
            holder.texttime.setText(String.valueOf(triptime.get(position)));
            holder.textcloth.setText(String.valueOf(tripcloth.get(position)));
            holder.textmeet.setText(String.valueOf(tripmeet.get(position)));
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Expenses.class);
                    intent.putExtra("id",String.valueOf(tripid.get(position)));
                    intent.putExtra("name", String.valueOf(tripname.get(position)));
                    startActivity(intent);

                }
            });
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
                                    Toast.makeText(SearchResults.this, "Show expenses", Toast.LENGTH_LONG).show();
                                    Intent intent1 = new Intent(SearchResults.this, Expenses.class);
                                    intent1.putExtra("id",String.valueOf(tripid.get(position)));
                                    intent1.putExtra("name", String.valueOf(tripname.get(position)));
                                    startActivity(intent1);
                                    break;
                                }
                                case R.id.delete:{
                                    Intent intent = new Intent(SearchResults.this, Delete.class);
                                    intent.putExtra("id",String.valueOf(tripid.get(position)));
                                    intent.putExtra("name", String.valueOf(tripname.get(position)));
                                    intent.putExtra("Destination", String.valueOf(tripdest.get(position)));
                                    intent.putExtra("Date", String.valueOf(tripdate.get(position)));
                                    intent.putExtra("Description", String.valueOf(tripdesc.get(position)));
                                    intent.putExtra("Time", String.valueOf(triptime.get(position)));
                                    intent.putExtra("Clothing", String.valueOf(tripcloth.get(position)));
                                    intent.putExtra("Meeting", String.valueOf(tripmeet.get(position)));
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

    //menu to diplayed in this activity.

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Do you want to delete all ?");
            builder.setMessage("Are you sure you want to delete all ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DBhelper db = new DBhelper(SearchResults.this);
                    db.deleteAll();
                    Intent intent = new  Intent(SearchResults.this, SearchResults.class);
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
                DBhelper dBhelper = new DBhelper(SearchResults.this);
                dBhelper.getSearch(query);
                Intent intent = new Intent(SearchResults.this, SearchResults.class);
                startActivity(intent);
                finish();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //customerAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }
    public  void getIntentData(){
        if (getIntent().hasExtra("id") && getIntent().hasExtra("name")) {
            name = getIntent().getStringExtra("name");

        }
        else {
            Toast.makeText(SearchResults.this, "No data selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}