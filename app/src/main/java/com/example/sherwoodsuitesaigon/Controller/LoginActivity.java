package com.example.sherwoodsuitesaigon.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sherwoodsuitesaigon.Model.ErrorAuthentication;
import com.example.sherwoodsuitesaigon.Model.User;
import com.example.sherwoodsuitesaigon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    EditText tfUser;
    EditText tfPassword;
    Button btnSignIn;
    Button btnSignUp;
    ErrorAuthentication errorAuthentication = new ErrorAuthentication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mapping();
        this.setListeners();
    }

    private void mapping() {
        this.tfUser = findViewById(R.id.tfUserName);
        this.tfPassword = findViewById(R.id.tfPassword);
        this.btnSignIn = findViewById(R.id.btnSignIn);
        this.btnSignUp = findViewById(R.id.btnSignUp);
    }

    public void setListeners() {
        btnSignIn.setOnClickListener(v -> {
            if (tfUser.getText().toString().matches("") || tfPassword.getText().toString().matches("")) {
                Toast.makeText(this, errorAuthentication.getErrorEmpty(),
                        Toast.LENGTH_LONG).show();
            }
            else {
                //Authen FireBase;
                checkFireBaseUser(tfUser.getText().toString(),tfPassword.getText().toString());
            }
        });

        btnSignUp.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
        });
    }

    private void checkFireBaseUser(String userName , String password) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("user",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("user").whereEqualTo("user_name",userName).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    if (task.getResult().isEmpty()) {
                        Toast.makeText(LoginActivity.this, errorAuthentication.getErrorLoginFail(),
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        for(QueryDocumentSnapshot document : task.getResult()) {
                            User user = document.toObject(User.class);
                            if (user.getPassword().equals(password)) {
                                editor.putString("name", user.getName());
                                editor.commit();
                                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                            }
                            else {
                                Toast.makeText(LoginActivity.this, errorAuthentication.getErrorLoginFail(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                }
                else {
                    Toast.makeText(LoginActivity.this, errorAuthentication.getErrorConnect(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }




}