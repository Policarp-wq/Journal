package com.policarp.journal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
    public static final String APPTAG = "JOURNALTAG";
    FragmentManager fm;
    FragmentTransaction ft;
    SharedPreferences sharedPref;
    School school;
    SchoolParticipant current;
    ActivityMainBinding binding;
    ArrayList<String> positions;
    public static final String TAG = "SchoolData";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(APPTAG, "Created MainActivity");
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        init();
        setContentView(binding.getRoot());
    }
    void init(){
        sharedPref = getSharedPreferences(TAG, MODE_PRIVATE);

        school = (School)JSONable.fromJSON(sharedPref.getString(TAG, ""), School.class);
        Log.i(APPTAG, "Got School JSON from sp");
        if(school == null)
            school = new School("Testin shit");
        binding.SchoolName.setText("Профиль школы: " + school.Name);
        fm = getSupportFragmentManager();

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
            current = school.updateParticipant(current);
            onGotCurrentUser();
            Log.i(APPTAG, "Selection applied to " + current.FullName);
            Toast.makeText(this, "Привязали группу к учатснику!", Toast.LENGTH_SHORT).show();
        });

        binding.exitProfile.setOnClickListener(v -> {
            loginUser();
        });

        loginUser();
    }
    public void deleteCache(){
        if(sharedPref != null)
            sharedPref.edit().clear().apply();
    }

    public void loginUser(){
        saveData();
        Intent login = new Intent(MainActivity.this, LoginActivity.class);
        login.putExtra(TAG, school.toJson());
        startActivityForResult(login, 1);
    }
    public void saveData(){
        sharedPref.edit().putString(TAG, school.toJson()).apply();
        Log.i(APPTAG, "Saved data");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK){
            Log.i("INFO", "Got result from login");
            school = (School)JSONable.fromJSON(data.getStringExtra(TAG), School.class);
            current = (SchoolParticipant)
                    JSONable.fromJSON(data.getStringExtra(LoginActivity.LOGINEDPARTICIPANT), SchoolParticipant.class);
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
            Log.i(APPTAG, "Set selection for current user");
            disableSelection();
            Fragment fr = null;
            if(current.Position == School.Position.Student){
                fr = StudentFragment.newInstance(school.toJson(),
                        JSONable.toJSON((Student)current));
            }
            else if(current.Position == School.Position.Teacher){
                fr = TeacherFragment.newInstance(school.toJson(),
                        JSONable.toJSON(current));
            }
            if(fr != null)
                changeFragment(fr);
        }
    }

    public void changeFragment(Fragment fragment){
        ft = fm.beginTransaction();
        ft.add(binding.placeHolder.getId(), fragment);
        ft.commit();
        Log.i(APPTAG, "Changed fragment to");
    }

    @Override
    protected void onStop() {
        saveData();
        super.onStop();
    }
    public void disableSelection(){
        binding.roleSelector.setEnabled(false);
        binding.selectPos.setEnabled(false);
        binding.selectPos.setVisibility(View.GONE);
        Log.i(APPTAG, "Disabled selection");
    }
}