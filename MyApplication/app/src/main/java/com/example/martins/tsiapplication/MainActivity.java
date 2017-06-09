package com.example.martins.tsiapplication;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.martins.tsiapplication.activities.AddNewTaskActivity;
import com.example.martins.tsiapplication.models.TaskAdapter;
import com.example.martins.tsiapplication.models.TaskDO;
import com.example.martins.tsiapplication.models.TaskHolder;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.to_do_list);

        database = FirebaseDatabase.getInstance();

        final DatabaseReference taskRef = database.getReference("tasks");
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        final TaskAdapter myAdapter = new TaskAdapter(TaskDO.class, R.layout.to_do_task, TaskHolder.class, taskRef, this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapter);

        addDeleteOnSwipeItemToucHelper(recyclerView, myAdapter);
        addFloatingActionButton();
    }

    private void addDeleteOnSwipeItemToucHelper(final RecyclerView recyclerView, final TaskAdapter myAdapter) {
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(MainActivity.this, "C'est maintenant ou jamais!", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                final TaskDO taskToDelete = myAdapter.getItem(position);
                database.getReference().child("tasks").child(taskToDelete.getId()).setValue(null);
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
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
            startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
