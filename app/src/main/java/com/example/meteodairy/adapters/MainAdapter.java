package com.example.meteodairy.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meteodairy.R;
import com.example.meteodairy.models.DayMeteo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private List<DayMeteo> dayMeteoList;

    public MainAdapter(List<DayMeteo> dayMeteoList) {
        this.dayMeteoList = dayMeteoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.day, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtNumberDay.setText(dayMeteoList.get(position).getNumberDay());
        holder.txtTemperature.setText(dayMeteoList.get(position).getTemperature());
        Picasso.get().load("https://" + dayMeteoList.get(position).getUrlCloud()).into(holder.imgCloud);
        Picasso.get().load("https://" + dayMeteoList.get(position).getUrlEffect()).into(holder.imgEffect);
    }

    @Override
    public int getItemCount() {
        return dayMeteoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNumberDay, txtTemperature;
        ImageView imgCloud, imgEffect;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNumberDay = itemView.findViewById(R.id.txtNumberday);
            txtTemperature = itemView.findViewById(R.id.txtTemperature);
            imgCloud = itemView.findViewById(R.id.imgCloud);
            imgEffect = itemView.findViewById(R.id.imgEffect);
        }
    }
}
