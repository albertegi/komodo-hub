package com.creekscholar.komodohub;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseConnection extends SQLiteOpenHelper {

//    private static final String DATABASE_NAME = "educationApp.db";
//    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "komodohubdatabase.db";
    private static final int DATABASE_VERSION = 1;

    // Table and column names for Users table
    public static final String TABLE_USERS = "Users";
    public static final String COLUMN_USER_ID = "UserID";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_EMAIL = "Email";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_ROLE = "Role";
    public static final String COLUMN_PROFILE_PICTURE = "ProfilePicture";


    // SQL statement for creating Users table
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT NOT NULL, "
            + COLUMN_EMAIL + " TEXT NOT NULL UNIQUE, "
            + COLUMN_PASSWORD + " TEXT NOT NULL, "
            + COLUMN_ROLE + " TEXT NOT NULL CHECK (Role IN ('SystemAdmin', 'SchoolAdmin', 'Teacher', 'Student')), "
            + COLUMN_PROFILE_PICTURE + " TEXT);";


    // User table
//    private static final String TABLE_USERS = "CREATE TABLE Users (" +
//            "UserID INTEGER PRIMARY KEY AUTOINCREMENT," +
//            "Name TEXT NOT NULL," +
//            "Email TEXT NOT NULL UNIQUE," +
//            "Password TEXT NOT NULL," +
//            "Role TEXT CHECK (Role IN ('SystemAdmin', 'SchoolAdmin', 'Teacher', 'Student')) NOT NULL," +
//            "ProfilePicture TEXT);";




//    public DatabaseConnection(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }

//    @Override
//    public void onCreate(SQLiteDatabase sqLiteDatabase) {
////        String sqlqry1 = "create table users(username text, email text, password text)";
////        sqLiteDatabase.execSQL(sqlqry1);
//
//    }

//    @Override
//    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//
//    }

//    public void register(String username, String email, String password){
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("username", username);
//        contentValues.put("email", email);
//        contentValues.put("password",password);
//
//        // create an object of sqlite database
//        SQLiteDatabase db = getWritableDatabase();
//        db.insert("users", null, contentValues);
//        db.close();
//    }

//    public int login(String username, String password){
//        int result = 0;
//        String str[] = new String[2];
//        str[0] = username;
//        str[1] = password;
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = db.rawQuery("select * from users where username=? and password=?", str);
//        if(cursor.moveToFirst()){
//            result = 1;
//        }
//        return result;
//    }

    public DatabaseConnection(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        //db.execSQL(CREATE_TABLE_SCHOOLS);
        // Execute other CREATE TABLE statements here
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Users");
        //db.execSQL("DROP TABLE IF EXISTS Schools");
        // Drop other tables as needed
        onCreate(db);
    }

    // Sample method for user login
    public boolean checkUserCredentials(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Users WHERE Email = ? AND Password = ?", new String[]{email, password});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    // Sample method for adding a user
    public long addUser(String name, String email, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("Email", email);
        values.put("Password", password);
        values.put("Role", role);
        return db.insert("Users", null, values);
    }
}
