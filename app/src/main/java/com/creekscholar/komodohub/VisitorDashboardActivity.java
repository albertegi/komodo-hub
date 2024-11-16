package com.creekscholar.komodohub;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.ListView;
import android.widget.TextView;
import java.util.List;

public class VisitorDashboardActivity extends AppCompatActivity {
//    private TextView informationTextView;
//    private DatabaseConnection databaseConnection;

    private ListView contentListView;
    private DatabaseConnection databaseConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_visitor_dashboard);

        contentListView = findViewById(R.id.contentListView);
        databaseConnection = new DatabaseConnection(this);

        List<String> generalInfo = databaseConnection.getGeneralInformation();
        VisitorAdapter adapter = new VisitorAdapter(this, generalInfo);
        contentListView.setAdapter(adapter);

        contentListView = findViewById(R.id.contentListView);
        databaseConnection = new DatabaseConnection(this);




//
//
//
//        informationTextView = findViewById(R.id.informationTextView);
//
//        // Initialize database connection
//        databaseConnection = new DatabaseConnection(this);
//
//        // Load information for visitors
//        loadVisitorInformation();

    }

//    private void loadVisitorInformation() {
//        List<String> infoList = databaseConnection.getGeneralInformation(); // Retrieve general content
//
//        // Display the information on the screen
//        StringBuilder informationText = new StringBuilder();
//        for (String info : infoList) {
//            informationText.append(info).append("\n\n");
//        }
//
//        informationTextView.setText(informationText.toString());
//    }
}