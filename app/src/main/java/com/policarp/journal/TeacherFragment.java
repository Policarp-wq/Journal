package com.policarp.journal;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
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
import com.policarp.journal.models.LearningClass;
import com.policarp.journal.models.JSONable;
import com.policarp.journal.models.OldSchool;
import com.policarp.journal.models.Teacher;

import java.util.ArrayList;
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
        CustomCallBack<TeacherEntity> getTeacher = new CustomCallBack<>(
                (c, r)->{
                    teacher = r.body();
                    Log.i(MainActivity.APPTAG, "Got teacher with id" + teacher.getTeacherId());
                },null, null
        );
        teacherApi.getTeacherByParticipantId(participant.getParticipantId()).enqueue(getTeacher);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TeacherFragmentBinding binding = TeacherFragmentBinding.inflate(inflater, container, false);
        FragmentManager fm = getParentFragmentManager();
        CustomCallBack<List<LearningClassEntity>> callBack = new CustomCallBack<>(
                (c, r)->{
                    adapter = new LearningClassesAdapter(new ArrayList<>(r.body()), (cl) ->{
                        Log.i(MainActivity.APPTAG, "Selected class " + cl.getClassName());
                        ClassFragment classFragment = ClassFragment.newInstance(cl.getClassId(), teacher);
                        fm.beginTransaction()
                                .replace(R.id.placeHolder, classFragment)
                                .addToBackStack("teacher")
                                .commit();

                    });
                    binding.availableClasses.setAdapter(adapter);
                    binding.availableClasses.setLayoutManager(new LinearLayoutManager(getContext()));
                },null,null
        );
        learningClassApi.getSchoolClasses(school.getSchoolId()).enqueue(callBack);

        return binding.getRoot();
    }
}
