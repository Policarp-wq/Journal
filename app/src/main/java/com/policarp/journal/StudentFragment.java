package com.policarp.journal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StudentFragment extends Fragment {

    private static final String SCHOOLPARAM = "SCHOOL";
    private static final String STUDENTPARAM = "STUDENT";

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
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //school = getArguments().getString(SCHOOLPARAM);
           // student = getArguments().getString(STUDENTPARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student, container, false);
    }
}