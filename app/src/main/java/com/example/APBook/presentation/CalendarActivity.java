package com.example.APBook.presentation;

import android.os.Bundle;
import android.widget.CalendarView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.APBook.R;

import java.util.Date;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private Date startDate;
    private Date endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);

//        calendarView = findViewById(R.id.calendarView);
//
//        // Установка слушателя для выбора даты
//        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
//                Calendar calendar = Calendar.getInstance();
//                calendar.set(year, month, dayOfMonth);
//
//                if (startDate == null || endDate != null) {
//                    startDate = calendar.getTime();
//                    endDate = null;
//                } else if (startDate != null && endDate == null) {
//                    endDate = calendar.getTime();
//
//                    // Обработка выбранного диапазона
//                    highlightDateRange(startDate, endDate);
//
//                    // Сброс выбранных дат для возможности выбора нового диапазона
//                    startDate = null;
//                    endDate = null;
//                }
//            }
//        });
    }

    // Метод для выделения выбранного диапазона дат на календаре
    private void highlightDateRange(Date startDate, Date endDate) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(startDate);
//        long startTimeInMillis = calendar.getTimeInMillis();
//        calendar.setTime(endDate);
//        long endTimeInMillis = calendar.getTimeInMillis();
//
//        calendarView.setDate(startTimeInMillis);
//
//        while (startTimeInMillis <= endTimeInMillis) {
//            calendarView.setDate(startTimeInMillis, true, true);
//            calendarView.getChildAt(0).setBackgroundColor(Color.GREEN); // Можно изменить цвет выделения
//            startTimeInMillis += 24 * 60 * 60 * 1000; // Добавляем один день в миллисекундах
//        }
    }
}
