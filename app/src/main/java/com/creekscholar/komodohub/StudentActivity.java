package com.creekscholar.komodohub;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class StudentActivity extends AppCompatActivity {
    private Button viewContentButton;
    private Button reportSightingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student);

        viewContentButton = findViewById(R.id.viewContentButton);
        reportSightingsButton = findViewById(R.id.reportSightingsButton);

//        viewContentButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Implement logic to view learning content or navigate to LearningContentActivity
//                Intent intent = new Intent(StudentActivity.this, LearningContentActivity.class);
//                startActivity(intent);
//            }
//        });

        reportSightingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement logic to report sightings of endangered species
                Intent intent = new Intent(StudentActivity.this, ReportSightingActivity.class);
                startActivity(intent);
            }
        });


    }
}