package com.policarp.journal.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.policarp.journal.R;
import com.policarp.journal.database.response.entities.MarkEntity;
import com.policarp.journal.databinding.OneMarkBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MarksAdapter extends RecyclerView.Adapter<MarksAdapter.MarkViewHolder> {
    ArrayList<MarkEntity> marks = new ArrayList<>();

    public MarksAdapter(ArrayList<MarkEntity> marks) {
        this.marks = marks;
    }

    @NonNull
    @Override
    public MarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.one_mark, parent, false);
        return new MarkViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return marks.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MarkViewHolder holder, int position) {
        String pattern = "DD/mm/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        MarkEntity mark = marks.get(position);
        holder.binding.markDate.setText(df.format(mark.getMarkDate()));
        holder.binding.markSubject.setText(mark.getSubjectName());
        holder.binding.markVal.setText(Integer.toString(mark.getVal()));
    }

    static class MarkViewHolder extends RecyclerView.ViewHolder{
        OneMarkBinding binding;
        public MarkViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = OneMarkBinding.bind(itemView);
        }
    }
}
