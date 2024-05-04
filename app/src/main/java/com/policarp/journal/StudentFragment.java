package com.policarp.journal;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.policarp.journal.adapters.MarksAdapter;
import com.policarp.journal.database.CustomCallBack;
import com.policarp.journal.database.MarkApi;
import com.policarp.journal.database.ServerAPI;
import com.policarp.journal.database.StudentApi;
import com.policarp.journal.database.response.entities.MarkEntity;
import com.policarp.journal.database.response.entities.SchoolParticipantEntity;
import com.policarp.journal.database.response.entities.StudentEntity;
import com.policarp.journal.databinding.FragmentStudentBinding;
import com.policarp.journal.models.School;

import java.util.ArrayList;
import java.util.List;

public class StudentFragment extends FragmentDataSender {
    FragmentStudentBinding binding;
    private static final String SCHOOLPARAM = "SCHOOL";
    private static final String STUDENTPARAM = "STUDENT";

    public SchoolParticipantEntity participant;
    public StudentEntity student;
    OnDataSendListener sendData;
    private StudentApi studentApi;
    private MarkApi markApi;

    public StudentFragment() {
    }

    public static StudentFragment newInstance(SchoolParticipantEntity participant){
        return newInstance(participant, null);
    }
    public static StudentFragment newInstance(SchoolParticipantEntity participant, OnDataSendListener listener) {
        StudentFragment fragment = new StudentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.sendData = listener;
        fragment.participant = participant;
        Log.i(MainActivity.APPTAG, "Created new instance of studentFragment");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        studentApi = ServerAPI.getInstance().getStudentApi();
        markApi = ServerAPI.getInstance().getMarkApi();
        Log.i(MainActivity.APPTAG, "Created student fragment");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStudentBinding.inflate(inflater, container, false);
        //binding.name.setText(student.FullName);
//        adapter.notifyDataSetChanged();
        CustomCallBack<List<MarkEntity>> markCallBack = new CustomCallBack<>(
                (c, r)->{
                    ArrayList<MarkEntity> arrayList = new ArrayList<>(r.body());
                    MarksAdapter adapter = new MarksAdapter(arrayList);
                    binding.subjects.setAdapter(adapter);
                    binding.subjects.setLayoutManager(new LinearLayoutManager(getContext()));
                },null,null
        );
        CustomCallBack<StudentEntity> callBack = new CustomCallBack<>(
                (c, r)->{
                    student = r.body();
                    binding.className.setText("Ebal tvoyu mat`");
                    markApi.getStudentsMarks(student.getStudentId()).enqueue(markCallBack);
                }, null, null
        );
        studentApi.getStudentByParticipantId(participant.getParticipantId())
                .enqueue(callBack);

        BottomNavigationView navigationView = binding.studentNavigation;
        Log.i(MainActivity.APPTAG, "Created view for student fragment");
        return binding.getRoot();
    }
}