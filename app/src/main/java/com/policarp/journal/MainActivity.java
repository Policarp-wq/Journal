package com.policarp.journal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.policarp.journal.database.DataBaseAccess;
import com.policarp.journal.databinding.ActivityMainBinding;
import com.policarp.journal.models.JSONable;
import com.policarp.journal.models.School;
import com.policarp.journal.models.SchoolParticipant;
import com.policarp.journal.models.Student;
import com.policarp.journal.models.Teacher;

public class MainActivity extends AppCompatActivity {
    public static final String APPTAG = "JOURNALTAG";
    public static final String GO_TO_MARKS = "Перейти к оценкам";
    FragmentManager fm;
    SharedPreferences sharedPref;
    School school;
    SchoolParticipant current;
    ActivityMainBinding binding;
    public static final String TAG = "SchoolData";
    String DBTAG = "Eqe";
    boolean deleted = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(APPTAG, "Created MainActivity");
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        init();
        setContentView(binding.getRoot());
    }
    void init(){
        sharedPref = getSharedPreferences(TAG, MODE_PRIVATE);
        school = (School) JSONable.fromJSON(sharedPref.getString(TAG, ""), School.class);
        DataBaseAccess dba = new DataBaseAccess();
//        dba.addSchoolParticipant(school,
//                new SchoolParticipant(new Person("Check"), new UserInfo("pidor", "pidor"),
//                        "13", School.Position.Student));
        Log.i(APPTAG, "Got School JSON from sp");
        if(school == null)
            school = new School("Testin shit");
        binding.SchoolName.setText("Профиль школы: " + school.Name);
        binding.deleteData.setOnClickListener(v -> deleteCache());
        fm = getSupportFragmentManager();
        binding.exitProfile.setOnClickListener(v -> loginUser());
        loginUser();
    }
    public void deleteCache(){//TODO: Крашится тута
        if(deleted)
            return;
        if(sharedPref != null)
            sharedPref.edit().clear().apply();
        school = (School) JSONable.fromJSON(sharedPref.getString(TAG, ""), School.class);
        recreate();
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
            String userLogin = data.getStringExtra(LoginActivity.LOGINEDPARTICIPANT);
            school = (School)JSONable.fromJSON(data.getStringExtra(TAG), School.class);
            current = school.getParticipant(userLogin);
            if(current == null){
                Toast.makeText(this, "Не получилось найти пользователя с данным логином", Toast.LENGTH_SHORT).show();
                loginUser();
            }
            onGotCurrentUser();
            binding.Name.setText(current.FullName);
        }
    }

    private void onGotCurrentUser() {
        if(current.Position!= School.Position.Guest){
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
        initSideBar();
    }

    private void initSideBar() {
        binding.toolbar.setSubtitle("Lf blb ns yf[eq");
        setSupportActionBar(binding.toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        binding.navigation.getMenu().addSubMenu("Успеваемость").add(GO_TO_MARKS).setIcon(R.drawable.baseline_looks_5_24);
        binding.navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int x = 0;
                if(item.getTitle().equals(GO_TO_MARKS))
                    Toast.makeText(MainActivity.this, "Перешёл к оценкам", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START))
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

    public void changeFragment(Fragment fragment){
        fm.beginTransaction()
                .add(binding.placeHolder.getId(), fragment)
                .commit();
        Log.i(APPTAG, "Changed fragment");
    }

    @Override
    protected void onStop() {
        saveData();
        super.onStop();
    }
}