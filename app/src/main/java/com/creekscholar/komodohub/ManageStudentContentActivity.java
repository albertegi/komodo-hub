package com.creekscholar.komodohub;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ManageStudentContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_student_content);

        findViewById(R.id.btnUploadContent).setOnClickListener(v -> {
            // Open file picker for content upload
        });

        findViewById(R.id.btnPublishContent).setOnClickListener(v -> {
            // Logic to publish content
        });
    }
}