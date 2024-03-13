package com.policarp.journal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        binding.enter.setOnClickListener(v -> {
            if(listener == null)
                return;
            UserInfo u = new UserInfo(binding.login.toString(), binding.password.toString());
            EnterInfo info = new EnterInfo(u);
            Message msg = new Message();
            msg.obj = info;
            listener.sendMessage(msg);
        });
        /*binding.createAcc.setOnClickListener(v -> {
            if(listener == null)
                return;
            EnterInfo info = new EnterInfo(null, true);
            Message msg = new Message();
            msg.obj = info;
            listener.sendMessage(msg);
        });*/
        return binding.getRoot();
    }
    public static class EnterInfo{
        UserInfo u;
        public EnterInfo(UserInfo u) {
            this.u = u;
        }
    }
}