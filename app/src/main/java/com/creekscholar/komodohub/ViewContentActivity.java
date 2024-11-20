
package com.creekscholar.komodohub;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewContentActivity extends AppCompatActivity {
    private RecyclerView rvContentList;
    private ContentAdapter contentAdapter;
    private List<Content> contentList = new ArrayList<>();
    private DatabaseConnection databaseConnection;

    public static final String PREFERENCES = "UserPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_content);

        // Initialize RecyclerView and database connection
        rvContentList = findViewById(R.id.rvContentList);
        databaseConnection = new DatabaseConnection(this);

        // Setup RecyclerView
        if (rvContentList != null) {
            rvContentList.setLayoutManager(new LinearLayoutManager(this));
            contentAdapter = new ContentAdapter(this, contentList);
            rvContentList.setAdapter(contentAdapter);
        } else {
            Log.e("INIT_ERROR", "RecyclerView initialization failed");
        }

        // Retrieve ClassID from Intent or SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        int classId = getIntent().getIntExtra("ClassID", -1); // Default to -1 if not provided

        if (classId == -1) {
            // Fallback to ClassID from SharedPreferences if not provided in Intent
            classId = sharedPreferences.getInt("ClassID", -1);
        } else {
            // Store the new ClassID in SharedPreferences for future use
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("ClassID", classId);
            editor.apply();
        }

        if (classId != -1) {
            // Load content for the valid ClassID
            loadContent(classId);
        } else {
            // Handle case where ClassID is missing or invalid
            Log.e("CLASS_ID_ERROR", "No valid ClassID found in Intent or SharedPreferences");
            showErrorMessage("ClassID is missing. Please log in again.");
        }
    }

    private void loadContent(int classId) {
        // Fetch content from the database for the given ClassID
        Cursor cursor = databaseConnection.getPublishedContent(classId);

        if (cursor != null) {
            try {
                // Log available column names for debugging
                String[] columnNames = cursor.getColumnNames();
                Log.d("DB_COLUMNS", "Available columns: " + Arrays.toString(columnNames));

                // Validate required column indices
                int titleIndex = cursor.getColumnIndex("Title");
                int descriptionIndex = cursor.getColumnIndex("Description");
                int filePathIndex = cursor.getColumnIndex("FilePath");

                if (titleIndex == -1 || descriptionIndex == -1 || filePathIndex == -1) {
                    Log.e("DB_ERROR", "Required column(s) not found in database table");
                    return;
                }

                // Read data from cursor and populate content list
                while (cursor.moveToNext()) {
                    String title = cursor.getString(titleIndex);
                    String description = cursor.getString(descriptionIndex);
                    String filePath = cursor.getString(filePathIndex);

                    Content content = new Content(title, description, filePath);
                    contentList.add(content);
                }

                // Check if contentList is still empty after processing the cursor
                if (contentList.isEmpty()) {
                    Log.w("LOAD_CONTENT", "No content found for ClassID " + classId);
                    showErrorMessage("No content available for the selected class.");
                }

            } catch (Exception e) {
                Log.e("LOAD_CONTENT_ERROR", "Error while loading content: " + e.getMessage(), e);
                showErrorMessage("Error while loading content. Please try again.");
            } finally {
                // Close the cursor to release database resources
                cursor.close();
            }
        } else {
            Log.w("DB_WARNING", "Cursor is null; no data returned for ClassID " + classId);
            showErrorMessage("No data found for the selected class.");
        }

        // Notify the adapter of data changes
        if (contentAdapter != null) {
            contentAdapter.notifyDataSetChanged();
        } else {
            Log.e("ADAPTER_ERROR", "ContentAdapter is not initialized");
        }
    }

    private void showErrorMessage(String message) {
        // Optionally display a Toast or show a TextView with the error message
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        // You can also set a fallback UI element like a TextView
        // For example:
        /*
        TextView errorMessageView = findViewById(R.id.error_message_view);
        if (errorMessageView != null) {
            errorMessageView.setText(message);
            errorMessageView.setVisibility(View.VISIBLE);
        }
        */
    }
}

//
//public class ViewContentActivity extends AppCompatActivity {
//    private RecyclerView rvContentList;
//    private ContentAdapter contentAdapter;
//    private List<Content> contentList = new ArrayList<>();
//    private DatabaseConnection databaseConnection;
//
//    public static final String PREFERENCES = "UserPrefs";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_content);
//
//        // Initialize RecyclerView and database connection
//        rvContentList = findViewById(R.id.rvContentList);
//        databaseConnection = new DatabaseConnection(this);
//
//        // Setup RecyclerView
//        if (rvContentList != null) {
//            rvContentList.setLayoutManager(new LinearLayoutManager(this));
//            contentAdapter = new ContentAdapter(this, contentList);
//            rvContentList.setAdapter(contentAdapter);
//        } else {
//            Log.e("INIT_ERROR", "RecyclerView initialization failed");
//        }
//
//        // Retrieve ClassID from the Intent
//        int classId = getIntent().getIntExtra("ClassID", -1); // Default to -1 if not provided
//        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt("ClassID", classId);
//        if (classId != -1) {
//            loadContent(classId);
//        } else {
//            Log.e("CLASS_ID_ERROR", "Invalid ClassID provided. Ensure ClassID is passed in Intent.");
//        }
//        editor.apply();
//    }
//
//
//
//    private void loadContent(int classId) {
//        // Fetch content from the database for the given ClassID
//        Cursor cursor = databaseConnection.getPublishedContent(classId);
//
//        if (cursor != null) {
//            try {
//                // Log available column names for debugging
//                String[] columnNames = cursor.getColumnNames();
//                Log.d("DB_COLUMNS", "Available columns: " + Arrays.toString(columnNames));
//
//                // Validate required column indices
//                int titleIndex = cursor.getColumnIndex("Title");
//                int descriptionIndex = cursor.getColumnIndex("Description");
//                int filePathIndex = cursor.getColumnIndex("FilePath");
//
//                if (titleIndex == -1 || descriptionIndex == -1 || filePathIndex == -1) {
//                    Log.e("DB_ERROR", "Required column(s) not found in database table");
//                    return;
//                }
//
//                // Read data from cursor and populate content list
//                while (cursor.moveToNext()) {
//                    String title = cursor.getString(titleIndex);
//                    String description = cursor.getString(descriptionIndex);
//                    String filePath = cursor.getString(filePathIndex);
//
//                    Content content = new Content(title, description, filePath);
//                    contentList.add(content);
//                }
//
//                // Check if contentList is still empty after processing the cursor
//                if (contentList.isEmpty()) {
//                    Log.w("LOAD_CONTENT", "No content found for ClassID " + classId);
//                }
//
//            } catch (Exception e) {
//                Log.e("LOAD_CONTENT_ERROR", "Error while loading content: " + e.getMessage(), e);
//            } finally {
//                // Close the cursor to release database resources
//                cursor.close();
//            }
//        } else {
//            Log.w("DB_WARNING", "Cursor is null; no data returned for ClassID " + classId);
//        }
//
//        // Notify the adapter of data changes
//        if (contentAdapter != null) {
//            contentAdapter.notifyDataSetChanged();
//        } else {
//            Log.e("ADAPTER_ERROR", "ContentAdapter is not initialized");
//        }
//    }
//}
