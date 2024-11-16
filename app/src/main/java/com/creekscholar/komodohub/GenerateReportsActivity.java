package com.creekscholar.komodohub;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class GenerateReportsActivity extends AppCompatActivity {
    private DatabaseConnection databaseConnection;
    private SQLiteDatabase db;
    private ListView lvStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_generate_reports);

        // Initialize database connection
        databaseConnection = new DatabaseConnection(this);
        db = databaseConnection.getReadableDatabase();

        // Find UI components
        lvStudents = findViewById(R.id.lvStudents);

        // Fetch students and populate ListView
        populateStudentList();
    }

    private void populateStudentList() {
        List<String> studentReports = fetchStudents();

        if (studentReports.isEmpty()) {
            studentReports.add("No student data available.");
        }

        // Create an ArrayAdapter to display the student data in the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentReports);
        lvStudents.setAdapter(adapter);
    }

    private List<String> fetchStudents() {
        List<String> students = new ArrayList<>();

        // SQL query to fetch student data with user details
        String query = "SELECT s." + DatabaseConnection.COLUMN_STUDENT_ID + ", " +
                "u." + DatabaseConnection.COLUMN_NAME + ", " +
                "u." + DatabaseConnection.COLUMN_EMAIL + ", " +
                "s." + DatabaseConnection.COLUMN_GRADE_LEVEL + ", " +
                "s." + DatabaseConnection.COLUMN_ENROLLED_PROGRAM +
                " FROM " + DatabaseConnection.TABLE_STUDENTS + " s " +
                "JOIN " + DatabaseConnection.TABLE_USERS + " u " +
                "ON s." + DatabaseConnection.COLUMN_STUDENT_USER_ID + " = u." + DatabaseConnection.COLUMN_USER_ID;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            int studentIdIndex = cursor.getColumnIndex(DatabaseConnection.COLUMN_STUDENT_ID);
            int nameIndex = cursor.getColumnIndex(DatabaseConnection.COLUMN_NAME);
            int emailIndex = cursor.getColumnIndex(DatabaseConnection.COLUMN_EMAIL);
            int gradeLevelIndex = cursor.getColumnIndex(DatabaseConnection.COLUMN_GRADE_LEVEL);
            int programIndex = cursor.getColumnIndex(DatabaseConnection.COLUMN_ENROLLED_PROGRAM);

            do {
                int studentId = cursor.getInt(studentIdIndex);
                String name = cursor.getString(nameIndex);
                String email = cursor.getString(emailIndex);
                String gradeLevel = cursor.getString(gradeLevelIndex);
                String enrolledProgram = cursor.getString(programIndex);

                String studentReport = "ID: " + studentId + "\n" +
                        "Name: " + name + "\n" +
                        "Email: " + email + "\n" +
                        "Grade: " + gradeLevel + "\n" +
                        "Program: " + enrolledProgram;

                students.add(studentReport);
            } while (cursor.moveToNext());

            cursor.close();
        } else {
            Log.d("GenerateReportsActivity", "No students found in the database.");
        }

        return students;
    }
}