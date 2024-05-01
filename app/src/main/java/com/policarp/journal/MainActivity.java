package com.policarp.journal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import com.policarp.journal.database.CustomCallBack;
import com.policarp.journal.database.SchoolParticipantApi;
import com.policarp.journal.database.ServerAPI;
import com.policarp.journal.database.response.entities.SchoolParticipantEntity;
import com.policarp.journal.databinding.ActivityMainBinding;
import com.policarp.journal.models.School;

public class MainActivity extends AppCompatActivity {
    public static final String APPTAG = "JOURNALTAG";
    public static final String GO_TO_MARKS = "Перейти к оценкам";
    FragmentManager fm;
    SchoolParticipantEntity current;
    ActivityMainBinding binding;
    SchoolParticipantApi participantApi;
    public static final String TAG = "SchoolData";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(APPTAG, "Created MainActivity");
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        participantApi = ServerAPI.getInstance().getSchoolParticipantApi();
        init();
        setContentView(binding.getRoot());
    }
    void init(){
//        sharedPref = getSharedPreferences(TAG, MODE_PRIVATE);
//        oldSchool = (OldSchool) JSONable.fromJSON(sharedPref.getString(TAG, ""), OldSchool.class);
////        dba.addSchoolParticipant(school,
////                new SchoolParticipant(new Person("Check"), new UserInfo("pidor", "pidor"),
////                        "13", School.Position.Student));
//        Log.i(APPTAG, "Got School JSON from sp");
////        if(oldSchool == null)
////            oldSchool = new OldSchool("Testin shit");
//        binding.SchoolName.setText("Профиль школы: " + oldSchool.name);
//        binding.deleteData.setOnClickListener(v -> deleteCache());
//        fm = getSupportFragmentManager();
//        binding.exitProfile.setOnClickListener(v -> loginUser());
        loginUser();
    }
    void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void loginUser() {
        Intent login = new Intent(MainActivity.this, LoginActivity.class);
        startActivityForResult(login, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK){
            Log.i("INFO", "Got result from login");
            Long participantId = data.getLongExtra(LoginActivity.LOGINEDPARTICIPANTID, -1);
            if(participantId == -1){
                showToast("Wrong participant id, restart the app");
                return;
            }
            CustomCallBack<SchoolParticipantEntity> callBack = new CustomCallBack<>(
                    (c, r)->{
                        current = r.body();
                        onGotCurrentUser();
                        binding.Name.setText(current.getName());
                    },
                    null,null
            );
            participantApi.getById(participantId).enqueue(callBack);

//
        }
    }

    private void onGotCurrentUser() {
        School.Position curPos = current.getSchoolPosition();
        if(curPos != School.Position.Guest){
            Fragment fr = null;
            if(curPos == School.Position.Student){
                fr = StudentFragment.newInstance(current.getParticipantId());
            }
            else if(curPos == School.Position.Teacher){
//                fr = TeacherFragment.newInstance(oldSchool.toJson(),
//                        JSONable.toJSON(current));
            }
            //if(fr != null)
                //changeFragment(fr);
        }
        //initSideBar();192.168.177.179
    }

    private void initSideBar() {
//        binding.toolbar.setSubtitle("Navigation toolbar");
//        setSupportActionBar(binding.toolbar);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.open, R.string.close);
//        binding.drawerLayout.addDrawerListener(toggle);
//        //binding.navigation.getMenu().addSubMenu("Успеваемость").add(GO_TO_MARKS).setIcon(R.drawable.baseline_looks_5_24);
//        binding.navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Toast.makeText(MainActivity.this, "ДА иди ты нахуй блять", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
//        toggle.syncState();
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
        super.onStop();
    }

}