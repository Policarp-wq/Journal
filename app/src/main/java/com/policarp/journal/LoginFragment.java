package com.policarp.journal;

import android.content.Context;
import android.os.Bundle;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.policarp.journal.database.CustomCallBack;
import com.policarp.journal.database.RequestError;
import com.policarp.journal.database.ServerAPI;
import com.policarp.journal.database.UserApi;
import com.policarp.journal.database.response.entities.SchoolParticipantEntity;
import com.policarp.journal.database.response.entities.UserEntity;
import com.policarp.journal.databinding.FragmentLoginBinding;

public class LoginFragment extends FragmentDataSender {
    FragmentLoginBinding binding;
    OnDataSendListener listener;
    private UserApi userApi;
    public static String PARTICIPANTID = "PARTICIPANTID";
    public LoginFragment() {
    }

    public static LoginFragment newInstance(OnDataSendListener listener) {
        LoginFragment fragment = new LoginFragment();
        fragment.listener = listener;
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userApi = ServerAPI.getInstance().getUserApi();
        Long participantId = checkAuth();
        if(participantId != -1)
            login(participantId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        binding.enter.setOnClickListener(v -> {
            UserEntity u = new UserEntity(binding.login.getText().toString().trim(),
                    binding.password.getText().toString().trim());
            tryLogin(u);

        });
        return binding.getRoot();
    }
    private void tryLogin(UserEntity u) {
        CustomCallBack<SchoolParticipantEntity> callBack = new CustomCallBack<>(
                (call, response) -> {
                    if(binding.rememberAuth.isChecked())
                        saveAuth(response.body().getParticipantId());
                    login(response.body().getParticipantId());
                },
                (c, r) ->{
                    showToast(RequestError.handleBadResponse(r));
                },
                null
        );
        userApi.authUser(u).enqueue(callBack);
    }

    private void login(Long participantId){
        if(listener == null)
            return;
        Message msg = new Message();
        msg.obj = participantId;
        listener.sendMessage(msg);
    }

    private Long checkAuth(){
        return getActivity()
                .getPreferences(Context.MODE_PRIVATE)
                .getLong(PARTICIPANTID, -1);
    }

    private void saveAuth(Long participantId) {
        getActivity()
                .getPreferences(Context.MODE_PRIVATE)
                .edit()
                .putLong(PARTICIPANTID, participantId)
                .apply();
    }
}