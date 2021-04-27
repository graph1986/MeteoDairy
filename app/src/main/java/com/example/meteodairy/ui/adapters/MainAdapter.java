package com.example.meteodairy.ui.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meteodairy.R;
import com.example.meteodairy.models.DayMeteo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private List<DayMeteo> days;

    public MainAdapter(List<DayMeteo> days) {
        this.days=days;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.day, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position==5){
            holder.contDay.setBackgroundColor(Color.YELLOW);
        }
        holder.txtNumberDay.setText(days.get(position).getNumberDay()+" "+days.get(position).getYear()+"-"+days.get(position).getMonth());
        holder.txtTemperature.setText(days.get(position).getTemperature());
        Picasso.get().load("https://" + days.get(position).getUrlCloud()).resize(60,60).centerInside().into(holder.imgCloud);
        Picasso.get().load("https://" + days.get(position).getUrlEffect()).resize(60,60).centerInside().into(holder.imgEffect);
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNumberDay, txtTemperature;
        ImageView imgCloud, imgEffect;
        ConstraintLayout contDay;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNumberDay = itemView.findViewById(R.id.txtNumberday);
            txtTemperature = itemView.findViewById(R.id.txtTemperature);
            imgCloud = itemView.findViewById(R.id.imgCloud);
            imgEffect = itemView.findViewById(R.id.imgEffect);
            contDay=itemView.findViewById(R.id.contDay);
        }
    }
}
