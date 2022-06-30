package com.mexpense.m_expense;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.view.View;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class DBhelper extends SQLiteOpenHelper {

    //constants for creating the tables i.e table name , database and columns.

    public   Context context;
    private static final String DABASBENAME = "Expenses.dp";
    private static final int VERSION = 1;

    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_NAME = "TripName";
    private static final String COLUMN_DESTINATION = "TripDestination";
    private static final String COLUMN_DATE = "Date";
    private static final String COLUMN_RISK = "RiskMeasures";
    private static final String COLUMN_DESCRIPTION = "TripDesription";
    private static final String COLUMN_TIME = "Time";
    private static final String COLUMN_CLOTH = "Clothing";
    private static final String COLUMN_MEET = "MeetingPoint";
    private static final String TABLE1 = "USER";


    public DBhelper(Context context) {
        super(context, DABASBENAME, null, VERSION);
        this.context = context;
    }

    @Override
    //creating sqlite table to store trip details
    public void onCreate(SQLiteDatabase db) {
        //creating the table.
        String query = "CREATE TABLE " + TABLE1 +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " + COLUMN_DESTINATION + " TEXT, " + COLUMN_DATE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " + COLUMN_TIME + " TEXT, " + COLUMN_CLOTH + " TEXT, " +
                COLUMN_MEET + " TEXT ); ";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE1);

    }
   // insert details to Database table
    public void insertdaatabase(String Name, String Desc, String Date, String Descr, String Time, String Cloth, String meet){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, Name);
        contentValues.put(COLUMN_DESTINATION, Desc);
        contentValues.put(COLUMN_DATE, Date);
        contentValues.put(COLUMN_DESCRIPTION, Descr);
        contentValues.put(COLUMN_TIME, Time);
        contentValues.put(COLUMN_CLOTH, Cloth);
        contentValues.put(COLUMN_MEET, meet);
        long result = db.insert(TABLE1, null, contentValues);
        if (result  == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Addition successful!!!", Toast.LENGTH_SHORT).show();
        }
    }
    //method for updating the database
    public void updatedatabase(String id ,String Name, String Desc, String Date, String Descr, String Time, String Cloth, String meet){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, Name);
        contentValues.put(COLUMN_DESTINATION, Desc);
        contentValues.put(COLUMN_DATE, Date);
        contentValues.put(COLUMN_DESCRIPTION, Descr);
        contentValues.put(COLUMN_TIME, Time);
        contentValues.put(COLUMN_CLOTH, Cloth);
        contentValues.put(COLUMN_MEET, meet);
        Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
        long result = db.update(TABLE1, contentValues, " ID = ?", new String[] {id});
            if (result  == -1){
                Toast.makeText(context, "Not updated", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context, "Updated successfully", Toast.LENGTH_LONG).show();
            }

        }
//deletes a given culomn in the database
     public void deletedatabase(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE1, "ID = ?", new String[]{id});
        if (result == -1){
            Toast.makeText(context, "Fail to delete", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Data deleted", Toast.LENGTH_SHORT).show();
        }

    }
    // getting data from database table to display
    public Cursor getData(){
        String query = "SELECT * FROM " + TABLE1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
           cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
   // clearing data in the database
    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE1);
    }
   // searchimg for details from database
    public Cursor getSearch(String text){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE1 + " WHERE " + COLUMN_NAME + " LIKE '%" + text + "%'" + " OR " + COLUMN_DESTINATION + " LIKE '%" + text + "%'";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        return cursor;
    }
}
