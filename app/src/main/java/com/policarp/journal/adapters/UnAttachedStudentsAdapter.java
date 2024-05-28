package com.policarp.journal.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.policarp.journal.R;
import com.policarp.journal.database.response.entities.ParticipantStudentEntity;
import com.policarp.journal.databinding.OneStudentBinding;

import java.util.ArrayList;

public class UnAttachedStudentsAdapter extends RecyclerView.Adapter<UnAttachedStudentsAdapter.StudentViewHolder> {
    ArrayList<ParticipantStudentEntity> students;
    public interface ClickListener{
        void onClick(ParticipantStudentEntity student, int pos);
    }
    ClickListener listener;

    public UnAttachedStudentsAdapter(ArrayList<ParticipantStudentEntity> students, ClickListener listener) {
        this.students = students;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.one_student, parent, false);
        return new UnAttachedStudentsAdapter.StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        ParticipantStudentEntity student = students.get(position);
        holder.binding.studentName.setText(student.getParticipant().getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.onClick(student, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder{
        OneStudentBinding binding;
        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = OneStudentBinding.bind(itemView);
        }
    }

}
