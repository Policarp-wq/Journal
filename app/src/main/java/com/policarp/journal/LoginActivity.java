package com.policarp.journal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.policarp.journal.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    School school;
    public static final String LOGINEDPARTICIPANT = "USER";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        school = School.fromJson(getIntent().getStringExtra(MainActivity.TAG));
        binding.enter.setOnClickListener(v -> {
           String login = binding.login.getText().toString();
           String password = binding.password.getText().toString();
           SchoolParticipant participant = school.getParticipant(login, password);
           Log.i("INFO", "Got participant");
           if(participant == null){
               Toast.makeText(this, "Создан новый аккаунт!", Toast.LENGTH_SHORT).show();
               participant = school.registerParticipant(login);
               school.registerUser(login, password, participant);
           }
           else{
               Toast.makeText(this, "Авторизован", Toast.LENGTH_SHORT).show();
           }
           try{
               Intent back = new Intent(LoginActivity.this, MainActivity.class);
               GsonBuilder builder = new GsonBuilder();
               Gson gson = builder.create();
               Log.i("INFO", "Sent result");
               builder = new GsonBuilder();
               gson = builder.create();
               String s = gson.toJson(participant);
               back.putExtra(LOGINEDPARTICIPANT, s);
               s = school.toJson();
               back.putExtra(MainActivity.TAG, s);
               setResult(RESULT_OK, back);
               Log.i("INFO", "Sent result");
           }catch (Exception ex){
               Log.e("EX",ex.getMessage());
           }

           finish();
        });

    }
}