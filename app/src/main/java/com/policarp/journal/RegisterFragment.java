package com.policarp.journal;

import android.os.Bundle;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.policarp.journal.database.CustomCallBack;
import com.policarp.journal.database.RequestError;
import com.policarp.journal.database.SchoolApi;
import com.policarp.journal.database.SchoolParticipantApi;
import com.policarp.journal.database.ServerAPI;
import com.policarp.journal.database.UserApi;
import com.policarp.journal.database.response.entities.SchoolEntity;
import com.policarp.journal.database.response.entities.SchoolParticipantEntity;
import com.policarp.journal.database.response.entities.UserEntity;
import com.policarp.journal.databinding.FragmentRegisterBinding;
import com.policarp.journal.models.Person;
import com.policarp.journal.models.School;
import com.policarp.journal.models.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends FragmentDataSender {
    FragmentRegisterBinding binding;
    OnDataSendListener listener;

    private UserApi userApi;
    private SchoolParticipantApi participantApi;
    private SchoolApi schoolApi;
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
        schoolApi = ServerAPI.getInstance().getSchoolApi();
        userApi = ServerAPI.getInstance().getUserApi();
        participantApi = ServerAPI.getInstance().getSchoolParticipantApi();
    }

    void showToast(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);

        ArrayList<String> positions = new ArrayList<>();
        for(School.Position pos : School.Position.values()){
            positions.add(pos.toString());
        }

        ArrayList<SchoolEntity> schools = new ArrayList<>();
        CustomCallBack<List<SchoolEntity>> callBack = new CustomCallBack<>(
                (c, r) ->{
                    schools.addAll(r.body());
                    ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, schools);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.schoolSelector.setAdapter(adapter);
                },
                null, null
        );
        schoolApi.getAllSchools().enqueue(callBack);

        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, positions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.roleSelector.setAdapter(adapter);

        binding.createAcc.setOnClickListener(v -> {
            if(listener == null)
                return;

            tryRegister(getAccFromUI());
        });
        return binding.getRoot();
    }

    private Account getAccFromUI(){
        String selectedPos = (String) binding.roleSelector.getSelectedItem();
        SchoolEntity school = (SchoolEntity)binding.schoolSelector.getSelectedItem();
        String login = binding.login.getText().toString();
        String password = binding.password.getText().toString();
        String fullName = binding.fullname.getText().toString();
        UserEntity user = new UserEntity();
        user.setLogin(login);
        user.setHash(password.hashCode());
        SchoolParticipantEntity participant = new SchoolParticipantEntity();
        participant.setName(fullName);
        participant.setPosition(selectedPos);
        participant.setSchoolId(school.getSchoolId());
        return new Account(participant, user);
    }

    public static class Account{
        public Account(SchoolParticipantEntity participant, UserEntity user) {
            this.participant = participant;
            this.user = user;
        }

        public SchoolParticipantEntity participant;
        public UserEntity user;
    }

    private void tryRegister(Account account) {
        CustomCallBack<UserEntity> callBack = new CustomCallBack<>(
                (c, r) ->{
                    account.user = r.body();
                    registerParticipant(account);
                },
                (c, r) -> {
                    showToast(RequestError.handleBadResponse(r));
                    },
                (c, r) -> {
                    showToast(RequestError.handleFailResponse(c, r));
                }
        );

        userApi.addUser(account.user).enqueue(callBack);
    }

    private void registerParticipant(Account account) {
        CustomCallBack<SchoolParticipantEntity> callBack = new CustomCallBack<>(
                (c, r)->{
                    showToast("Registered new participant" + r.body().getName() + "with user" + account.user.getLogin());
                    Message msg = new Message();
                    msg.obj = r.body().getParticipantId();
                    listener.sendMessage(msg);
                },
                (c, r)->{
                    showToast("Bad on registration " + RequestError.handleBadResponse(r));
                },
                (c, r) -> {
                    showToast(RequestError.handleFailResponse(c, r));
                }
        );
        participantApi.add(account.user.getUserId(), account.participant).enqueue(callBack);
    }
}