package com.example.meteodairy.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.meteodairy.R;
import com.example.meteodairy.ui.adapters.MainAdapter;
import com.example.meteodairy.ui.adapters.PickerCityAdapter;
import com.example.meteodairy.models.City;
import com.example.meteodairy.models.DayMeteo;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity implements MainContract.View,PickerCityAdapter.OnClickCityListner {
    @Inject
    MainContract.Presenter presenter;

    private RecyclerView recyclerView;
    private TextView txtCity;
    private TextView txtDataEmpty;
    private ProgressBar progressBar;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnPickerCity = findViewById(R.id.btnCity);
        btnPickerCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickCity();
            }
        });
        txtCity = findViewById(R.id.txtCity);
        progressBar = findViewById(R.id.progressBar);
        txtDataEmpty = findViewById(R.id.txtDataEmpty);
        recyclerView = findViewById(R.id.recyleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void showError() {
    }

    @Override
    public void showWeather(List<DayMeteo> days,  String city) {
        txtDataEmpty.setVisibility(days.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        recyclerView.setVisibility(days.isEmpty() ? View.INVISIBLE : View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        recyclerView.setAdapter(new MainAdapter(days));
        recyclerView.scrollToPosition(5);
        txtCity.setText(city);
    }

    @Override
    public void showPickerCity(List<City> cities) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = (View) inflater.inflate(R.layout.dialog, null);
        builder.setView(dialogView);
        RecyclerView recyclerViewCity = (RecyclerView) dialogView.findViewById(R.id.recyleViewCity);
        recyclerViewCity.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCity.setAdapter(new PickerCityAdapter(this,cities));
        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void showDataEmpty(boolean b) {
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void showProgressBar() {
        recyclerView.setAdapter(null);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void pickCity(City city) {
        presenter.changeCity(city);
        dialog.cancel();
    }
}