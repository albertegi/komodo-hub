package com.creekscholar.komodohub;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;


public class SightingReportsActivity extends AppCompatActivity {
    private String selectedPhotoUriString;
    private static final int REQUEST_CODE_PHOTO = 1001;

    private EditText speciesNameInput, descriptionInput, locationInput, reportedDateInput;
    private ImageView photoImageView;
    private Uri photoUri = null; // To store the photo URI
    private DatabaseConnection databaseConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sighting_reports);


        // Initialize views
        speciesNameInput = findViewById(R.id.speciesNameInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        locationInput = findViewById(R.id.locationInput);
        photoImageView = findViewById(R.id.selectedPhotoView);
        reportedDateInput = findViewById(R.id.reportedDateInput);
        Button submitButton = findViewById(R.id.submitButton);
        Button addPhotoButton = findViewById(R.id.addPhotoButton);


        databaseConnection = new DatabaseConnection(this);

        // Listener for adding a photo
        addPhotoButton.setOnClickListener(v -> selectPhoto());

        // Listener for submitting the sighting report
        submitButton.setOnClickListener(v -> submitSightingReport());
    }


    private void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PHOTO && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            if (selectedImageUri != null) {
                // Store the selected photo URI for later use (e.g., to display in the report)
                ImageView selectedPhotoView = findViewById(R.id.selectedPhotoView); // Replace with your ImageView ID
                selectedPhotoView.setImageURI(selectedImageUri);

                // Optionally, save the URI as a string to use in your database
                selectedPhotoUriString = selectedImageUri.toString();
            } else {
                Toast.makeText(this, "No photo selected.", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void submitSightingReport() {
        String speciesName = speciesNameInput.getText().toString().trim();
        String description = descriptionInput.getText().toString().trim();
        String location = locationInput.getText().toString().trim();
        String reportedDate = java.text.DateFormat.getDateInstance().format(new java.util.Date());
        String status = "Pending"; // Default status

        if (speciesName.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Species name and location are required!", Toast.LENGTH_LONG).show();
            return;
        }

        // Store the data into ContentValues to insert into the SightingReports table
        SQLiteDatabase db = databaseConnection.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("SpeciesName", speciesName);
        values.put("Description", description);
        values.put("Location", location);
        values.put("ReportedDate", reportedDate);
        values.put("Status", status);

        if (photoUri != null) {
            values.put("Photo", photoUri.toString()); // Store URI as string
        }

        long result = db.insert("SightingReports", null, values);

        if (result != -1) {
            Toast.makeText(this, "Sighting report submitted successfully!", Toast.LENGTH_LONG).show();
            finish(); // Close activity after successful submission
        } else {
            Toast.makeText(this, "Failed to submit sighting report!", Toast.LENGTH_LONG).show();
        }
    }

}