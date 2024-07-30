package com.example.ejemplofirebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {


    //Variables que vamos a usar
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button btnRegister;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        //Inicializar el autenticador
        mAuth = FirebaseAuth.getInstance();

        //Linkiar con las vista
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        //Inicializar la base de datos
        db = FirebaseFirestore.getInstance();

        //Agregar funcionalidad al boton de login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                //Enviamos a la vista de login
                Intent intent = new Intent(Register.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Agregar funcionalidad al boton de registrar
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    //Metodo para registrar un usuario
    private void registerUser(){

        //Obtener los datos de la vista
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        int tipoUsuario = 1; //1 --> Usuario normal
                             //0 --> Usuario Admin

        //Validar que todos los datos han sido llenados
        if(name.isEmpty() || email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Por favor, complete todos los campos",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        //Validar que la contraseña es válida
        if(password.length() < 6){
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        //Creamos el usuario
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                //Si el registro es exitoso
                if(task.isSuccessful()){
                    Log.d("Firebase Authentication", "Registro exitoso");

                    //Obtener el usuario actual
                    FirebaseUser user = mAuth.getCurrentUser();

                    //Miramos si el usuario es diferente de nulo
                    if(user != null){

                        //Guardamos los datos del usuario en una colección
                        Map<String, Object> userData = new HashMap<>();
                        userData.put("name", name);
                        userData.put("email", email);
                        userData.put("password", password);
                        userData.put("tipoUsuario", tipoUsuario);

                        //guardamos en la db en una colleccion (si no existe se crea)
                        db.collection("usuarios")
                                .document(user.getUid()) //se linkea con un id unico de usuario
                                .set(userData) //guardamos la data asociada a la id
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d("Firebase Authentication",
                                                "usuario registrado exitosamente");
                                        Toast.makeText(Register.this,
                                                "Registro exitoso", Toast.LENGTH_SHORT).show();
                                    }

                                });

                        //Reiniciamos los edit text para que no muestren nada
                        editTextName.setText("");
                        editTextEmail.setText("");
                        editTextPassword.setText("");

                        //Enviamos a la vista de login
                        Intent intent = new Intent(Register.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                    }


                }else{
                    Log.e("Firebase Authentication", "Error al registrar usuario",
                            task.getException());
                    Toast.makeText(Register.this, "Error al registrar usuario",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}