package com.example.mylibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.mylibrary.constants.ActivityType;

import java.util.ArrayList;
import java.util.Objects;

public class FinishedBooksActivity extends AppCompatActivity {

    private RecyclerView finishedBooksRecView;
    private BooksRecViewAdapter finishedBooksRecViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_books);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        finishedBooksRecView = findViewById(R.id.finishedBooksRecView);
        finishedBooksRecViewAdapter = new BooksRecViewAdapter(this, ActivityType.FinishedBooksActivity);
        finishedBooksRecView.setAdapter(finishedBooksRecViewAdapter);
        finishedBooksRecView.setLayoutManager(new LinearLayoutManager(this));

        finishedBooksRecViewAdapter.setBookList(Utils.getFinishedBooks());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}