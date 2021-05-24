package com.example.sherwoodsuitesaigon.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sherwoodsuitesaigon.Model.ErrorAuthentication;
import com.example.sherwoodsuitesaigon.Model.User;
import com.example.sherwoodsuitesaigon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class SignUpActivity extends AppCompatActivity {

    EditText tfUserName,tfName;
    EditText tfPassword;
    EditText tfConfirmPassword;
    EditText tfPhone;
    Button btnSignUp,btnSignIn;
    ErrorAuthentication errorAuthentication = new ErrorAuthentication();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        this.mapping();
        this.setListeners();
    }

    private void mapping() {
        this.tfName = findViewById(R.id.tfName);
        this.tfUserName = findViewById(R.id.tfUserName);
        this.tfPassword = findViewById(R.id.tfPassword);
        this.tfConfirmPassword = findViewById(R.id.tfConfirmPassword);
        this.tfPhone = findViewById(R.id.tfPhone);
        this.btnSignUp = findViewById(R.id.btnSignUp);
        this.btnSignIn = findViewById(R.id.btnSignIn);
    }

    private void setListeners() {
        btnSignUp.setOnClickListener(v-> {
            if (tfName.getText().toString().matches("") ||
                    tfUserName.getText().toString().matches("") ||
                    tfPassword.getText().toString().matches("") ||
                    tfConfirmPassword.getText().toString().matches("") ||
                    tfPhone.getText().toString().matches("")) {
                Toast.makeText(this, errorAuthentication.getErrorEmpty(),
                        Toast.LENGTH_LONG).show();
            }
            else {
                if(!tfPassword.getText().toString().equals(tfConfirmPassword.getText().toString())) {
                    Toast.makeText(this, errorAuthentication.getErrorPasswordNotEqual(),
                            Toast.LENGTH_LONG).show();
                }
                else {
                    //Authen FireBase;
                    User user = new User("non@ga",tfName.getText().toString(),tfPassword.getText().toString(),tfPhone.getText().toString(),tfUserName.getText().toString());
                    this.checkExistUser(user);
                }

            }
        });

        btnSignIn.setOnClickListener(v-> {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        });
    }

    private void insertUserToFireBase(User user) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("user",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("user").document().set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    editor.putString("name", user.getName());
                    editor.commit();
                    startActivity(new Intent(SignUpActivity.this,HomeActivity.class));
                }
                else{
                    Toast.makeText(SignUpActivity.this,errorAuthentication.getErrorConnect(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void checkExistUser(User user) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("user").whereEqualTo("user_name",user.getUser_name()).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    if (task.getResult().isEmpty()) {
                        Log.d("usercheck","kotontai");
                        db.collection("user").document().set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    startActivity(new Intent(SignUpActivity.this,HomeActivity.class));
                                }
                                else{
                                    Toast.makeText(SignUpActivity.this,errorAuthentication.getErrorConnect(),Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                    else {
                        Log.d("usercheck","tontai");
                        Toast.makeText(SignUpActivity.this,errorAuthentication.getErrorUsernameExist(),Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(SignUpActivity.this,errorAuthentication.getErrorConnect(),Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}