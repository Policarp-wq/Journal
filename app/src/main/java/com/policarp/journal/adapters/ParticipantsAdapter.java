package com.policarp.journal.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.policarp.journal.R;
import com.policarp.journal.database.response.entities.LearningClassEntity;
import com.policarp.journal.database.response.entities.SchoolParticipantEntity;
import com.policarp.journal.databinding.OneParticipantBinding;

import java.util.ArrayList;

public class ParticipantsAdapter extends RecyclerView.Adapter<ParticipantsAdapter.ParticipantViewHolder> {


    ArrayList<SchoolParticipantEntity> participants;
    public interface ClickListener{
        void onClick(SchoolParticipantEntity entity);
    }
    private ClickListener listener;
    public ParticipantsAdapter(ArrayList<SchoolParticipantEntity> participants, ClickListener listener) {
        this.participants = participants;
        this.listener = listener;
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
        SchoolParticipantEntity schoolParticipantEntity = participants.get(position);
        holder.binding.participantName.setText(schoolParticipantEntity.getName());
        //TODO: Change color and mark
        holder.binding.averageMark.setText("5");
        holder.binding.averageMark.setTextColor(Color.GREEN);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.onClick(schoolParticipantEntity);
            }
        });
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
