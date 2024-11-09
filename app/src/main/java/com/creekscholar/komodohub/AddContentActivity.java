package com.creekscholar.komodohub;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddContentActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_FILE = 1;

    private EditText titleInput, descriptionInput;
    private Spinner typeSpinner;
    private ImageView fileImageView;
    private Button uploadFileButton, submitButton;
    private String selectedType;
    private String filePath = "";

    private Spinner contentTypeSpinner;

    private DatabaseConnection databaseConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_content);


        // Initialize views
        titleInput = findViewById(R.id.titleInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        //typeSpinner = findViewById(R.id.typeSpinner);
        contentTypeSpinner = findViewById(R.id.contentTypeSpinner);
        //String selectedType = contentTypeSpinner.getSelectedItem().toString();
        fileImageView = findViewById(R.id.fileImageView);
        uploadFileButton = findViewById(R.id.uploadFileButton);
        submitButton = findViewById(R.id.submitButton);

        // Initialize Database Connection
        databaseConnection = new DatabaseConnection(this);

        contentTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedType = null;
            }
        });



        // Set up spinner for content type
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.content_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        contentTypeSpinner.setAdapter(adapter);



        // Listener for uploading a file
        uploadFileButton.setOnClickListener(v -> selectFile());

        // Listener for submitting content
        submitButton.setOnClickListener(v -> submitContent());

//        SharedPreferences preferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
//        int classID = preferences.getInt("ClassID", -1);
//        int teacherID = preferences.getInt("TeacherID", -1);

// Proceed similarly with submitContent method

    }


    private void selectFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, REQUEST_CODE_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_FILE && resultCode == RESULT_OK && data != null) {
            filePath = data.getData().toString();
            fileImageView.setImageResource(R.drawable.ic_file_selected); // Change to indicate file selection
        }
    }



    private void submitContent() {
        String title = titleInput.getText().toString().trim();
        String description = descriptionInput.getText().toString().trim();


        if (title.isEmpty() || selectedType == null || filePath.isEmpty()) {
            Toast.makeText(this, "Title, type, and file are required", Toast.LENGTH_SHORT).show();
            return;
        }


        String publishDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        // Insert content into the database
        long result = databaseConnection.addContent(title, description, selectedType, filePath, publishDate, 1, 1); // Replace 1, 1 with actual ClassID and TeacherID
        if (result != -1) {
            Toast.makeText(this, "Content added successfully!", Toast.LENGTH_LONG).show();
            finish(); // Close activity
        } else {
            Toast.makeText(this, "Failed to add content!", Toast.LENGTH_LONG).show();
        }
    }



}