package com.policarp.journal;

import static androidx.fragment.app.DialogFragment.STYLE_NO_FRAME;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.type.DateTime;
import com.policarp.journal.adapters.ParticipantsAdapter;
import com.policarp.journal.database.CustomCallBack;
import com.policarp.journal.database.LearningClassApi;
import com.policarp.journal.database.RequestError;
import com.policarp.journal.database.ServerAPI;
import com.policarp.journal.database.response.entities.MarkEntity;
import com.policarp.journal.database.response.entities.SchoolParticipantEntity;
import com.policarp.journal.database.response.entities.StudentEntity;
import com.policarp.journal.database.response.entities.TeacherEntity;
import com.policarp.journal.databinding.FragmentClassBinding;
import com.policarp.journal.models.School;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import lombok.val;

public class ClassFragment extends FragmentDataSender {
    FragmentClassBinding binding;
    public static String CLASSID = "classid";
    private LearningClassApi learningClassApi;
    private TeacherEntity currentTeacher;
    public ClassFragment() {
        // Required empty public constructor
    }

    public static ClassFragment newInstance(Long classId, TeacherEntity teacher) {
        Bundle bundle = new Bundle();
        bundle.putLong(CLASSID, classId);
        ClassFragment fragment = new ClassFragment();
        fragment.setArguments(bundle);
        fragment.currentTeacher = teacher;
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

        // TODO:Specify for class
        ArrayList<School.Subjects> subjects = new ArrayList<>(Arrays.asList(School.Subjects.values()));
        ArrayAdapter<School.Subjects> subjAdapter = new ArrayAdapter<>(
                getContext(), android.R.layout.simple_spinner_item, subjects);
        subjAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.subjectSelector.setAdapter(subjAdapter);

        CustomCallBack<List<SchoolParticipantEntity>> callBack = new CustomCallBack<>(
                (c, r)->{
                    ParticipantsAdapter adapter = new ParticipantsAdapter(
                            new ArrayList<>(r.body()),
                            (participant)->{
                                StudentInfoDialog dialog = StudentInfoDialog.newInstance(participant,
                                        (mark)-> onGotMark(participant, mark)
                                        );
                                dialog.show(getParentFragmentManager(), "Jopka");
                                dialog.setStyle(STYLE_NO_FRAME, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
                            });
                    binding.classParticipants.setAdapter(adapter);
                    binding.classParticipants.setLayoutManager(new LinearLayoutManager(getContext()));
                }, null, null
        );
        if(getArguments() == null){
            showToast("No args WTF");
        }
        else{
            Long classId = getArguments().getLong(CLASSID);
            learningClassApi.getClassParticipants(classId).enqueue(callBack);
        }
        return binding.getRoot();
    }

    private void onGotMark(SchoolParticipantEntity marked, int mark) {
        if(!School.isMarkCorrect(mark))
            return;
        School.Subjects subj = (School.Subjects)binding.subjectSelector.getSelectedItem();
        CustomCallBack<StudentEntity> gotStudent = new CustomCallBack<>(
                (c, r)->{
                    setMark(r.body().getStudentId(), currentTeacher.getTeacherId(), mark, subj);
                }
                , (c, r)->
                        showToast("Не удалось получить студента с данным id " + marked.getParticipantId())
                , null
        );
        ServerAPI.getInstance().getStudentApi().getStudentByParticipantId(marked.getParticipantId()).enqueue(gotStudent);
    }

    private void setMark(Long studentId, Long teacherId, int mark, School.Subjects subj) {
        CustomCallBack<MarkEntity> addedMark = new CustomCallBack<>(
                (c, r)->{
                    showToast("Оценка поставлена: " + mark);
                    Log.i(MainActivity.APPTAG, "Оценка поставлена: " + r.body().toString());
                }
                ,(c, r)-> {
                    showToast(RequestError.handleBadResponse(r) + "Не удалось поставить оценку!");
                }, null
        );
        // TODO:Make dates normal
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        MarkEntity markEntity = new MarkEntity(subj.name(), mark, studentId, teacherId, formater.format(new Date()));
        ServerAPI.getInstance().getMarkApi().addMark(markEntity).enqueue(addedMark);
    }
}