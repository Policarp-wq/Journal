package com.policarp.journal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.policarp.journal.databinding.TeacherFragmentBinding;
import com.policarp.journal.models.LearningClass;
import com.policarp.journal.models.JSONable;
import com.policarp.journal.models.OldSchool;
import com.policarp.journal.models.Teacher;

public class TeacherFragment extends FragmentDataSender{
    OldSchool oldSchool;
    Teacher teacher;
    public static final String SCHOOLARG = "SCHOOLARG";
    public static final String TEACHERARG = "TEACHERARG";
    public static TeacherFragment newInstance(String school, String teacher) {
        Bundle args = new Bundle();
        args.putString(SCHOOLARG, school);
        args.putString(TEACHERARG, teacher);
        TeacherFragment fragment = new TeacherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(getArguments() != null){
//            oldSchool = OldSchool.fromJson(getArguments().getString(SCHOOLARG));
//            teacher = (Teacher) JSONable.fromJSON(getArguments().getString(TEACHERARG), Teacher.class);
//        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TeacherFragmentBinding binding = TeacherFragmentBinding.inflate(inflater, container, false);
//        ArrayAdapter<LearningClass> adapter = new ArrayAdapter<>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, teacher.learningClasses);
//        binding.learningClasses.setAdapter(adapter);
        return binding.getRoot();
    }
}
