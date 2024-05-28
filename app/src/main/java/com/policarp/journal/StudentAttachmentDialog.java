package com.policarp.journal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.policarp.journal.database.CustomCallBack;
import com.policarp.journal.database.ServerAPI;
import com.policarp.journal.database.response.entities.LearningClassEntity;
import com.policarp.journal.databinding.StudentAttachmentDialogBinding;
import com.policarp.journal.models.School;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentAttachmentDialog extends DialogFragment {
    StudentAttachmentDialogBinding binding;
    Long schoolId;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public interface ClickListener{
        void onClick(LearningClassEntity classEntity);
    }
    private ClickListener listener;

    public static StudentAttachmentDialog newInstance(Long schoolId, ClickListener listener) {
        StudentAttachmentDialog fragment = new StudentAttachmentDialog();
        fragment.schoolId = schoolId;
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = StudentAttachmentDialogBinding.inflate(inflater, container, false);
        CustomCallBack<List<LearningClassEntity>> gotClasses = new CustomCallBack<>(
                (c, r)->{
                    ArrayList<LearningClassEntity> classes = new ArrayList<>(r.body());
                    ArrayAdapter<LearningClassEntity> subjAdapter = new ArrayAdapter<>(
                            getContext(), android.R.layout.simple_spinner_item, classes);
                    subjAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.attachmentClassSelector.setAdapter(subjAdapter);
                },null, null
        );
        ServerAPI.getInstance().getLearningClassApi().getSchoolClasses(schoolId).enqueue(gotClasses);
        binding.attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.onClick((LearningClassEntity)binding.attachmentClassSelector.getSelectedItem());
                dismiss();
            }
        });
        return binding.getRoot();
    }
}
