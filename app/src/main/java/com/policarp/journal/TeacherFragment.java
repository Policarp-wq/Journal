package com.policarp.journal;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.policarp.journal.adapters.LearningClassesAdapter;
import com.policarp.journal.database.CustomCallBack;
import com.policarp.journal.database.LearningClassApi;
import com.policarp.journal.database.ServerAPI;
import com.policarp.journal.database.TeacherApi;
import com.policarp.journal.database.response.entities.LearningClassEntity;
import com.policarp.journal.database.response.entities.SchoolEntity;
import com.policarp.journal.database.response.entities.SchoolParticipantEntity;
import com.policarp.journal.database.response.entities.TeacherEntity;
import com.policarp.journal.databinding.TeacherFragmentBinding;
import com.policarp.journal.models.School;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeacherFragment extends FragmentDataSender{
    private LearningClassesAdapter adapter;
    private TeacherEntity teacher;
    private SchoolEntity school;
    private SchoolParticipantEntity participant;
    private LearningClassApi learningClassApi;
    private TeacherApi teacherApi;
    public static TeacherFragment newInstance(SchoolParticipantEntity participant, SchoolEntity schoolEntity) {
        TeacherFragment fragment = new TeacherFragment();
        fragment.school = schoolEntity;
        fragment.participant = participant;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        learningClassApi = ServerAPI.getInstance().getLearningClassApi();
        teacherApi = ServerAPI.getInstance().getTeacherApi();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TeacherFragmentBinding binding = TeacherFragmentBinding.inflate(inflater, container, false);
        FragmentManager fm = getParentFragmentManager();
        // TODO: Specify for class
        ArrayList<School.Subjects> subjects = new ArrayList<>(Arrays.asList(School.Subjects.values()));
        ArrayAdapter<School.Subjects> subjAdapter = new ArrayAdapter<>(
                getContext(), android.R.layout.simple_spinner_item, subjects);
        subjAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.subjectSelector.setAdapter(subjAdapter);
        CustomCallBack<List<LearningClassEntity>> callBack = new CustomCallBack<>(
                (c, r)->{
                    adapter = new LearningClassesAdapter(new ArrayList<>(r.body()), (cl) ->{
                        Log.i(MainActivity.APPTAG, "Selected class " + cl.getClassName());
                        if(teacher == null)
                            return;
                        ClassFragment classFragment = ClassFragment.newInstance(cl.getClassId(), teacher,
                                ((School.Subjects) binding.subjectSelector.getSelectedItem()));
                        fm.beginTransaction()
                                .replace(R.id.placeHolder, classFragment)
                                .addToBackStack("teacher")
                                .commit();

                    });
                    binding.availableClasses.setAdapter(adapter);
                    binding.availableClasses.setLayoutManager(new LinearLayoutManager(getContext()));
                },null,null
        );
        CustomCallBack<TeacherEntity> getTeacher = new CustomCallBack<>(
                (c, r)->{
                    teacher = r.body();
                    learningClassApi.getSchoolClasses(school.getSchoolId()).enqueue(callBack);
                    Log.i(MainActivity.APPTAG, "Got teacher with id" + teacher.getTeacherId());
                },null, null
        );
        teacherApi.getTeacherByParticipantId(participant.getParticipantId()).enqueue(getTeacher);
        return binding.getRoot();
    }
}
