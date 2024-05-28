package com.policarp.journal;

import static androidx.fragment.app.DialogFragment.STYLE_NO_FRAME;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.policarp.journal.adapters.ParticipantStudentsAdapter;
import com.policarp.journal.database.CustomCallBack;
import com.policarp.journal.database.LearningClassApi;
import com.policarp.journal.database.RequestError;
import com.policarp.journal.database.ServerAPI;
import com.policarp.journal.database.response.entities.MarkEntity;
import com.policarp.journal.database.response.entities.ParticipantStudentEntity;
import com.policarp.journal.database.response.entities.TeacherEntity;
import com.policarp.journal.databinding.FragmentClassBinding;
import com.policarp.journal.models.School;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ClassFragment extends FragmentDataSender {
    FragmentClassBinding binding;
    public static String CLASSID = "classid";
    private LearningClassApi learningClassApi;
    private TeacherEntity currentTeacher;
    private School.Subjects selectedSubj;
    public ClassFragment() {
        // Required empty public constructor
    }

    public static ClassFragment newInstance(Long classId, TeacherEntity teacher, School.Subjects subject) {
        Bundle bundle = new Bundle();
        bundle.putLong(CLASSID, classId);
        ClassFragment fragment = new ClassFragment();
        fragment.setArguments(bundle);
        fragment.currentTeacher = teacher;
        fragment.selectedSubj = subject;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        learningClassApi = ServerAPI.getInstance().getLearningClassApi();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentClassBinding.inflate(inflater, container, false);
        binding.selectedSubject.setText(selectedSubj.name());
        CustomCallBack<List<ParticipantStudentEntity>> callBack = new CustomCallBack<>(
                (c, r)->{
                    ParticipantStudentsAdapter adapter = new ParticipantStudentsAdapter(
                            new ArrayList<>(r.body()),
                            (participant, pos)->{
                                StudentInfoDialog dialog = StudentInfoDialog.newInstance(participant,
                                        (mark)-> onGotMark(participant, mark, pos)
                                        );
                                dialog.show(getParentFragmentManager(), "StudentInfo");
                                dialog.setStyle(STYLE_NO_FRAME,
                                        android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
                            }, selectedSubj);
                    binding.classParticipants.setAdapter(adapter);
                    binding.classParticipants.setLayoutManager(new LinearLayoutManager(getContext()));
                }, null, null
        );
        if(getArguments() == null){
            showToast("No args WTF");
        }
        else{
            Long classId = getArguments().getLong(CLASSID);
            learningClassApi.getClassParticipantStudents(classId).enqueue(callBack);
        }
        return binding.getRoot();
    }

    private void onGotMark(ParticipantStudentEntity marked, int mark, int pos) {
        if(!School.isMarkCorrect(mark))
            return;
        setMark(marked.getStudent().getStudentId(), currentTeacher.getTeacherId(), mark, selectedSubj, pos);
//        CustomCallBack<StudentEntity> gotStudent = new CustomCallBack<>(
//                (c, r)->{
//                    setMark(r.body().getStudentId(), currentTeacher.getTeacherId(), mark, subj);
//                }
//                , (c, r)->
//                        showToast("Не удалось получить студента с данным id " + marked.getParticipantId())
//                , null
//        );
//        ServerAPI.getInstance().getStudentApi().getStudentByParticipantId(marked.getParticipantId()).enqueue(gotStudent);
    }

    private void setMark(Long studentId, Long teacherId, int mark, School.Subjects subj, int pos) {
        CustomCallBack<MarkEntity> addedMark = new CustomCallBack<>(
                (c, r)->{
                    showToast("Оценка поставлена: " + mark);
                    Log.i(MainActivity.APPTAG, "Оценка поставлена: " + r.body().toString());
                    binding.classParticipants.getAdapter().notifyItemChanged(pos);
                }
                ,(c, r)-> {
                    showToast(RequestError.handleBadResponse(r) + "Не удалось поставить оценку!");
                }, null
        );
        MarkEntity markEntity = new MarkEntity(subj.name(), mark, studentId, teacherId, new Date());
        ServerAPI.getInstance().getMarkApi().addMark(markEntity).enqueue(addedMark);
    }
}