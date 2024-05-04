package com.policarp.journal.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.policarp.journal.R;
import com.policarp.journal.databinding.OneSubjectBinding;
import com.policarp.journal.models.SubjectStatistic;

import java.util.ArrayList;

public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.SubjectViewHolder> {
    ArrayList<SubjectStatistic> subjectStatistics = new ArrayList<>();

    public SubjectsAdapter(ArrayList<SubjectStatistic> subjectStatistics) {
        this.subjectStatistics = subjectStatistics;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.one_subject, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        SubjectStatistic subjectStatistic = subjectStatistics.get(position);
        holder.binding.subjName.setText(subjectStatistic.Name.name());
        holder.binding.marks.setText(subjectStatistic.getMarks());
        holder.binding.average.setText(String.format("%.2f", subjectStatistic.getAverage()));
    }

    @Override
    public int getItemCount() {
        return subjectStatistics != null ? subjectStatistics.size() : 0;
    }

    static class SubjectViewHolder extends RecyclerView.ViewHolder {
        OneSubjectBinding binding;
        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = OneSubjectBinding.bind(itemView);
        }
    }
}
