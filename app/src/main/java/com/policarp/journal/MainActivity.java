package com.policarp.journal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.policarp.journal.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    GsonBuilder builder;
    Gson gson;
    SharedPreferences sharedPref;
    School school;
    SchoolParticipant current;
    ActivityMainBinding binding;
    ArrayList<String> positions;
    public static final String TAG = "SchoolData";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        positions = new ArrayList<>();
        for(School.Position pos : School.Position.values()){
            positions.add(pos.toString());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, positions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.roleSelector.setAdapter(adapter);

        binding.selectPos.setOnClickListener(v -> {
            String selected = (String)binding.roleSelector.getSelectedItem();
            for(School.Position pos : School.Position.values()){
                if(pos.toString().equals(selected))
                    current.Position = pos;
            }
            school.updateParticipant(current);
            Toast.makeText(this, "Привязали группу к учатснику!", Toast.LENGTH_SHORT).show();
            disableSelection();
            sharedPref.edit().putString(TAG, school.toJson()).apply();


        });

        builder = new GsonBuilder();
        sharedPref = getSharedPreferences(TAG, MODE_PRIVATE);
        gson = builder.create();
        school = gson.fromJson(sharedPref.getString(TAG, ""), School.class);
        //String a = JSONable.toJSON(school);
        //School s = (School)JSONable.fromJSON(a, School.class);
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
            onGotCurrentUser();
            binding.Name.setText(current.FullName);
        }
    }

    private void onGotCurrentUser() {
        if(current.Position!= School.Position.Guest){
            int ind = 0;
            int cur = 0;
            for(String pos : positions){
                if(pos.equals(current.Position.toString())){
                    ind = cur;
                    break;
                }
                ++cur;
            }
            binding.roleSelector.setSelection(ind);
            disableSelection();
        }
    }

    @Override
    protected void onStop() {
        sharedPref.edit().putString(TAG, school.toJson()).apply();
        super.onStop();
    }
    public void disableSelection(){
        binding.roleSelector.setEnabled(false);
        binding.selectPos.setEnabled(false);
        binding.selectPos.setVisibility(View.GONE);
    }
}