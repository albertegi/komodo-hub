package com.creekscholar.komodohub;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class VisitorDashboardActivity extends AppCompatActivity {
    private RecyclerView rvSightingReports;
    private VisitorAdapter visitorAdapter;
    private List<SightingReport> sightingReportList = new ArrayList<>();
    private DatabaseConnection databaseConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_dashboard);

        // Initialize RecyclerView and database connection
        rvSightingReports = findViewById(R.id.rvSightingReports);
        databaseConnection = new DatabaseConnection(this);

        // Set up RecyclerView
        rvSightingReports.setLayoutManager(new LinearLayoutManager(this));
        visitorAdapter = new VisitorAdapter(this, sightingReportList);
        rvSightingReports.setAdapter(visitorAdapter);

        // Load sighting reports for the visitor
        loadSightingReports();
    }

    private void loadSightingReports() {
        Cursor cursor = databaseConnection.getSightingReportsForVisitor();

        if (cursor != null) {
            try {
                // Get column indices
                int speciesNameIndex = cursor.getColumnIndex("SpeciesName");
                int descriptionIndex = cursor.getColumnIndex("Description");
                int photoIndex = cursor.getColumnIndex("Photo");
                int locationIndex = cursor.getColumnIndex("Location");
                int reportedDateIndex = cursor.getColumnIndex("ReportedDate");

                if (speciesNameIndex == -1 || descriptionIndex == -1 || photoIndex == -1 ||
                        locationIndex == -1 || reportedDateIndex == -1) {
                    Log.e("DB_ERROR", "Required column(s) not found");
                    return;
                }

                // Populate sighting reports
                while (cursor.moveToNext()) {
                    SightingReport sightingReport = new SightingReport(
                            cursor.getString(speciesNameIndex),
                            cursor.getString(descriptionIndex),
                            cursor.getString(photoIndex),
                            cursor.getString(locationIndex),
                            cursor.getString(reportedDateIndex)
                    );
                    sightingReportList.add(sightingReport);
                }
            } catch (Exception e) {
                Log.e("LOAD_REPORTS_ERROR", "Error loading reports", e);
            } finally {
                cursor.close();
            }
        } else {
            Log.w("DB_WARNING", "No sighting reports found");
            Toast.makeText(this, "No reports found", Toast.LENGTH_SHORT).show();
        }

        // Notify adapter of data changes
        if (visitorAdapter != null) {
            visitorAdapter.notifyDataSetChanged();
        }
    }
}
