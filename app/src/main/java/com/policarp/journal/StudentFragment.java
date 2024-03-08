package com.policarp.journal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.policarp.journal.databinding.FragmentStudentBinding;

public class StudentFragment extends Fragment {
    FragmentStudentBinding binding;
    private static final String SCHOOLPARAM = "SCHOOL";
    private static final String STUDENTPARAM = "STUDENT";
    SubjectsAdapter adapter;

    public School school;
    public Student student;

    public StudentFragment() {
    }

    public static StudentFragment newInstance(String school, String student) {
        StudentFragment fragment = new StudentFragment();
        Bundle args = new Bundle();
        args.putString(SCHOOLPARAM, school);
        args.putString(STUDENTPARAM, student);
        fragment.setArguments(args);
        Log.i(MainActivity.APPTAG, "Created new instance of studentFragment");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            school = (School)JSONable.fromJSON(getArguments().getString(SCHOOLPARAM), School.class);
            student = (Student) JSONable.fromJSON(getArguments().getString(STUDENTPARAM), Student.class);
            adapter = new SubjectsAdapter(student.Subjects);
            Log.i(MainActivity.APPTAG, "Got school and student info");
        }
        Log.i(MainActivity.APPTAG, "Created student fragment");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStudentBinding.inflate(inflater, container, false);
        //binding.name.setText(student.FullName);
        adapter.notifyDataSetChanged();
        binding.subjects.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.subjects.setAdapter(adapter);
        binding.className.setText(student.AttachedClass);
        Log.i(MainActivity.APPTAG, "Created view for student fragment");
        return binding.getRoot();
    }
}