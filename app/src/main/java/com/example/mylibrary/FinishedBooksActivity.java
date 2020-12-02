package com.example.mylibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.mylibrary.constants.ActivityType;
import com.example.mylibrary.constants.BookType;
import com.example.mylibrary.constants.Genre;
import com.example.mylibrary.utils.SpinnerUtils;

import java.util.ArrayList;
import java.util.Objects;

public class FinishedBooksActivity extends AppCompatActivity {

    private RecyclerView finishedBooksRecView;
    private Spinner finishedBooksGenreSpinner;
    private BooksRecViewAdapter finishedBooksRecViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_books);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        finishedBooksGenreSpinner = (Spinner) findViewById(R.id.finishedBooksGenreSpinner);
        ArrayList<String> genres = SpinnerUtils.getGenres();
        ArrayAdapter<String> genresAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, genres);
        finishedBooksGenreSpinner.setAdapter(genresAdapter);

        finishedBooksGenreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Utils.fetchBooks(BookType.FinishedBooks, Genre.valueOf(TextUtils.join("_", genres.get(position).toUpperCase().split(" "))));
                finishedBooksRecViewAdapter.setBookList(Utils.getBookList(BookType.FinishedBooks));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        finishedBooksRecView = findViewById(R.id.finishedBooksRecView);
        finishedBooksRecViewAdapter = new BooksRecViewAdapter(this, ActivityType.FinishedBooksActivity);
        finishedBooksRecView.setAdapter(finishedBooksRecViewAdapter);
        finishedBooksRecView.setLayoutManager(new LinearLayoutManager(this));

        Utils.fetchBooks(BookType.FinishedBooks, Genre.ALL);
        finishedBooksRecViewAdapter.setBookList(Utils.getBookList(BookType.FinishedBooks));
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

    @Override
    protected void onResume() {
        super.onResume();
        Utils.fetchBooks(BookType.FinishedBooks, Genre.ALL);
        finishedBooksRecViewAdapter.setBookList(Utils.getBookList(BookType.FinishedBooks));
        finishedBooksGenreSpinner.setSelection(0);
    }

}