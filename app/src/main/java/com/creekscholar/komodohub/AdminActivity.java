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

public class AdminActivity extends AppCompatActivity {
    private Button manageSchoolsButton;
    private Button viewCommunityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);




        manageSchoolsButton = findViewById(R.id.manageSchoolsButton);
        viewCommunityButton = findViewById(R.id.viewCommunityButton);


        manageSchoolsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement managing schools logic or navigate to a ManageSchoolsActivity
                Intent intent = new Intent(AdminActivity.this, ManageSchoolsActivity.class);
                startActivity(intent);
            }
        });

        viewCommunityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement viewing community data logic
                Intent intent = new Intent(AdminActivity.this, ViewCommunityActivity.class);
                startActivity(intent);
            }
        });

    }
}