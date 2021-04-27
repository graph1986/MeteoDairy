package com.example.meteodairy.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meteodairy.R;
import com.example.meteodairy.models.City;

import java.util.List;

public class PickerCityAdapter extends RecyclerView.Adapter<PickerCityAdapter.ViewHolder> {
    private List<City> cities;
    private OnClickCityListner listner;

    public interface OnClickCityListner {
        void pickCity(City city);
    }
    public PickerCityAdapter(OnClickCityListner listner,List<City> cities) {
        this.listner=listner;
        this.cities = cities;
    }

    @NonNull
    @Override
    public PickerCityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.city, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PickerCityAdapter.ViewHolder holder, int position) {
        holder.txtCity.setText(cities.get(position).getName());
        holder.txtCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.pickCity(cities.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCity = itemView.findViewById(R.id.txtDialogCity);
        }
    }
}
