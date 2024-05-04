package com.policarp.journal;

import android.os.Bundle;

import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    //TODO:Unexpected variable behaviour
    private SchoolEntity selectedSchool = null;
    private ArrayAdapter<SchoolEntity> adapter;
    private boolean createdSchool = false;
    public RegisterFragment() {}

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);

        ArrayList<SchoolEntity> schools = new ArrayList<>();
        CustomCallBack<List<SchoolEntity>> callBack = new CustomCallBack<>(
                (c, r) ->{
                    schools.addAll(r.body());
                    adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, schools);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.schoolSelector.setAdapter(adapter);
                    if(!schools.isEmpty())
                        selectedSchool = schools.get(0);
                },
                (c, r) -> {
                    showToast(RequestError.handleBadResponse(r));
                }, (c, t) -> {
                    showToast(RequestError.handleFailResponse(c, t));
                }
        );
        schoolApi.getAllSchools().enqueue(callBack);

        ArrayList<String> positions = new ArrayList<>();
        for(School.Position pos : School.Position.values()){
            positions.add(pos.toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, positions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.roleSelector.setAdapter(adapter);
        binding.roleSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String)parent.getItemAtPosition(position);
                if(selected.equals(School.Position.Principal.name())){
                    binding.schoolCreationName.setText("");
                    binding.schoolCreationName.setVisibility(View.VISIBLE);
                    binding.schoolSelector.setVisibility(View.GONE);
                }
                else if(!createdSchool){
                    binding.schoolCreationName.setVisibility(View.GONE);
                    binding.schoolSelector.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.schoolSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSchool = (SchoolEntity)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        binding.createAcc.setOnClickListener(v -> {
            if(listener == null)
                return;
            beginRegistration();
            //tryRegister(getAccFromUI());
        });
        return binding.getRoot();
    }

    private void beginRegistration() {
        String selectedPos = (String) binding.roleSelector.getSelectedItem();
        if(selectedPos.equals(School.Position.Principal.name()) && !createdSchool){
            createSchool();
        }else tryRegister(getAccFromUI());
    }
    private void createSchool() {
        CustomCallBack<SchoolEntity> callBack = new CustomCallBack<>(
                (c,r)->{
                    selectedSchool = r.body();
                    adapter.add(selectedSchool);
                    adapter.notifyDataSetChanged();
                    binding.schoolSelector.setAdapter(adapter);
                    binding.schoolSelector.setSelection(adapter.getPosition(selectedSchool));
                    Log.i(MainActivity.APPTAG, "Created school");
                    showToast("Школа была создана");
                    createdSchool = true;
                    tryRegister(getAccFromUI());
                },
                (c, r)->{
                    showToast(RequestError.handleBadResponse(r));
                    },
                null
        );
        SchoolEntity entity = new SchoolEntity();
        entity.setName(binding.schoolCreationName.getText().toString());
        schoolApi.addSchool(entity).enqueue(callBack);
    }


    private Account getAccFromUI(){
        String selectedPos = (String) binding.roleSelector.getSelectedItem();
        //SchoolEntity school = (SchoolEntity)binding.schoolSelector.getSelectedItem();
        SchoolEntity school = selectedSchool;
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