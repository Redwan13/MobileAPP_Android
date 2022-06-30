package com.mexpense.m_expense;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ImageDatabase extends SQLiteOpenHelper {

    public   Context context;
    private static final String DABASBENAME = "Images.dp";
    private static final int VERSION = 1;
    ContentResolver contentResolver;
    SQLiteDatabase db;

    public static final String COLUMN_Image = "ImageName";
    public static final String COLUMN_ID = "ID";
    public static final String TABLE = "ImageTable";

    public ImageDatabase(Context context) {
        super(context, DABASBENAME, null, VERSION);
        this.context = context;
        contentResolver = context.getContentResolver();
        db = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_Image + " BLOB NOT NULL ); ";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE);
    }
    public  void addDatabase(byte[] image){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_Image, image);
        db.insert(TABLE, null, contentValues);
    }
    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE);
    }
    public void deletedatabase(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE, "ID = ?", new String[]{String.valueOf(id)});
        if (result == -1){
            Toast.makeText(context, "Fail to delete", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Data deleted", Toast.LENGTH_SHORT).show();
        }

    }
}
