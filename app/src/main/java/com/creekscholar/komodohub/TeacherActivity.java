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

public class TeacherActivity extends AppCompatActivity {
    private Button manageClassesButton;
    private Button manageContentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_teacher);

        manageClassesButton = findViewById(R.id.manageClassesButton);
        manageContentButton = findViewById(R.id.manageContentButton);



        manageClassesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement logic to manage classes or navigate to ManageClassesActivity
                Intent intent = new Intent(TeacherActivity.this, ManageClassesActivity.class);
                startActivity(intent);
            }
        });

        manageContentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement logic to manage content related to animal conservation
                Intent intent = new Intent(TeacherActivity.this, ManageContentActivity.class);
                startActivity(intent);
            }
        });
    }
}