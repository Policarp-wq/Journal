package com.policarp.journal.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.policarp.journal.R;
import com.policarp.journal.database.CustomCallBack;
import com.policarp.journal.database.ServerAPI;
import com.policarp.journal.database.response.entities.MarkEntity;
import com.policarp.journal.database.response.entities.ParticipantStudentEntity;
import com.policarp.journal.database.response.entities.SchoolParticipantEntity;
import com.policarp.journal.databinding.OneParticipantBinding;
import com.policarp.journal.models.School;

import java.util.ArrayList;
import java.util.List;

public class ParticipantStudentsAdapter extends RecyclerView.Adapter<ParticipantStudentsAdapter.ParticipantViewHolder> {


    ArrayList<ParticipantStudentEntity> participants;
    public interface ClickListener{
        void onClick(ParticipantStudentEntity entity, int pos);
    }
    private ClickListener listener;
    private final School.Subjects selectedSubj;

    public ParticipantStudentsAdapter(ArrayList<ParticipantStudentEntity> participants, ClickListener listener, School.Subjects selectedSubj) {
        this.participants = participants;
        this.listener = listener;
        this.selectedSubj = selectedSubj;
    }
    @NonNull
    @Override
    public ParticipantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.one_participant, parent, false);
        return new ParticipantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipantViewHolder holder, int position) {
        ParticipantStudentEntity schoolParticipantEntity = participants.get(position);
        holder.binding.participantName.setText(schoolParticipantEntity.getParticipant().getName());
        CustomCallBack<List<MarkEntity>> gotMarks = new CustomCallBack<>(
                (c, r)->{
                    double average = getAverage(r.body());
                    holder.binding.averageMark.setText(String.format("%,.2f", average));
                    holder.binding.averageMark.setTextColor(MarksAdapter.getMarkColor((int)Math.round(average)));
                },null, null
        );
        ServerAPI.getInstance().getMarkApi().getMarksByStudentAndSubject(
                schoolParticipantEntity.getStudent().getStudentId(), selectedSubj.name()).enqueue(gotMarks);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.onClick(schoolParticipantEntity, holder.getAdapterPosition());
            }
        });
    }

    private double getAverage(List<MarkEntity> body) {
        if(body.isEmpty())
            return 0;
        double res = 0;
        for(MarkEntity mark : body){
            res +=mark.getVal();
        }
        return res / body.size();
    }

    @Override
    public int getItemCount() {
        return participants.size();
    }

    static class ParticipantViewHolder extends RecyclerView.ViewHolder{
        OneParticipantBinding binding;
        public ParticipantViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = OneParticipantBinding.bind(itemView);
        }
    }
}
