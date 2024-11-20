package com.creekscholar.komodohub;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class DashboardActivity extends AppCompatActivity {

    private TextView tvWelcomeMessage, tvTotalClasses, tvTotalContent, tvRecentUpdates;
    private CardView cvClasses, cvContent, cvContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize views
        tvWelcomeMessage = findViewById(R.id.tvWelcomeMessage);
        tvTotalClasses = findViewById(R.id.tvTotalClasses);
        tvTotalContent = findViewById(R.id.tvTotalContent);
        tvRecentUpdates = findViewById(R.id.tvRecentUpdates);

        cvClasses = findViewById(R.id.cvClasses);
        cvContent = findViewById(R.id.cvContent);
        cvContact = findViewById(R.id.cvContact);

        // Set welcome message
        tvWelcomeMessage.setText("Welcome to KomodoHub!");

        // Fetch overall information (for demonstration, hardcoded values are used)
        int totalClasses = fetchTotalClasses();
        int totalContent = fetchTotalContent();
        String recentUpdates = fetchRecentUpdates();

        tvTotalClasses.setText(String.valueOf(totalClasses));
        tvTotalContent.setText(String.valueOf(totalContent));
        tvRecentUpdates.setText(recentUpdates);

        // Set click listeners for navigation
        cvClasses.setOnClickListener(v -> navigateToClasses());
        cvContent.setOnClickListener(v -> navigateToContent());
        cvContact.setOnClickListener(v -> navigateToContact());
    }

    private int fetchTotalClasses() {
        // Replace with actual logic to fetch the total number of classes
        return 12;
    }

    private int fetchTotalContent() {
        // Replace with actual logic to fetch the total number of content pieces
        return 50;
    }

    private String fetchRecentUpdates() {
        // Replace with actual logic to fetch recent updates
        return "New content added on 16th Nov.";
    }

    private void navigateToClasses() {
        // Replace with actual navigation logic
        Toast.makeText(this, "Navigating to Classes...", Toast.LENGTH_SHORT).show();
    }

    private void navigateToContent() {
        // Replace with actual navigation logic
        Toast.makeText(this, "Navigating to Content...", Toast.LENGTH_SHORT).show();
    }

    private void navigateToContact() {
        // Replace with actual navigation logic
        Toast.makeText(this, "Navigating to Contact Us...", Toast.LENGTH_SHORT).show();
    }
}
