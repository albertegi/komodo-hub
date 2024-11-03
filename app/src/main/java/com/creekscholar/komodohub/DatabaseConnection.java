package com.creekscholar.komodohub;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseConnection extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "komodo_hub.db";
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

    // Insert a default SystemAdmin user
    private static final String INSERT_SYSTEM_ADMIN = "INSERT INTO " + TABLE_USERS + " ("
            + COLUMN_NAME + ", "
            + COLUMN_EMAIL + ", "
            + COLUMN_PASSWORD + ", "
            + COLUMN_ROLE + ", "
            + COLUMN_PROFILE_PICTURE + ") VALUES ("
            + "'Albert Egi', "               // Name
            + "'albertegi90@gmail.com', "      // Email
            + "'password123', "              // Password (Consider hashing in a real app)
            + "'SystemAdmin', "              // Role
            + "NULL);";                      // Profile Picture (null if not specified)



    public DatabaseConnection(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        Log.d("DatabaseHelper", "Users table created");
        db.execSQL(INSERT_SYSTEM_ADMIN);
        Log.d("DatabaseHelper", "SystemAdmin seeded into Users table");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Users");
        onCreate(db);
    }

    // method for user login
    public boolean checkUserCredentials(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Users WHERE Email = ? AND Password = ?", new String[]{email, password});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    // method for adding a user
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
