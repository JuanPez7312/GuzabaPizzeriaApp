package com.example.ejemplofirebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import android.widget.EditText;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RecuperacionActivity extends AppCompatActivity {

    private Button btnRecuperar;
    private EditText editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recuperacion);

        //Linkeamos los valores con la interfaz
        btnRecuperar = findViewById(R.id.btnRecuperar);
        editTextEmail = findViewById(R.id.editTextEmail);

        btnRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString().trim();

                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(RecuperacionActivity.this, "Correo de recuperación enviado", Toast.LENGTH_SHORT).show();
                    } else {
                    Toast.makeText(RecuperacionActivity.this, "Error al enviar el correo electrónico.", Toast.LENGTH_SHORT).show();
                }

                    Intent intent = new Intent(RecuperacionActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                });
            }
        });


    }
}