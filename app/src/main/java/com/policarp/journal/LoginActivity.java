package com.policarp.journal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.policarp.journal.database.CustomCallBack;
import com.policarp.journal.database.RequestError;
import com.policarp.journal.database.ServerAPI;
import com.policarp.journal.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private boolean isLogin = true;
    private ActivityLoginBinding binding;
    public static final String LOGINEDPARTICIPANTID = "USER";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.change.setOnClickListener(v -> {
            if(isLogin)
                registerFragmentSelect();
            else loginFragmentSelect();
        });
        if(getIntent().getBooleanExtra("LOGOFF", false)){
            removeSavedAuth();
        }
        loginFragmentSelect();
    }
    void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    private void removeSavedAuth(){
        getPreferences(Context.MODE_PRIVATE).edit().remove(LoginFragment.PARTICIPANTID).apply();
    }
    private void loginFragmentSelect(){
        LoginFragment frag = LoginFragment.newInstance((m) ->{
            Long participantId = (Long)m.obj;
            //Проверка id
            ServerAPI.getInstance()
                    .getSchoolParticipantApi()
                    .getById(participantId)
                    .enqueue(new CustomCallBack<>(
                            (c, r)->login(participantId),
                            (c, r)->removeSavedAuth(),
                            (c, t)->showToast(RequestError.handleFailResponse(c, t))
                    ));
        });
        getSupportFragmentManager().beginTransaction().replace(binding.frame.getId(), frag).commit();
        binding.change.setText("Регистрация");
        isLogin = true;
    }

    public void registerFragmentSelect(){
        RegisterFragment registerFragment =
                RegisterFragment.newInstance(m -> login((Long) m.obj));
        getSupportFragmentManager().beginTransaction().replace(binding.frame.getId(), registerFragment).commit();
        binding.change.setText("Войти");
        isLogin = false;
    }

    private void login(Long participantId){
        Toast.makeText(this, "Авторизован", Toast.LENGTH_SHORT).show();
        Log.i(MainActivity.APPTAG, "Authorized user with participant id" + participantId);
        Intent main = new Intent(LoginActivity.this, MainActivity.class);
        main.putExtra(LOGINEDPARTICIPANTID, participantId);
        startActivity(main);
        finish();
    }

}