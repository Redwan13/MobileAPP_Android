package com.mexpense.m_expense;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    DBhelper dBhelper;
    ArrayList<String> trip_id, trip_name, trip_desc, trip_date, trip_descr, trip_time, trip_cloth, trip_meet;
    HomeFragment.CustomerAdapter customerAdapter;
    TextView textView;
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dBhelper = new DBhelper(getContext());
        trip_id = new ArrayList<>();
        trip_name = new ArrayList<>();
        trip_desc = new ArrayList<>();
        trip_date = new ArrayList<>();
        trip_descr = new ArrayList<>();
        trip_time = new ArrayList<>();
        trip_cloth = new ArrayList<>();
        trip_meet = new ArrayList<>();

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        textView = view.findViewById(R.id.nodata);
        imageView = view.findViewById(R.id.datashow);

        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddDatabase.class);
                startActivity(intent);
            }
        });

        displayData();
        recyclerView = view.findViewById(R.id.recyle);
        customerAdapter = new HomeFragment.CustomerAdapter(getActivity(), getContext(), trip_id, trip_name, trip_desc, trip_date, trip_descr, trip_time, trip_cloth, trip_meet);
        recyclerView.setAdapter(customerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            getActivity().recreate();
        }
    }

    public  void displayData(){
        Cursor cursor = dBhelper.getData();
        if (cursor.getCount() == 0){
            textView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "No data in the database", Toast.LENGTH_SHORT).show();
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
    public class CustomerAdapter extends RecyclerView.Adapter<HomeFragment.CustomerAdapter.DataHolder>{

        Context context;
        Activity activity;
        ArrayList tripid, tripname, tripdest, tripdate, tripdesc, triptime, tripcloth, tripmeet;
        int position;

        public CustomerAdapter(Activity activity, Context context, ArrayList tripid, ArrayList tripname, ArrayList tripdest, ArrayList tripdate, ArrayList tripdesc, ArrayList triptime, ArrayList tripcloth, ArrayList tripmeet) {
            this.context = context;
            this.activity = activity;
            this.tripid = tripid;
            this.tripname = tripname;
            this.tripdest = tripdest;
            this.tripdate = tripdate;
            this.tripdesc = tripdesc;
            this.triptime = triptime;
            this.tripcloth = tripcloth;
            this.tripmeet = tripmeet;
        }


        @NonNull
        @Override
        public HomeFragment.CustomerAdapter.DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.database_ror, parent, false);
            return new HomeFragment.CustomerAdapter.DataHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HomeFragment.CustomerAdapter.DataHolder holder, @SuppressLint("RecyclerView") int position) {
            this.position = position;
            holder.textid.setText(String.valueOf(tripid.get(position)));
            holder.textname.setText(String.valueOf(tripname.get(position)));
            holder.textdesc.setText(String.valueOf(tripdest.get(position)));
            holder.textdate.setText(String.valueOf(tripdate.get(position)));
            holder.textdescr.setText(String.valueOf(tripdesc.get(position)));
            holder.texttime.setText(String.valueOf(triptime.get(position)));
            holder.textcloth.setText(String.valueOf(tripcloth.get(position)));
            holder.textmeet.setText(String.valueOf(tripmeet.get(position)));
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(context, v);
                    popupMenu.getMenuInflater().inflate(R.menu.pop_edit, popupMenu.getMenu());
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @SuppressLint("NonConstantResourceId")
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getItemId() == R.id.edit) {
                                Toast.makeText(getContext(), "Update the datebase", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getActivity(), Update.class);
                                intent.putExtra("id",String.valueOf(tripid.get(position)));
                                intent.putExtra("name", String.valueOf(tripname.get(position)));
                                intent.putExtra("Destination", String.valueOf(tripdest.get(position)));
                                intent.putExtra("Date", String.valueOf(tripdate.get(position)));
                                intent.putExtra("Description", String.valueOf(tripdesc.get(position)));
                                intent.putExtra("Time", String.valueOf(triptime.get(position)));
                                intent.putExtra("Clothing", String.valueOf(tripcloth.get(position)));
                                intent.putExtra("Meeting", String.valueOf(tripmeet.get(position)));
                                activity.startActivityForResult(intent, 1);
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
}