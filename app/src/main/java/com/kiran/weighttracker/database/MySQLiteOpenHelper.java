package com.kiran.weighttracker.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kiran.weighttracker.ProfileModel;
import com.kiran.weighttracker.modals.CommonModal;
import com.kiran.weighttracker.modals.TargetModal;

import java.util.ArrayList;
import java.util.List;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "my_database.db";
    private static final int DATABASE_VERSION = 1;

    public MySQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the table
        db.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, password TEXT, name TEXT, age TEXT, mobile TEXT, targetWeight TEXT)");
        db.execSQL("CREATE TABLE targetDetails (id INTEGER PRIMARY KEY AUTOINCREMENT, time TEXT, date TEXT, weight TEXT, calories TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the old table
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS targetDetails");

        // Create the new table
        onCreate(db);
    }

    public void saveUser(String name, String age, String email, String password, String mobile, String targetWeight) {
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

    public void saveTargetDetails(String time, String date, String weight, String calories) {
        // Get the writable database
        SQLiteDatabase db = getWritableDatabase();

        // Create a ContentValues object to store the data
        ContentValues values = new ContentValues();
        values.put("time", time);
        values.put("date", date);
        values.put("weight", weight);
        values.put("calories", calories);

        // Insert the data into the database
        db.insert("targetDetails", null, values);
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

    @SuppressLint("Range")
    public ProfileModel getUserDetails() {
        ProfileModel obj = new ProfileModel();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + "users", null);

        // Check if there is a row in the result set
        if (cursor.moveToFirst()) {
            obj.email = cursor.getString(cursor.getColumnIndex("email"));
            obj.password = cursor.getString(cursor.getColumnIndex("password"));
            obj.name = cursor.getString(cursor.getColumnIndex("name"));
            obj.age = cursor.getString(cursor.getColumnIndex("age"));
            obj.mobile = cursor.getString(cursor.getColumnIndex("mobile"));
            obj.targetWeight = cursor.getString(cursor.getColumnIndex("targetWeight"));
        }

        return obj;
    }

    @SuppressLint("Range")
    public TargetModal getUserTargetDetails() {
        TargetModal obj = new TargetModal();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + "targetDetails", null);

        // Check if there is a row in the result set
       /* if (cursor.moveToFirst()) {
            obj.time = cursor.getString(cursor.getColumnIndex("time"));
            obj.date = cursor.getString(cursor.getColumnIndex("date"));
            obj.weight = cursor.getString(cursor.getColumnIndex("weight"));
            obj.Calories = cursor.getString(cursor.getColumnIndex("calories"));
        }*/

        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    obj.time = cursor.getString(cursor.getColumnIndex("time"));
                    obj.date = cursor.getString(cursor.getColumnIndex("date"));
                    obj.weight = cursor.getString(cursor.getColumnIndex("weight"));
                    obj.Calories = cursor.getString(cursor.getColumnIndex("calories"));
                } while (cursor.moveToNext());
            }
        }
        return obj;
    }

    public boolean isUserExist(String email) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = ?", new String[]{email});

        // Check if there is a row in the result set
        if (cursor.moveToFirst()) {

            ProfileModel obj = new ProfileModel();
            obj.email = cursor.getString(cursor.getColumnIndex("email"));
            obj.password = cursor.getString(cursor.getColumnIndex("password"));

        }

        //   SQLiteDatabase db = getReadableDatabase();

        // Create a cursor to query the users table
//        Cursor cursor = db.query("users", null, "email = ?", new String[]{email}, null, null, null);
        //  Cursor cursor = db.rawQuery("SELECT value FROM users GROUP BY email = ?", new String[]{email});
        // Check if the cursor has any rows
        if (cursor.getCount() == 0) {
            cursor.close();
            return false;
        } else {
            cursor.close();
            // The user exists, so return true
            return true;
        }
    }

    @SuppressLint("Range")
    public List<ProfileModel> getUsersList() {
        // Get the readable database
      /*  SQLiteDatabase db = getReadableDatabase();

        // Create a cursor to query the users table
        Cursor cursor = db.query("users", null, "email = ? AND password = ? AND name=? AND age=? AND mobile=? AND targetWeight =?", new String[]{email, password, name, age, mobile, targetWeight}, null, null, null);
*/
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + "targetDetails", null);
        List<ProfileModel> item_data = new ArrayList<>();
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    ProfileModel obj = new ProfileModel();
                    obj.email = cursor.getString(cursor.getColumnIndex("email"));
                    obj.password = cursor.getString(cursor.getColumnIndex("password"));
                    obj.name = cursor.getString(cursor.getColumnIndex("name"));
                    obj.age = cursor.getString(cursor.getColumnIndex("age"));
                    obj.mobile = cursor.getString(cursor.getColumnIndex("mobile"));
                    obj.targetWeight = cursor.getString(cursor.getColumnIndex("targetWeight"));
                    item_data.add(obj);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return item_data;
        // Check if the cursor has any rows
    }

    public List<TargetModal> getUsersTargetList() {
        // Get the readable database
      /*  SQLiteDatabase db = getReadableDatabase();

        // Create a cursor to query the users table
        Cursor cursor = db.query("users", null, "email = ? AND password = ? AND name=? AND age=? AND mobile=? AND targetWeight =?", new String[]{email, password, name, age, mobile, targetWeight}, null, null, null);
*/
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + "targetDetails", null);
        List<TargetModal> item_data = new ArrayList<>();
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    TargetModal obj = new TargetModal();
                    obj.time = cursor.getString(cursor.getColumnIndex("time"));
                    obj.date = cursor.getString(cursor.getColumnIndex("date"));
                    obj.weight = cursor.getString(cursor.getColumnIndex("weight"));
                    obj.Calories = cursor.getString(cursor.getColumnIndex("calories"));
                    item_data.add(obj);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return item_data;
    }

/*    public ArrayList<CommonModal> readCourses()
    {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to
        // read data from database.
        Cursor cursor
                = db.rawQuery("SELECT * FROM " + "users", null);

        // on below line we are creating a new array list.
        ArrayList<CommonModal> list
                = new ArrayList<>();

        // moving our cursor to first position.
        if (cursor.moveToFirst()) {
            do {
                // on below line we are adding the data from
                // cursor to our array list.
                list.add(new CommonModal(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)

                ));
            } while (cursor.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursor.close();
        return list;
    }*/

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

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, "targetDetails");
        return numRows;
    }
}

