package com.example.martins.tsiapplication.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.martins.tsiapplication.MainActivity;
import com.example.martins.tsiapplication.R;
import com.example.martins.tsiapplication.models.TaskDO;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddNewTaskActivity extends Activity {
    FirebaseDatabase database;
    private int mYear,mMonth,mDay;
    private Date selectedDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_task);
        database = FirebaseDatabase.getInstance();

        final Button button = (Button) findViewById(R.id.add_new_task);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText titleEditView = (EditText) findViewById(R.id.add_title);
                final EditText descriptionEditView = (EditText) findViewById(R.id.add_description);
                final EditText urlEditView = (EditText) findViewById(R.id.add_thumbnail);
                if (isInputValid(titleEditView, descriptionEditView, urlEditView)) {
                    writeNewTask(titleEditView.getText().toString(), descriptionEditView.getText().toString(),
                            descriptionEditView.getText().toString(), urlEditView.getText().toString());
                    startActivity(new Intent());
                } else {
                    Snackbar.make(v, "Fill in all Fields!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });
    }

    private boolean isInputValid(EditText titleEditView, EditText descriptionEditView, EditText urlEditView) {
        return !TextUtils.isEmpty(titleEditView.getText().toString()) || !TextUtils.isEmpty(descriptionEditView.getText().toString()) ||
                !TextUtils.isEmpty(urlEditView.getText().toString());
    }

    private void writeNewTask(String title, String details, String url, String label) {
        String key = database.getReference().child("tasks").push().getKey();
        final TaskDO task = new TaskDO(key, title, details, url);
        task.setToDoDate(selectedDate);
        Map<String, Object> postValues = task.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/tasks/" + key, postValues);

        database.getReference().updateChildren(childUpdates);
    }

    public void showDatePickerDialog(View view) {
        final Button pickDate = (Button) findViewById(R.id.pick_date);
        final TextView textView = (TextView) findViewById(R.id.date);

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                // myCalendar.add(Calendar.DATE, 0);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
                textView.setText(sdf.format(myCalendar.getTime()));
                selectedDate = myCalendar.getTime();
            }

        };
        pickDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(AddNewTaskActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox

                                if (year < mYear)
                                    view.updateDate(mYear,mMonth,mDay);

                                if (monthOfYear < mMonth && year == mYear)
                                    view.updateDate(mYear,mMonth,mDay);

                                if (dayOfMonth < mDay && year == mYear && monthOfYear == mMonth)
                                    view.updateDate(mYear,mMonth,mDay);

                                textView.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                dpd.getDatePicker().setMinDate(System.currentTimeMillis());
                dpd.show();

            }
        });
    }
}
