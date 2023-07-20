package com.kiran.weighttracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kiran.weighttracker.ProfileModel;

import java.util.ArrayList;
import java.util.List;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "my_database.db";
    private static final int DATABASE_VERSION = 1;
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    public MySQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the table
        db.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, password TEXT, name TEXT, age TEXT, mobile TEXT, targetWeight TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the old table
        db.execSQL("DROP TABLE IF EXISTS users");

        // Create the new table
        onCreate(db);
    }

    public void saveUser(String name, String age,String email, String password, String mobile, String targetWeight) {
        // Get the writable database
        SQLiteDatabase db = getWritableDatabase();

        // Create a ContentValues object to store the data
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("age", age);
        values.put("email", email);
        values.put("password", password);
        values.put("mobile", mobile);
        values.put("targetWeight", targetWeight);


        // Insert the data into the database
        db.insert("users", null, values);

        // Close the database
        db.close();
    }

    public boolean getUser(String email, String password) {
        // Get the readable database
        SQLiteDatabase db = getReadableDatabase();

        // Create a cursor to query the users table
        Cursor cursor = db.query("users", null, "email = ? AND password = ?", new String[]{email, password}, null, null, null);

        // Check if the cursor has any rows
        if (cursor.getCount() == 0) {
            return false;
        } else {
            // The user exists, so return true
            return true;
        }
    }

    public boolean isUserExist(String email){
        SQLiteDatabase db = getReadableDatabase();

        // Create a cursor to query the users table
        Cursor cursor = db.query("users", null, "email = ?", new String[]{email}, null, null, null);

        // Check if the cursor has any rows
        if (cursor.getCount() == 0) {
            return false;
        } else {
            // The user exists, so return true
            return true;
        }
    }

    public List<ProfileModel> getUsersList(String email, String password) {
        // Get the readable database
        SQLiteDatabase db = getReadableDatabase();

        // Create a cursor to query the users table
        Cursor cursor = db.query("users", null, "email = ? AND password = ?", new String[]{email, password}, null, null, null);

        List<ProfileModel> item_data = new ArrayList<>();
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    ProfileModel obj = new ProfileModel();
                   /* obj.email = cursor.getString(cursor.getColumnIndex("email"));
                    obj.password = cursor.getString(cursor.getColumnIndex("password"));*/
                    item_data.add(obj);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return item_data;
        // Check if the cursor has any rows
    }

    public void updateUser(String email, String password) {
        // Get the writable database
        SQLiteDatabase db = getWritableDatabase();

        // Create a ContentValues object to store the data
        ContentValues values = new ContentValues();
        values.put("password", password);

        // Update the data in the database
        db.update("users", values, "email = ?", new String[]{email});

        // Close the database
        db.close();
    }

    public void deleteUser(String email) {
        // Get the writable database
        SQLiteDatabase db = getWritableDatabase();

        // Delete the data from the database
        db.delete("users", "email = ?", new String[]{email});

        // Close the database
        db.close();
    }
}

