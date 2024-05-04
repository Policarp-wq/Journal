package com.policarp.journal.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.policarp.journal.R;
import com.policarp.journal.database.response.entities.LearningClassEntity;
import com.policarp.journal.databinding.OneLearningClassBinding;

import java.util.ArrayList;

public class LearningClassesAdapter extends RecyclerView.Adapter<LearningClassesAdapter.ClassViewHolder> {
    ArrayList<LearningClassEntity> classEntities;
    public interface ClickListener{
        void onClick(LearningClassEntity entity);
    }
    private ClickListener listener;
    public LearningClassesAdapter(ArrayList<LearningClassEntity> classEntities, ClickListener listener) {
        this.classEntities = classEntities;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.one_learning_class, parent, false);
        return new ClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        LearningClassEntity learningClass = classEntities.get(position);
        holder.binding.className.setText(learningClass.getClassName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.onClick(learningClass);
            }
        });
    }

    @Override
    public int getItemCount() {
        return classEntities.size();
    }

    static class ClassViewHolder extends RecyclerView.ViewHolder {
        OneLearningClassBinding binding;
        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = OneLearningClassBinding.bind(itemView);
        }
    }
}
