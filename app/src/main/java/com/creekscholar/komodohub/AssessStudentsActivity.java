package com.creekscholar.komodohub;

import static com.creekscholar.komodohub.DatabaseConnection.COLUMN_STUDENT_ID;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AssessStudentsActivity extends AppCompatActivity {
    private Button btnSubmitAssessment;
    private DatabaseConnection databaseConnection;
    private EditText etStudentID;
    private EditText etAssessmentDetails;
    private EditText etAssessmentDateInput;
    private Spinner spinnerStudents;
    private SQLiteDatabase db;
    private List<Integer> studentIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_assess_students);


        // Find the UI elements
        //EditText etStudentID = findViewById(R.id.etStudentID);
        etAssessmentDetails = findViewById(R.id.etAssessmentDetails);
        etAssessmentDateInput = findViewById(R.id.etAssessmentDate);
        btnSubmitAssessment = findViewById(R.id.btnSubmitAssessment);
        spinnerStudents = findViewById(R.id.spinnerStudents);


        databaseConnection = new DatabaseConnection(this);
        db = databaseConnection.getWritableDatabase();

        // Populate the Spinner with student names or IDs
        loadStudentsIntoSpinner();

        // Set onClickListener for the "Submit Assessment" button
        btnSubmitAssessment.setOnClickListener(v -> submitAssessment());

    }


    private void loadStudentsIntoSpinner() {
        // SQL query to join the Students and Users tables using the correct column names
        String query = "SELECT s." + DatabaseConnection.COLUMN_STUDENT_ID + ", u." + DatabaseConnection.COLUMN_NAME +
                " FROM " + DatabaseConnection.TABLE_STUDENTS + " s " +
                "JOIN " + DatabaseConnection.TABLE_USERS + " u ON s." + DatabaseConnection.COLUMN_STUDENT_USER_ID + " = u." + DatabaseConnection.COLUMN_USER_ID;

        // Execute the query
        Cursor cursor = db.rawQuery(query, null);

        List<String> studentNames = new ArrayList<>();
        studentIds.clear();  // Clear the list before adding new data

        if (cursor != null && cursor.moveToFirst()) {
            int studentNameColumnIndex = cursor.getColumnIndex(DatabaseConnection.COLUMN_NAME);  // The column in Users table
            int studentIdColumnIndex = cursor.getColumnIndex(DatabaseConnection.COLUMN_STUDENT_ID); // The column in Students table

            // Ensure that the column indices are valid
            if (studentNameColumnIndex >= 0 && studentIdColumnIndex >= 0) {
                do {
                    // Get the student name and ID from the cursor
                    String studentName = cursor.getString(studentNameColumnIndex);
                    int studentId = cursor.getInt(studentIdColumnIndex);

                    studentNames.add(studentName);
                    studentIds.add(studentId);  // Store the student ID
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        // Create an ArrayAdapter to display the student names in the Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, studentNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStudents.setAdapter(adapter);
    }




    private void submitAssessment() {
        int selectedPosition = spinnerStudents.getSelectedItemPosition();
        if (selectedPosition < 0) {
            Toast.makeText(this, "Please select a student.", Toast.LENGTH_SHORT).show();
            return;
        }

        int studentId = studentIds.get(selectedPosition);
        String assessmentDetails = etAssessmentDetails.getText().toString().trim();
        String assessmentDate = etAssessmentDateInput.getText().toString().trim();

        if (assessmentDetails.isEmpty() || assessmentDate.isEmpty()) {
            Toast.makeText(this, "Please provide assessment details.", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put(DatabaseConnection.COLUMN_STUDENT_ID, studentId);
        values.put(DatabaseConnection.COLUMN_ASSESSMENT_DETAILS, assessmentDetails);
        values.put(DatabaseConnection.COLUMN_ASSESSMENT_DATE, assessmentDate);

        long result = db.insert(DatabaseConnection.TABLE_ASSESSMENTS, null, values);
        Toast.makeText(this, result == -1 ? "Failed to save assessment." : "Assessment saved successfully.", Toast.LENGTH_SHORT).show();
    }



}