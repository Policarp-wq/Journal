package com.policarp.journal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.policarp.journal.databinding.ActivityLoginBinding;
import com.policarp.journal.models.JSONable;
import com.policarp.journal.models.School;
import com.policarp.journal.models.SchoolParticipant;
import com.policarp.journal.models.Student;
import com.policarp.journal.models.UserInfo;

public class LoginActivity extends AppCompatActivity {
    boolean isLogin = true;
    ActivityLoginBinding binding;
    School school;
    public static final String LOGINEDPARTICIPANT = "USER";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        school = School.fromJson(getIntent().getStringExtra(MainActivity.TAG));
        binding.change.setOnClickListener(v -> {
            if(isLogin)
                registerFragmentSelect();
            else loginFragmentSelect();
        });
        loginFragmentSelect();
    }

    private void loginFragmentSelect(){
        LoginFragment frag = LoginFragment.newInstance((m) ->{
            LoginFragment.EnterInfo info = (LoginFragment.EnterInfo)m.obj;
            tryLogin(info.u);
        });
        getSupportFragmentManager().beginTransaction().replace(binding.frame.getId(), frag).commit();
        binding.change.setText("Регистрация");
        isLogin = true;
    }

    private void tryLogin(UserInfo u) {
        SchoolParticipant participant = school.getParticipant(u);
        Log.i("INFO", "Got participant");
        if(participant == null){
            Toast.makeText(this, "Не найден аккаунт с таким логином и паролем!", Toast.LENGTH_SHORT).show();
            return;
        }
        login(participant);
    }
    private void login(SchoolParticipant participant){
        Toast.makeText(this, "Авторизован", Toast.LENGTH_SHORT).show();
        Log.i(MainActivity.APPTAG, "Authorized user" + participant.FullName);
        Intent back = new Intent(LoginActivity.this, MainActivity.class);
        Log.i("INFO", "Sent result");
        String s = JSONable.toJSON(participant);
        back.putExtra(LOGINEDPARTICIPANT, s);
        s = school.toJson();
        back.putExtra(MainActivity.TAG, s);
        setResult(RESULT_OK, back);
        finish();
    }

    public void registerFragmentSelect(){
        RegisterFragment registerFragment =
                RegisterFragment.newInstance(m -> tryRegister((RegisterFragment.Account)m.obj));
        getSupportFragmentManager().beginTransaction().replace(binding.frame.getId(), registerFragment).commit();
        binding.change.setText("Войти");
        isLogin = false;
    }

    private void tryRegister(RegisterFragment.Account account) {
        if(school.containsLogin(account.info.Login)){
            Toast.makeText(this, "В системе уже есть пользователь с данным логином!", Toast.LENGTH_SHORT).show();
            return;
        }
        SchoolParticipant p = school.registerParticipant(account.person, account.info, account.position);
        login(p);
    }

}