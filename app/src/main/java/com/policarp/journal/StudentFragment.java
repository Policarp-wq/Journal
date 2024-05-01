package com.policarp.journal;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.policarp.journal.database.CustomCallBack;
import com.policarp.journal.database.response.entities.StudentEntity;
import com.policarp.journal.databinding.FragmentStudentBinding;
import com.policarp.journal.models.JSONable;
import com.policarp.journal.models.OldSchool;
import com.policarp.journal.models.School;
import com.policarp.journal.models.Student;

public class StudentFragment extends FragmentDataSender {
    FragmentStudentBinding binding;
    private static final String SCHOOLPARAM = "SCHOOL";
    private static final String STUDENTPARAM = "STUDENT";
    SubjectsAdapter adapter;

    public School school;
    public StudentEntity student;
    OnDataSendListener sendData;

    public StudentFragment() {
    }

    public static StudentFragment newInstance(Long participantID){
        return newInstance(participantID, null);
    }
    public static StudentFragment newInstance(Long participantId, OnDataSendListener listener) {
        StudentFragment fragment = new StudentFragment();
        Bundle args = new Bundle();
        args.putLong(STUDENTPARAM, participantId);
        fragment.setArguments(args);
        fragment.sendData = listener;
        Log.i(MainActivity.APPTAG, "Created new instance of studentFragment");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            CustomCallBack<StudentEntity> callBack = new CustomCallBack<>(
                    (c, r)->{
                        student = r.body();
                    }, null, null
            );

            //adapter = new SubjectsAdapter();
            Log.i(MainActivity.APPTAG, "Got school and student info");
        }
        Log.i(MainActivity.APPTAG, "Created student fragment");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStudentBinding.inflate(inflater, container, false);
        //binding.name.setText(student.FullName);
//        adapter.notifyDataSetChanged();
        binding.subjects.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.subjects.setAdapter(adapter);
        binding.className.setText(student.getAttachedClassId());
        Log.i(MainActivity.APPTAG, "Created view for student fragment");
        return binding.getRoot();
    }
}