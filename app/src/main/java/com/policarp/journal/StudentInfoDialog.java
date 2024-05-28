package com.policarp.journal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.policarp.journal.database.response.entities.ParticipantStudentEntity;
import com.policarp.journal.database.response.entities.SchoolParticipantEntity;
import com.policarp.journal.databinding.StudentInfoDialogBinding;
import com.policarp.journal.models.School;

public class StudentInfoDialog extends DialogFragment{
    StudentInfoDialogBinding binding;
    private ParticipantStudentEntity student;
    public interface MarkConfirm{
        void onMarkConfirm(int mark);
    }
    private MarkConfirm listener;
    public static StudentInfoDialog newInstance(ParticipantStudentEntity student, MarkConfirm listener) {
        StudentInfoDialog fragment = new StudentInfoDialog();
        fragment.student = student;
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = StudentInfoDialogBinding.inflate(inflater, container, false);
        binding.studentName.setText(student.getParticipant().getName());
        getDialog().setCancelable(true);
        //getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        binding.confirmMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.settedMark.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Оценка не поставлена!", Toast.LENGTH_SHORT).show();
                    return;
                }
                int mark = Integer.parseInt(binding.settedMark.getText().toString());
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
