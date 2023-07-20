package com.kiran.weighttracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String databaseName = "SignUp.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "SignUp.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table allUsers(email TEXT primary key, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop table if exists allUsers");
    }

    public Boolean insertData(String name, String age, String email, String password, String mobile, String targetWeight) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("age", age);
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("mobile", mobile);
        contentValues.put("targetWeight", targetWeight);
        long result = sqLiteDatabase.insert("allUsers", null, contentValues);


        if (result == -1) {
            return false;
        } else {
            return true;

        }
    }

    public Boolean checkEmail(String email) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from allUsers where email = ?", new String[]{email});

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from allUsers where email = ? and password = ?", new String[]{email, password});

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
