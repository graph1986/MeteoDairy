package com.example.meteodairy.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.meteodairy.R;
import com.example.meteodairy.adapters.MainAdapter;
import com.example.meteodairy.models.DayMeteo;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    @Inject
    MainContract.Presenter presenter;

    private RecyclerView recyclerView;
    private TextView txtDate;
    private TextView txtDataEmpty;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Button btnYear = findViewById(R.id.btnYear);
        txtDate = findViewById(R.id.txtDate);
        progressBar = findViewById(R.id.progressBar);
        txtDataEmpty = findViewById(R.id.txtDataEmpty);
        btnYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickYear();
            }
        });
        Button btnMonth = findViewById(R.id.btnMonth);
        btnMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickMonth();
            }
        });
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
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showWeather(List<DayMeteo> dayMeteoList, Calendar calendar) {
        txtDataEmpty.setVisibility(dayMeteoList.isEmpty()? View.VISIBLE : View.INVISIBLE);
        recyclerView.setVisibility(dayMeteoList.isEmpty()? View.INVISIBLE : View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, RecyclerView.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(new MainAdapter(dayMeteoList));
        txtDate.setText("Год: " + new SimpleDateFormat("yyyy").format(calendar.getTime()) + " Месяц: " + new SimpleDateFormat("LLLL").format(calendar.getTime()));
    }

    @Override
    public void showPickerYear(Calendar calendar) {
        MonthPickerDialog.Builder monthPickerDialog = new MonthPickerDialog.Builder(this, new MonthPickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(int selectedMonth, int selectedYear) {
            }
        }, Calendar.MONTH, Calendar.YEAR);
        monthPickerDialog
                .setActivatedYear(calendar.get(Calendar.YEAR))
                .setMinYear(1997)
                .setMaxYear(Calendar.getInstance().get(Calendar.YEAR))
                .showYearOnly()
                .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                    @Override
                    public void onYearChanged(int year) {
                        presenter.changeYear(year);
                    }
                })
                .build()
                .show();

    }

    @Override
    public void showPickerMonth(Calendar calendar) {
        MonthPickerDialog.Builder monthPickerDialog = new MonthPickerDialog.Builder(this, new MonthPickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(int selectedMonth, int selectedYear) {
            }
        }, Calendar.MONTH, Calendar.YEAR);
        monthPickerDialog
                .setActivatedMonth(calendar.get(Calendar.MONTH))
                .showMonthOnly()
                .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                    @Override
                    public void onMonthChanged(int month) {
                        presenter.changeMonth(month);
                    }
                })
                .build()
                .show();

    }

    @Override
    public void showPickerCity() {

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

}