package com.example.rottenfridge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.Date;
import java.text.SimpleDateFormat;

class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME="RottenFridgeDB.db";
    private static final int DATABASE_VERSION=1;

    private static final String TABLE_NAME="myFridge";
    private static final String COLUMN_ID="_id";
    private static final String COLUMN_PRODUCTNAME="product_name";
    private static final String COLUMN_EXPIRATION="product_expiration";
    private static final String COLUMN_QUANTITY="product_quantity";



    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_PRODUCTNAME + " TEXT, " +
                        COLUMN_EXPIRATION + " TEXT, " +
                        COLUMN_QUANTITY + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addProduct(String name, String expirationDate, int quantity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_PRODUCTNAME, name);
        cv.put(COLUMN_EXPIRATION, expirationDate);
        cv.put(COLUMN_QUANTITY, quantity);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result==-1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db!=null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String row_id, String name, String expiration, String quantity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PRODUCTNAME, name);
        cv.put(COLUMN_EXPIRATION, expiration);
        cv.put(COLUMN_QUANTITY, quantity);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if (result==-1){
            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();

        }
    }
}
