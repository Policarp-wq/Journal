package com.policarp.journal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.policarp.journal.database.CustomCallBack;
import com.policarp.journal.database.RequestError;
import com.policarp.journal.database.ServerAPI;
import com.policarp.journal.database.UserApi;
import com.policarp.journal.database.response.entities.SchoolParticipantEntity;
import com.policarp.journal.database.response.entities.UserEntity;
import com.policarp.journal.databinding.FragmentLoginBinding;
import com.policarp.journal.models.UserInfo;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends FragmentDataSender {
    FragmentLoginBinding binding;
    OnDataSendListener listener;
    private UserApi userApi;
    public LoginFragment() {
    }

    public static LoginFragment newInstance(OnDataSendListener listener) {
        LoginFragment fragment = new LoginFragment();
        fragment.listener = listener;
        return fragment;
    }
    void showToast(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userApi = ServerAPI.getInstance().getUserApi();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        binding.enter.setOnClickListener(v -> {

            UserEntity u = new UserEntity(binding.login.getText().toString(),
                    binding.password.getText().toString());
            tryLogin(u);

        });
        return binding.getRoot();
    }
    private void tryLogin(UserEntity u) {
        CustomCallBack<SchoolParticipantEntity> callBack = new CustomCallBack<>(
                (call, response) -> {
                    if(listener == null)
                        return;
                    Message msg = new Message();
                    msg.obj = response.body().getParticipantId();
                    listener.sendMessage(msg);
                },
                (c, r) ->{
                    showToast(RequestError.handleBadResponse(r));
                },
                null
        );
        userApi.authUser(u).enqueue(callBack);
    }
}