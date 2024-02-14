package com.policarp.journal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.policarp.journal.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    GsonBuilder builder;
    Gson gson;
    SharedPreferences sharedPref;
    School school;
    SchoolParticipant current;
    ActivityMainBinding binding;
    public static final String TAG = "SchoolData";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        builder = new GsonBuilder();
        sharedPref = getSharedPreferences(TAG, MODE_PRIVATE);
        gson = builder.create();
        school = gson.fromJson(sharedPref.getString(TAG, ""), School.class);
        if(school == null)
            school = new School("Asylum");
        Intent login = new Intent(MainActivity.this, LoginActivity.class);
        login.putExtra(TAG, school.toJson());
        startActivityForResult(login, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK){
            Log.i("INFO", "Got result from login");
            school = gson.fromJson(data.getStringExtra(TAG), School.class);
            current = gson.fromJson(data.getStringExtra(LoginActivity.LOGINEDPARTICIPANT), SchoolParticipant.class);
            binding.Name.setText(current.FullName);
        }
    }

    @Override
    protected void onStop() {
        sharedPref.edit().putString(TAG, school.toJson()).apply();
        super.onStop();
    }
}