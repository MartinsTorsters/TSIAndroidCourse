package com.example.martins.tsiapplication.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
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
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class EditTaskActivity extends Activity {

    private FirebaseDatabase firebaseDatabase;
    private int mYear, mMonth, mDay;
    final EditText titleEditView = (EditText) findViewById(R.id.add_title);
    final EditText descriptionEditView = (EditText) findViewById(R.id.add_description);
    final EditText urlEditView = (EditText) findViewById(R.id.add_thumbnail);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task);
        final Intent intent = getIntent();
        final String id = setValues(intent);
        final Button button = (Button) findViewById(R.id.add_new_task);
        addButtonClickListener(id, button);
    }

    private String setValues(final Intent intent) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        titleEditView.setText(intent.getStringExtra("title"));
        descriptionEditView.setText(intent.getStringExtra("description"));
        urlEditView.setText(intent.getStringExtra("url"));
        return intent.getStringExtra("id");
    }

    private void addButtonClickListener(final String id, final Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInputValid(titleEditView, descriptionEditView, urlEditView)) {
                    updateTask(titleEditView.getText().toString(), descriptionEditView.getText().toString(),
                            descriptionEditView.getText().toString(), urlEditView.getText().toString(), id);
                    startActivity(new Intent(EditTaskActivity.this, MainActivity.class));
                } else {
                    Snackbar.make(v, "Fill in all Fields!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });
    }

    private void updateTask(String id, String details, String url, String label, String title) {
        final HashMap<String, Object> result = new HashMap<>();
        result.put("name", title);
        result.put("details", details);
        result.put("url", url);
        result.put("label", label);
        firebaseDatabase.getReference("tasks").child(id).updateChildren(result);
    }


    private boolean isInputValid(EditText titleEditView, EditText descriptionEditView, EditText urlEditView) {
        return !TextUtils.isEmpty(titleEditView.getText().toString()) || !TextUtils.isEmpty(descriptionEditView.getText().toString()) ||
                !TextUtils.isEmpty(urlEditView.getText().toString());
    }

    public void showDatePickerDialog1(View view) {
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
                final String myFormat = "yyyy-MM-dd"; //In which you need put here
                final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
                textView.setText(sdf.format(myCalendar.getTime()));

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
                DatePickerDialog dpd = new DatePickerDialog(EditTaskActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox

                                if (year < mYear)
                                    view.updateDate(mYear, mMonth, mDay);

                                if (monthOfYear < mMonth && year == mYear)
                                    view.updateDate(mYear, mMonth, mDay);

                                if (dayOfMonth < mDay && year == mYear && monthOfYear == mMonth)
                                    view.updateDate(mYear, mMonth, mDay);

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
