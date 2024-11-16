package com.creekscholar.komodohub;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.Cursor;

public class UserDao {
    private DatabaseConnection databaseConnection;

    public UserDao(Context context) {

        databaseConnection = new DatabaseConnection(context);
    }

    // Method to add a new user (register)
    public boolean addUser(String name, String email, String password, String role) {
        SQLiteDatabase db = databaseConnection.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseConnection.COLUMN_NAME, name);
        values.put(DatabaseConnection.COLUMN_EMAIL, email);
        values.put(DatabaseConnection.COLUMN_PASSWORD, password);  // In production, hash the password
        values.put(DatabaseConnection.COLUMN_ROLE, role);

        long result = db.insert(DatabaseConnection.TABLE_USERS, null, values);
        db.close();

        return result != -1;  // Returns true if inserted successfully, false otherwise
    }

    // Method to authenticate user (login)
    public boolean authenticateUser(String email, String password) {
        SQLiteDatabase db = databaseConnection.getReadableDatabase();
        String[] columns = {DatabaseConnection.COLUMN_USER_ID};
        String selection = DatabaseConnection.COLUMN_EMAIL + " = ? AND " + DatabaseConnection.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(DatabaseConnection.TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        boolean isAuthenticated = cursor.moveToFirst();
        cursor.close();
        db.close();

        return isAuthenticated;  // Returns true if user exists with matching credentials
    }

//    public String authenticateUser(String email, String password) {
//        SQLiteDatabase db = databaseConnection.getReadableDatabase();
//        String role = null;
//
//        Cursor cursor = db.query("Users",
//                new String[]{"UserID", "Role"},
//                "Email = ? AND Password = ?",
//                new String[]{email, password},
//                null, null, null);
//
//        if (cursor != null && cursor.moveToFirst()) {
//            // Get index of the "Role" column
//            int roleIndex = cursor.getColumnIndex("Role");
//            if (roleIndex >= 0) {  // Check if the column index is valid
//                role = cursor.getString(roleIndex);  // Get the role value
//            }
//            cursor.close();
//        }
//        return role;  // Returns null if user is not authenticated
//    }

    // Insert user method (assuming it's already implemented)
    public long insertUser(User user) {
        SQLiteDatabase db = databaseConnection.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", user.getName());
        values.put("Email", user.getEmail());
        values.put("Password", user.getPassword());
        values.put("Role", user.getRole());
        return db.insert("Users", null, values);
    }

    public String getUserRole(String email) {
        SQLiteDatabase db = databaseConnection.getReadableDatabase();
        String[] columns = {DatabaseConnection.COLUMN_ROLE};
        String selection = DatabaseConnection.COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(DatabaseConnection.TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int roleIndex = cursor.getColumnIndex(DatabaseConnection.COLUMN_ROLE);
                if (roleIndex != -1) {  // Ensure the column exists
                    String role = cursor.getString(roleIndex);
                    cursor.close();
                    return role;
                }
            }
            cursor.close();
        }
        return null;
    }

    public int getUserId(String email) {
        SQLiteDatabase db = databaseConnection.getReadableDatabase();
        String[] columns = {DatabaseConnection.COLUMN_USER_ID};
        String selection = DatabaseConnection.COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(DatabaseConnection.TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int userIdIndex = cursor.getColumnIndex(DatabaseConnection.COLUMN_USER_ID);
                if (userIdIndex != -1) {  // Ensure the column exists
                    int user_id = cursor.getInt(userIdIndex);
                    cursor.close();
                    return user_id;
                }
            }
            cursor.close();
        }
        return -1;
    }

    public int getSchoolIdByUserId(int userId) {
        SQLiteDatabase db = databaseConnection.getReadableDatabase();
        int schoolId = -1; // Default value if no school is found
        String query = "SELECT " + DatabaseConnection.COLUMN_SCHOOL_ID + " FROM " + DatabaseConnection.TABLE_SCHOOLS +
                " WHERE " + DatabaseConnection.COLUMN_SCHOOL_ADMIN_ID + " = ?";
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
            if (cursor != null && cursor.moveToFirst()) {
                schoolId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConnection.COLUMN_SCHOOL_ID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return schoolId;
    }



}
