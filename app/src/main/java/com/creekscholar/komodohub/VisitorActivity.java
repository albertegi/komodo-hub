package com.creekscholar.komodohub;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class VisitorActivity extends AppCompatActivity {
    private EditText visitorNameInput, visitorEmailInput;
    private Button saveVisitorButton;
    private RecyclerView visitorRecyclerView;
    private VisitorAdapter visitorAdapter;
    private DatabaseConnection databaseConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_visitor);


//        // Initialize database connection
//        databaseConnection = new DatabaseConnection(this);
//
//        // Initialize views
//        visitorNameInput = findViewById(R.id.visitorNameInput);
//        visitorEmailInput = findViewById(R.id.visitorEmailInput);
//        saveVisitorButton = findViewById(R.id.saveVisitorButton);
//        visitorRecyclerView = findViewById(R.id.visitorRecyclerView);
//
//        // Set up RecyclerView
//        visitorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        visitorAdapter = new VisitorAdapter(new ArrayList<>());
//        visitorRecyclerView.setAdapter(visitorAdapter);
//
//        // Load visitors from database
//        loadVisitors();
//
//        // Button click listener to save visitor
//        saveVisitorButton.setOnClickListener(v -> saveVisitor());
    }

//    private void saveVisitor() {
//        String name = visitorNameInput.getText().toString().trim();
//        String email = visitorEmailInput.getText().toString().trim();
//
//        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email)) {
//            Toast.makeText(this, "Please enter both name and email", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        long result = databaseConnection.addVisitor(name, email);
//        if (result != -1) {
//            Toast.makeText(this, "Visitor saved successfully!", Toast.LENGTH_SHORT).show();
//            loadVisitors();
//            visitorNameInput.setText("");
//            visitorEmailInput.setText("");
//        } else {
//            Toast.makeText(this, "Failed to save visitor", Toast.LENGTH_SHORT).show();
//        }
//    }

//    private void loadVisitors() {
//        List<Visitor> visitorList = new ArrayList<>();
//        Cursor cursor = databaseConnection.getAllVisitors();
//        if (cursor != null) {
//            while (cursor.moveToNext()) {
//                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConnection.COLUMN_VISITOR_ID));
//                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConnection.COLUMN_VISITOR_NAME));
//                String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConnection.COLUMN_VISITOR_EMAIL));
//                visitorList.add(new Visitor(id, name, email));
//            }
//            cursor.close();
//        }
//        visitorAdapter.setVisitors(visitorList);
//    }


}