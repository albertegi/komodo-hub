package com.creekscholar.komodohub;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseConnection extends SQLiteOpenHelper {
    public DatabaseConnection(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlqry1 = "create table users(username tex, email text, password text)";
        sqLiteDatabase.execSQL(sqlqry1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void register(String username, String email, String password){
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("email", email);
        contentValues.put("password",password);

        // create an object of sqlite database
        SQLiteDatabase db = getWritableDatabase();
        db.insert("users", null, contentValues);
        db.close();
    }

    public int login(String username, String password){
        int result = 0;
        String str[] = new String[2];
        str[0] = username;
        str[1] = password;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=? and password=?", str);
        if(cursor.moveToFirst()){
            result = 1;
        }
        return result;
    }
}
