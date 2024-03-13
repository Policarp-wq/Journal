package com.policarp.journal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.policarp.journal.databinding.FragmentRegisterBinding;
import com.policarp.journal.models.Person;
import com.policarp.journal.models.School;
import com.policarp.journal.models.SchoolParticipant;
import com.policarp.journal.models.UserInfo;

import java.util.ArrayList;

public class RegisterFragment extends FragmentDataSender {
    FragmentRegisterBinding binding;
    OnDataSendListener listener;
    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance(OnDataSendListener listener) {
        RegisterFragment fragment = new RegisterFragment();
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
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        ArrayList<String> positions = new ArrayList<>();
        for(School.Position pos : School.Position.values()){
            positions.add(pos.toString());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, positions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.roleSelector.setAdapter(adapter);
        binding.createAcc.setOnClickListener(v -> {
            if(listener == null)
                return;;
            School.Position position = School.Position.Guest;
            String selected = (String)binding.roleSelector.getSelectedItem();
            for(School.Position pos : School.Position.values()){
                if(pos.toString().equals(selected))
                    position = pos;
            }
            String login = binding.login.getText().toString();
            String password = binding.password.getText().toString();
            String fullName = binding.fullname.getText().toString();
            Account info = new Account(
                    new Person(fullName),
                    new UserInfo(login, password),
                    position);
            Message msg = new Message();
            msg.obj = info;
            listener.sendMessage(msg);
        });
        return binding.getRoot();
    }
    public static class Account{
        Person person;
        UserInfo info;
        School.Position position;

        public Account(Person person, UserInfo info, School.Position position) {
            this.person = person;
            this.info = info;
            this.position = position;
        }
    }
}