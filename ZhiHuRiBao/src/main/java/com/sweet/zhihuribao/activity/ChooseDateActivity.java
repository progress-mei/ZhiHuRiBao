package com.sweet.zhihuribao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.squareup.timessquare.CalendarPickerView;
import com.sweet.zhihuribao.R;
import com.sweet.zhihuribao.base.BaseActivity;
import com.sweet.zhihuribao.utils.support.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sweet on 2016/4/6/0006.
 */
public class ChooseDateActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        layoutResID = R.layout.activity_pick_date;
        super.onCreate(savedInstanceState);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Calendar nextDay = Calendar.getInstance();
        nextDay.add(Calendar.DAY_OF_YEAR, 1);

        CalendarPickerView calendarPickerView = (CalendarPickerView) findViewById(R.id.calendar_view);
        calendarPickerView.init(Constants.Date.birthday, nextDay.getTime())
                .withSelectedDate(Calendar.getInstance().getTime());

        calendarPickerView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_YEAR, 0);
                Intent intent = new Intent(ChooseDateActivity.this, ChooseItemActivity.class);
                String chooseDate = Constants.Date.simpleDateFormat.format(calendar.getTime());
                intent.putExtra("Choose_Date", chooseDate);
                startActivity(intent);

            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
        calendarPickerView.setOnInvalidDateSelectedListener(new CalendarPickerView.OnInvalidDateSelectedListener() {
            @Override
            public void onInvalidDateSelected(Date date) {
                if (date.after(new Date())) {
                    showSnackbar(R.string.not_coming);
                } else {
                    showSnackbar(R.string.not_born);
                }
            }
        });
    }

}
