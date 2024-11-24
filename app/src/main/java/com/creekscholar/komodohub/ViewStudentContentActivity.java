package com.creekscholar.komodohub;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ViewStudentContentActivity extends AppCompatActivity {
    private RecyclerView vsContentList;
    private StudentContentAdapter studentContentAdapter;
    private List<SightingReport> sightingList = new ArrayList<>();
    private DatabaseConnection databaseConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_content);

        // Initialize views
        vsContentList = findViewById(R.id.vsContentList);
        databaseConnection = new DatabaseConnection(this);

        // Setup RecyclerView
        vsContentList.setLayoutManager(new LinearLayoutManager(this));
        studentContentAdapter = new StudentContentAdapter(this, sightingList);
        vsContentList.setAdapter(studentContentAdapter);

        // Load content
        loadStudentPublishedContent();
    }

    private void loadStudentPublishedContent() {
        Cursor cursor = databaseConnection.getStudentPublishedContent(); // Ensure this method works correctly
        if (cursor != null) {
            // Clear the list to avoid duplicate entries on reload
            sightingList.clear();

            // Fetch data from the cursor
            while (cursor.moveToNext()) {
                // Get column indices safely
                int speciesNameIndex = cursor.getColumnIndex("SpeciesName");
                int descriptionIndex = cursor.getColumnIndex("Description");
                int locationIndex = cursor.getColumnIndex("Location");
                int reportedDateIndex = cursor.getColumnIndex("ReportedDate");
                int statusIndex = cursor.getColumnIndex("Status");



                // Check if all indices are valid (>= 0)
                if (speciesNameIndex >= 0 && descriptionIndex >= 0 && locationIndex >= 0 &&
                        reportedDateIndex >= 0 && statusIndex >= 0) {

                    // Retrieve data from the cursor
                    String speciesName = cursor.getString(speciesNameIndex);
                    String description = cursor.getString(descriptionIndex);
                    String location = cursor.getString(locationIndex);
                    String reportedDate = cursor.getString(reportedDateIndex);
                    String status = cursor.getString(statusIndex);

                    // Add the sighting report to the list
                    SightingReport sightingReport = new SightingReport(speciesName, description, location, reportedDate, status);
                    sightingList.add(sightingReport);
                } else {
                    // Log or handle the case where column names do not match
                    System.err.println("One or more column indices are invalid.");
                }
            }
            cursor.close();
        }

        // Notify the adapter to refresh the RecyclerView
        studentContentAdapter.notifyDataSetChanged();
    }

}
