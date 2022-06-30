package com.mexpense.m_expense;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBexpense extends SQLiteOpenHelper {

    public Context context;
    //creating the database name i.e Expenses.dp
    private static final String DABASBENAME = "Expense.dp";
    private static final int VERSION = 1;
    //table columns.
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_ID1 = "PRIMARYKEY";
    private static final String COLUMN_EXPENSE = "Expense";
    private static final String COLUMN_AMOUNT = "Amount";
    private static final String COLUMN_TIME = "Time";
    private static final String COLUMN_ADD = "AdditionalInfo";
    //table name.
    private static final String TABLE = "Expense";

    public DBexpense(Context context) {
        super(context, DABASBENAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creating the table entities and columns
        String query = "CREATE TABLE " + TABLE +
                " (" + COLUMN_ID1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_ID + " INTEGER, "   +
                COLUMN_EXPENSE + " TEXT, " + COLUMN_AMOUNT + " TEXT, " + COLUMN_TIME + " TEXT, " +
                COLUMN_ADD + " TEXT ); ";
        db.execSQL(query);

    }
    //called when the table is on update.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE);
    }
    //method for inserting data to the databse.
    public void insertdaatabase(String id, String type, String Desc, String Date, String Descr){
        SQLiteDatabase db = this.getWritableDatabase();
        //contentvalues for inserting values(inputs) to database
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, id);
        contentValues.put(COLUMN_EXPENSE, type);
        contentValues.put(COLUMN_AMOUNT, Desc);
        contentValues.put(COLUMN_TIME, Date);
        contentValues.put(COLUMN_ADD, Descr);
        long result = db.insert(TABLE, null, contentValues);
        //checks input succeed and returns a toast on fail.
        if (result  == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        //return successful addition.
        else {
            Toast.makeText(context, "Addition successful!!!", Toast.LENGTH_SHORT).show();
        }
    }
    //returns all data from the database for display in the expense activty.
    public Cursor getData(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID1 + " = ? ", new String[]{id});
        }
        return cursor;
    }
}
