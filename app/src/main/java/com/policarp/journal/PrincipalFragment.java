package com.policarp.journal;

import static androidx.fragment.app.DialogFragment.STYLE_NO_FRAME;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.policarp.journal.adapters.UnAttachedStudentsAdapter;
import com.policarp.journal.database.CustomCallBack;
import com.policarp.journal.database.LearningClassApi;
import com.policarp.journal.database.RequestError;
import com.policarp.journal.database.ServerAPI;
import com.policarp.journal.database.response.entities.LearningClassEntity;
import com.policarp.journal.database.response.entities.ParticipantStudentEntity;
import com.policarp.journal.database.response.entities.SchoolEntity;
import com.policarp.journal.database.response.entities.StudentEntity;
import com.policarp.journal.databinding.FragmentPrincipalBinding;

import java.util.ArrayList;
import java.util.List;

public class PrincipalFragment extends FragmentDataSender {
    FragmentPrincipalBinding binding;
    SchoolEntity school;
    ArrayList<ParticipantStudentEntity> classes;
    public PrincipalFragment() {}

    public static PrincipalFragment newInstance(SchoolEntity school) {
        PrincipalFragment fragment = new PrincipalFragment();
        fragment.school = school;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPrincipalBinding.inflate(inflater, container, false);
        CustomCallBack<List<ParticipantStudentEntity>> gotStudents = new CustomCallBack<>(
                (c, r)->{
                    classes = new ArrayList<>(r.body());
                    UnAttachedStudentsAdapter adapter = new UnAttachedStudentsAdapter(classes, (student, pos)->{
                        StudentAttachmentDialog dialog = StudentAttachmentDialog.newInstance(school.getSchoolId(), (classEntity)->{
                            attachStudent(student, classEntity, pos);
                        });
                        dialog.show(getParentFragmentManager(), "Attach");
                        dialog.setStyle(STYLE_NO_FRAME,
                                android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
                    });
                    binding.unAttachedStudents.setAdapter(adapter);
                    binding.unAttachedStudents.setLayoutManager(new LinearLayoutManager(getContext()));
                },null,null
        );
        ServerAPI.getInstance().getStudentApi().getUnattached(school.getSchoolId()).enqueue(gotStudents);
        binding.createClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateClass();
            }
        });
        return binding.getRoot();
    }

    private void attachStudent(ParticipantStudentEntity student, LearningClassEntity classEntity, int pos) {
        CustomCallBack<StudentEntity> updatedStudent = new CustomCallBack<>(
                (c, r)->{
                    Log.i(MainActivity.APPTAG,"Attached student " + r.body().toString());
                    classes.remove(pos);
                    binding.unAttachedStudents.getAdapter().notifyItemRemoved(pos);
                    //binding.unAttachedStudents.getAdapter().notifyDataSetChanged();
                    showToast("Прикрепление успешно" + r.body().getAttachedClassId());
                },(c, r)->showToast(RequestError.handleBadResponse(r)),null
        );
        student.getStudent().setAttachedClassId(classEntity.getClassId());
        ServerAPI.getInstance().getStudentApi().updateStudent(student.getStudent()).enqueue(updatedStudent);
    }

    private void onCreateClass() {
        String className = binding.classCreationName.getText().toString();
        if(className.length() > 5 || className.isEmpty()){
            showToast("Некорректное название класса! Не должно быть пустым или больше 5 символов");
            return;
        }
        CustomCallBack<LearningClassEntity> gotClass = new CustomCallBack<>(
                (c, r)->{
                    Log.i(MainActivity.APPTAG, "Created class " + r.body().toString());
                    showToast("Создан класс");
                    binding.classCreationName.setText("");
                },
                (c, r)->showToast(RequestError.handleBadResponse(r)),null
        );
        LearningClassEntity entity = new LearningClassEntity();
        entity.setSchoolId(school.getSchoolId());
        entity.setClassName(className);
        ServerAPI.getInstance().getLearningClassApi().addClass(entity).enqueue(gotClass);
    }
}