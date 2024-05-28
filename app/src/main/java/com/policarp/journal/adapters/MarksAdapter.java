package com.policarp.journal.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.policarp.journal.R;
import com.policarp.journal.database.response.entities.MarkEntity;
import com.policarp.journal.databinding.OneMarkBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import lombok.val;
import lombok.var;

public class MarksAdapter extends RecyclerView.Adapter<MarksAdapter.MarkViewHolder>{
    ArrayList<MarkEntity> marks;
    ArrayList<MarkEntity> filtered;

    public MarksAdapter(ArrayList<MarkEntity> marks) {
        this.marks = marks;
        filtered = marks;
    }

    public void filter(String subject, Date start, Date end){
        subject = subject.toLowerCase();
        filtered = new ArrayList<>();
        if(start == null || end == null){
            for(var mark : marks){
                if(subject.isEmpty()
                        || mark.getSubjectName().toLowerCase().startsWith(subject))
                    filtered.add(mark);
            }
        }
        else{
            for(var mark : marks){
                if(mark.getMarkDate().after(start) && mark.getMarkDate().before(end)){
                    if(subject.isEmpty())
                        filtered.add(mark);
                    else if(mark.getSubjectName().toLowerCase().startsWith(subject))
                        filtered.add(mark);
                }

            }
        }
        notifyDataSetChanged();
    }

    public double getAverage(){
        if(filtered.isEmpty())
            return 0;
        int sm = 0;
        for(var mark : filtered){
            sm += mark.getVal();
        }
        return 1.0 * sm / filtered.size();
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
        return filtered.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MarkViewHolder holder, int position) {
        //String pattern = "DD/mm/yyyy";
        String pattern = "dd-MM-yyyy HH:mm";
        DateFormat df = new SimpleDateFormat(pattern, Locale.getDefault());
        MarkEntity mark = filtered.get(position);
        holder.binding.markDate.setText(df.format(mark.getMarkDate()));
        holder.binding.markSubject.setText(mark.getSubjectName());
        holder.binding.markVal.setText(Integer.toString(mark.getVal()));
        holder.binding.markVal.setTextColor(getMarkColor(mark.getVal()));
    }

    public static int getMarkColor(Integer val) {
        switch (val){
            case 5:
                return Color.rgb(177, 248, 81);
            case 4:
                return Color.rgb(19, 87, 0);
            case 3:
                return Color.rgb(238, 248, 81);
            case 2:
                return Color.rgb(246, 34, 34);
            default:
                return Color.rgb(30, 190, 254);
        }
    }

    static class MarkViewHolder extends RecyclerView.ViewHolder{
        OneMarkBinding binding;
        public MarkViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = OneMarkBinding.bind(itemView);
        }
    }
}
