package com.example.com594cw12020;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LoginDataBaseAdapter {
    //Database Version
    private static final int DATABASE_VERSION = 4;
    //Database Name
    private static final String DATABASE_NAME = "Details.db";
    //Variable to hold the database instance
    private static SQLiteDatabase db;
    //Database open/upgrade helper
    private  DataBaseHelper dataBaseHelper;

    //Constructor
    public  LoginDataBaseAdapter(Context context){
        dataBaseHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //Method to open the Database
    public LoginDataBaseAdapter open() throws SQLException{
        db = dataBaseHelper.getWritableDatabase();
        return this;
    }
    //Method to close the Database
    public void close(){
        db.close();
    }
    //Method returns an Instance of the Database
    public  SQLiteDatabase getDatabaseInstance(){
        return db;
    }
    //Insert username, password and email into the table
    public void insertEntry(String un, String pw, String em, String gen){
        try{
            ContentValues newValues = new ContentValues();
            //Assign values for each row
            newValues.put("userName", un);
            newValues.put("userPassword", pw);
            newValues.put("userEmail", em);
            newValues.put("userGender", gen);
            //Insert the row into your table
            db.insert("User", null, newValues);
            Log.d("checking", "Inserted");
        }
        catch (Exception ex){
        Log.e("Error", "error login");
        }
    }
    //Method to get the password of username
    public String getSingleEntry(String un){
        db = dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.query("User", null, "userName=?",
                new String[]{un}, null, null, null);
        if(cursor.getCount() < 1) //Username Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String getPassword = cursor.getString(cursor.getColumnIndex("userPassword"));
        cursor.close();
        return getPassword;
    }

    public Cursor fetch() {
        Cursor cursor = this.db.query();
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
}
