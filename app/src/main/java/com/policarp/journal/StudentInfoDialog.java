package com.policarp.journal;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.policarp.journal.database.response.entities.SchoolEntity;
import com.policarp.journal.database.response.entities.SchoolParticipantEntity;
import com.policarp.journal.database.response.entities.StudentEntity;
import com.policarp.journal.databinding.StudentInfoDialogBinding;
import com.policarp.journal.models.School;

import java.util.ArrayList;
import java.util.Arrays;

public class StudentInfoDialog extends DialogFragment{
    StudentInfoDialogBinding binding;
    private SchoolParticipantEntity student;
    public static interface MarkConfirm{
        void onMarkConfirm(int mark);
    }
    private MarkConfirm listener;
    public static StudentInfoDialog newInstance(SchoolParticipantEntity student, MarkConfirm listener) {
        StudentInfoDialog fragment = new StudentInfoDialog();
        fragment.student = student;
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = StudentInfoDialogBinding.inflate(inflater, container, false);
        binding.studentName.setText(student.getName());
        getDialog().setCancelable(true);
        //getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        binding.confirmMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.settedMark.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Оценка не поставлена!", Toast.LENGTH_SHORT).show();
                    return;
                }
                int mark = Integer.valueOf(binding.settedMark.getText().toString());
                if(!School.isMarkCorrect(mark)){
                    Toast.makeText(getContext(), "Некорректная оценка!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(listener != null)
                    listener.onMarkConfirm(mark);
                dismiss();
            }
        });
        return binding.getRoot();
    }
}
