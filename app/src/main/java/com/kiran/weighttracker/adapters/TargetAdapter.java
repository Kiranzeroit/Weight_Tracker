package com.kiran.weighttracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.kiran.weighttracker.R;
import com.kiran.weighttracker.interfaces.ClickListenerInterface;
import com.kiran.weighttracker.modals.TargetModal;

import java.util.ArrayList;
import java.util.List;

public class TargetAdapter extends RecyclerView.Adapter<TargetAdapter.TargetViewHolder> {
    private Context context;
    private List<TargetModal> list;
    private ClickListenerInterface clickListenerInterface;

    private AppCompatTextView tvTime, tvDate, tvWeight, tvCalories;

    public TargetAdapter(Context context, List<TargetModal> list, ClickListenerInterface clickListenerInterface) {
        this.context = context;
        this.list = list;
        this.clickListenerInterface = clickListenerInterface;
    }

    @NonNull
    @Override
    public TargetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_target, parent, false);
        return new TargetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TargetViewHolder holder, int position) {
        holder.bindView(list.get(position),position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TargetViewHolder extends RecyclerView.ViewHolder {
        public TargetViewHolder(@NonNull View itemView) {
            super(itemView);
            initView();
        }

        private void initView() {
            tvTime= itemView.findViewById(R.id.tvTime);
            tvDate= itemView.findViewById(R.id.tvDate);
            tvWeight= itemView.findViewById(R.id.tvWeight);
            tvCalories= itemView.findViewById(R.id.tvCalories);

        }

        private void bindView(TargetModal targetModal, int position) {
            tvTime.setText(targetModal.time);
            tvDate.setText(targetModal.date);
            tvWeight.setText(targetModal.weight);
            tvCalories.setText(targetModal.Calories);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListenerInterface.onClick(targetModal,position);
                }
            });
        }
    }
}