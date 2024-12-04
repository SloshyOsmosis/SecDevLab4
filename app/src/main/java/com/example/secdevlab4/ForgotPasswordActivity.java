package com.example.secdevlab4;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText editTextEmailAddress, editTextNewPassword, editTextRetypeNewPassword;
    Button updatePassButton;

    DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        editTextEmailAddress = findViewById(R.id.editTextEmailAddress);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextRetypeNewPassword = findViewById(R.id.editTextRetypeNewPassword);

        updatePassButton = findViewById(R.id.updatePassButton);

        myDB = new DBHelper(this);

        updatePassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void resetPassword() {
        String email = editTextEmailAddress.getText().toString().trim();
        String newPassword = editTextNewPassword.getText().toString().trim();
        String retypePassword = editTextRetypeNewPassword.getText().toString().trim();

        //Validation
        if (email.isEmpty() || newPassword.isEmpty() || retypePassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        // Checks if the passwords match
        if (!newPassword.equals(retypePassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }
        // Checks if the user exists
        if (!myDB.checkUserEmail(email)) {
            Toast.makeText(this, "User does not exist", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isUpdated = myDB.updatePassword(email, newPassword);
        if (isUpdated) {
            Toast.makeText(this, "Password updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Password update failed. Please try again.", Toast.LENGTH_SHORT).show();
        }

    }
}