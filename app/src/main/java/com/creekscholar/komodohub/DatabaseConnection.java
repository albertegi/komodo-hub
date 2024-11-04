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
    private static final int DATABASE_VERSION = 3;

    // Table and column names for Users table
//    public static final String TABLE_USERS = "Users";
//    public static final String COLUMN_USER_ID = "UserID";
//    public static final String COLUMN_NAME = "Name";
//    public static final String COLUMN_EMAIL = "Email";
//    public static final String COLUMN_PASSWORD = "Password";
//    public static final String COLUMN_ROLE = "Role";
//    public static final String COLUMN_PROFILE_PICTURE = "ProfilePicture";
    // Table and column names for Users table

    // Table and column names for Users table
    public static final String TABLE_USERS = "Users";
    public static final String COLUMN_USER_ID = "UserID";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_EMAIL = "Email";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_ROLE = "Role";
    public static final String COLUMN_PROFILE_PICTURE = "ProfilePicture";
    public static final String COLUMN_USER_SCHOOL_ID = "UserSchoolID";  // Renamed to avoid conflict

    // Table and column names for Schools table
    public static final String TABLE_SCHOOLS = "Schools";
    public static final String COLUMN_SCHOOL_ID = "SchoolID";
    public static final String COLUMN_SCHOOL_NAME = "SchoolName";
    public static final String COLUMN_ADDRESS = "Address";
    public static final String COLUMN_SUBSCRIPTION_STATUS = "SubscriptionStatus";
    public static final String COLUMN_PAYMENT_DETAILS = "PaymentDetails";
    public static final String COLUMN_SCHOOL_ADMIN_ID = "SchoolAdminID";



//    // Table and column names for Schools table
//    public static final String TABLE_SCHOOLS = "Schools";
//    public static final String COLUMN_SCHOOL_ID = "SchoolID";
//    public static final String COLUMN_SCHOOL_NAME = "SchoolName";
//    public static final String COLUMN_ADDRESS = "Address";
//    public static final String COLUMN_SUBSCRIPTION_STATUS = "SubscriptionStatus";
//    public static final String COLUMN_PAYMENT_DETAILS = "PaymentDetails";
//    public static final String COLUMN_SCHOOL_ADMIN_ID = "SchoolAdminID";

//    // Table and column names for Schools table
//    public static final String TABLE_SCHOOLS = "Schools";
//    public static final String COLUMN_SCHOOL_ID = "SchoolID";
//    public static final String COLUMN_SCHOOL_NAME = "SchoolName";
//    public static final String COLUMN_ADDRESS = "Address";
//    public static final String COLUMN_SUBSCRIPTION_STATUS = "SubscriptionStatus";
//    public static final String COLUMN_PAYMENT_DETAILS = "PaymentDetails";
//    public static final String COLUMN_SCHOOL_ADMIN_ID = "SchoolAdminID";


    // SQL statement for creating Users table
//    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
//            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//            + COLUMN_NAME + " TEXT NOT NULL, "
//            + COLUMN_EMAIL + " TEXT NOT NULL UNIQUE, "
//            + COLUMN_PASSWORD + " TEXT NOT NULL, "
//            + COLUMN_ROLE + " TEXT NOT NULL CHECK (Role IN ('SystemAdmin', 'SchoolAdmin', 'Teacher', 'Student')), "
//            + COLUMN_PROFILE_PICTURE + " TEXT);";

    // SQL statement for creating Users table with updated school ID reference
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT NOT NULL, "
            + COLUMN_EMAIL + " TEXT NOT NULL UNIQUE, "
            + COLUMN_PASSWORD + " TEXT NOT NULL, "
            + COLUMN_ROLE + " TEXT NOT NULL CHECK (Role IN ('SystemAdmin', 'SchoolAdmin', 'Teacher', 'Student')), "
            + COLUMN_PROFILE_PICTURE + " TEXT, "
            + COLUMN_USER_SCHOOL_ID + " INTEGER, "  // Updated reference name
            + "FOREIGN KEY(" + COLUMN_USER_SCHOOL_ID + ") REFERENCES " + TABLE_SCHOOLS + "(" + COLUMN_SCHOOL_ID + ") ON DELETE CASCADE"
            + ");";

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


    // SQL statement for creating Schools table
//    private static final String CREATE_TABLE_SCHOOLS = "CREATE TABLE " + TABLE_SCHOOLS + "("
//            + COLUMN_SCHOOL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//            + COLUMN_SCHOOL_NAME + " TEXT NOT NULL, "
//            + COLUMN_ADDRESS + " TEXT NOT NULL, "
//            + COLUMN_SUBSCRIPTION_STATUS + " TEXT NOT NULL CHECK (SubscriptionStatus IN ('Active', 'Inactive')), "
//            + COLUMN_PAYMENT_DETAILS + " TEXT, "
//            + COLUMN_SCHOOL_ADMIN_ID + " INTEGER, "
//            + "FOREIGN KEY(" + COLUMN_SCHOOL_ADMIN_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ") ON DELETE CASCADE);";

    // SQL statement for creating Schools table
    private static final String CREATE_TABLE_SCHOOLS = "CREATE TABLE " + TABLE_SCHOOLS + "("
            + COLUMN_SCHOOL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_SCHOOL_NAME + " TEXT NOT NULL UNIQUE, "
            + COLUMN_ADDRESS + " TEXT NOT NULL, "
            + COLUMN_SUBSCRIPTION_STATUS + " TEXT NOT NULL CHECK (SubscriptionStatus IN ('Active', 'Inactive')), "
            + COLUMN_PAYMENT_DETAILS + " TEXT, "
            + COLUMN_SCHOOL_ADMIN_ID + " INTEGER, "
            + "FOREIGN KEY(" + COLUMN_SCHOOL_ADMIN_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ") ON DELETE CASCADE"
            + ");";

    public DatabaseConnection(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_SCHOOLS);
        Log.d("DatabaseConnection", "Users table created");
        db.execSQL(INSERT_SYSTEM_ADMIN);
        Log.d("DatabaseConnection", "SystemAdmin seeded into Users table");

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

    // Method to add a new SchoolAdmin user
    public long addSchoolAdmin(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_ROLE, "SchoolAdmin");
        values.put(COLUMN_PROFILE_PICTURE, (String) null);

        return db.insert(TABLE_USERS, null, values);
    }

    // Method to retrieve a SchoolAdmin user ID by email
    public long getSchoolAdminIdByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_USER_ID};
        String selection = COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        long userId = -1;
        if (cursor.moveToFirst()) {
            userId = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_USER_ID));
        }
        cursor.close();
        return userId;
    }

    // Method to add a new school linked to a SchoolAdmin user
    public long addSchool(String schoolName, String address, String subscriptionStatus, String paymentDetails, long schoolAdminId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SCHOOL_NAME, schoolName);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_SUBSCRIPTION_STATUS, subscriptionStatus);
        values.put(COLUMN_PAYMENT_DETAILS, paymentDetails);
        values.put(COLUMN_SCHOOL_ADMIN_ID, schoolAdminId);

        return db.insert(TABLE_SCHOOLS, null, values);
    }








}
