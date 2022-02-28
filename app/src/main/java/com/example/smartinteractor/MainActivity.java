package com.example.smartinteractor;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextInputEditText textInputEditText,textInputEditText2,textInputEditText3,textInputEditText4,textInputEditText5,textInputEditText6;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textInputEditText = findViewById(R.id.textInputEditText);
        textInputEditText2 = findViewById(R.id.textInputEditText2);
        textInputEditText3 = findViewById(R.id.textInputEditText3);
        textInputEditText4 = findViewById(R.id.textInputEditText4);
        textInputEditText5 = findViewById(R.id.textInputEditText5);
        textInputEditText6 = findViewById(R.id.textInputEditText6);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text1 = textInputEditText.getText().toString();
                String text2 = textInputEditText2.getText().toString();
                String text3 = textInputEditText3.getText().toString();
                String text4 = textInputEditText4.getText().toString();
                String text5 = textInputEditText5.getText().toString();
                String text6 = textInputEditText6.getText().toString();

                // Create a new user with a first and last name
                Map<String, Object> clientFeedback = new HashMap<>();
                clientFeedback.put("Service Rating: ", text1);
                clientFeedback.put("Complaints: ", text2);
                clientFeedback.put("Improvement Suggestions: ", text3);
                clientFeedback.put("COVID-19 Suggestions: ",text4);
                clientFeedback.put("Name: ", text5);
                clientFeedback.put("Phone: ", text6);

                // Add a new document with a generated ID
                db.collection("Client Feedback")
                        .add(clientFeedback)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });

                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Message Received");
                alertDialog.setMessage("Thank you for your feedback!");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                alertDialog.show();



            }
        });
    }

}