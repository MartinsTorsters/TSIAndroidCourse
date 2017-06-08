package com.example.martins.tsiapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.martins.tsiapplication.activities.AddNewTaskActivity;
import com.example.martins.tsiapplication.models.TaskDO;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ListView listview = (ListView) findViewById(R.id.to_do_list);

        database = FirebaseDatabase.getInstance();

        final DatabaseReference taskRef = database.getReference("tasks");
        final FirebaseListAdapter<TaskDO> myAdapter = new FirebaseListAdapter<TaskDO>(this, TaskDO.class, R.layout.activity_main, taskRef) {
            @Override
            protected void populateView(View v, TaskDO model, int position) {
                final TextView titleTextView = (TextView) findViewById(R.id.to_do_title);
                titleTextView.setText(model.getName());
                final TextView subtitleTextView = (TextView) findViewById(R.id.to_do_description);
                subtitleTextView.setText(model.getDetails());
                final TextView detailTextView = (TextView) findViewById(R.id.to_do_detail);
                detailTextView.setText(model.getUrl());
                final ImageView thumbnailImageView = (ImageView) findViewById(R.id.to_do_thumbnail);
                thumbnailImageView.setImageURI(Uri.parse(model.getUrl()));
            }
        };
        listview.setAdapter(myAdapter);
        addFloatingActionButton();
    }

    private void addFloatingActionButton() {
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddNewTaskActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
