package com.policarp.journal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;

import com.policarp.journal.database.CustomCallBack;
import com.policarp.journal.database.SchoolApi;
import com.policarp.journal.database.SchoolParticipantApi;
import com.policarp.journal.database.ServerAPI;
import com.policarp.journal.database.response.entities.SchoolEntity;
import com.policarp.journal.database.response.entities.SchoolParticipantEntity;
import com.policarp.journal.databinding.ActivityMainBinding;
import com.policarp.journal.models.School;

public class MainActivity extends AppCompatActivity {
    public static final String APPTAG = "JOURNALTAG";
    FragmentManager fm;
    SchoolParticipantEntity current;
    ActivityMainBinding binding;
    SchoolParticipantApi participantApi;
    SchoolApi schoolApi;
    private SchoolEntity school;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(APPTAG, "Created MainActivity");
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        participantApi = ServerAPI.getInstance().getSchoolParticipantApi();
        schoolApi = ServerAPI.getInstance().getSchoolApi();
        setContentView(binding.getRoot());
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        init();
    }
    void init(){
        Long participantId = getIntent().getLongExtra(LoginActivity.LOGINEDPARTICIPANTID, -1);
        if(participantId == -1){
            showToast("Wrong participant id, restart the app");
            return;
        }
        CustomCallBack<SchoolEntity> schoolCallBack = new CustomCallBack<>(
                (c, r)->{
                    school = r.body();
                    binding.SchoolName.setText(school.getName());
                    onGotCurrentUser();
                },null,null
        );
        CustomCallBack<SchoolParticipantEntity> callBack = new CustomCallBack<>(
                (c, r)->{
                    current = r.body();
                    schoolApi.getSchoolById(current.getSchoolId()).enqueue(schoolCallBack);
                    binding.Name.setText(current.getName());
                },
                null,null
        );
        participantApi.getById(participantId).enqueue(callBack);
        fm = getSupportFragmentManager();
        initSideBar();
        //navController = Navigation.findNavController(this, R.id.fragmentContainerView);
    }
    void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    private void onGotCurrentUser() {
        School.Position curPos = current.getSchoolPosition();
        if(curPos != School.Position.Guest){
            Fragment fr = null;
            if(curPos == School.Position.Student){
                fr = StudentFragment.newInstance(current);
            }
            else if(curPos == School.Position.Teacher){
                fr = TeacherFragment.newInstance(current, school);
            }
            if(fr != null)
                changeFragment(fr);
        }
        //initSideBar();192.168.177.179
    }

    private void initSideBar() {
        //binding.toolbar.setSubtitle("Navigation toolbar");
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.exitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoff();

            }
        });
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

    private void logoff() {

        Intent restart = new Intent(MainActivity.this, LoginActivity.class);
        restart.putExtra("LOGOFF", true);
        startActivity(restart);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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