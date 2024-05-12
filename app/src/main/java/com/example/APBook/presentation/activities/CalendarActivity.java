package com.example.APBook.presentation.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.APBook.R;
import com.example.APBook.data.retrofit.RetrofitInstance;
import com.example.APBook.domain.models.projects.ProjectModel;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarActivity extends AppCompatActivity {

    private MaterialCalendarView calendarView;
    private Map<CalendarDay, List<ProjectModel>> projectsMap = new HashMap<>();
    ListView listView;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);
        AndroidThreeTen.init(this);
        listView = findViewById(R.id.calendar_list);
        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            List<ProjectModel> projects = projectsMap.get(date);
            showProjectsDialog(projects);

        });

        loadProjects();
    }

    private void loadProjects() {

        RetrofitInstance.projectApi.getAllProjects().enqueue(new Callback<List<ProjectModel>>() {
            @Override
            public void onResponse(Call<List<ProjectModel>> call, Response<List<ProjectModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (ProjectModel project : response.body()) {
                        addProjectToCalendar(project);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ProjectModel>> call, Throwable t) {
                Toast.makeText(CalendarActivity.this, "Error loading projects", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addProjectToCalendar(ProjectModel project) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(project.startDate, formatter);
        LocalDate endDate = LocalDate.parse(project.endDate, formatter);

        Set<CalendarDay> updatedDays = new HashSet<>();

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            CalendarDay day = CalendarDay.from(date);
            List<ProjectModel> projectsForDay = projectsMap.getOrDefault(day, new ArrayList<>());
            if (!projectsForDay.contains(project)) {
                projectsForDay.add(project);
                projectsMap.put(day, projectsForDay);
            }
            updatedDays.add(day);
        }

        calendarView.addDecorator(new EventDecorator(R.color.color_primary_bright, updatedDays));
    }

    private void showProjectsDialog(List<ProjectModel> projects) {
        adapter = new ArrayAdapter<>(this, R.layout.dropdown_item);
        if (projects != null && !projects.isEmpty()) {
            adapter.clear();
            for (ProjectModel project : projects) {
                adapter.add(project.name + " (с " + project.startDate + " по " + project.endDate + ")");
            }
            listView.setAdapter(adapter);
        } else {
            adapter.clear();
            adapter.add("Нет активных проектов на этот день");
            listView.setAdapter(adapter);
        }
    }

}
