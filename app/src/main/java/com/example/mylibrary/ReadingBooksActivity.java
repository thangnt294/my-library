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

public class ReadingBooksActivity extends AppCompatActivity {

    private RecyclerView readingBooksRecView;
    private Spinner readingBooksGenreSpinner;
    private BooksRecViewAdapter readingBooksRecViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_books);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        readingBooksGenreSpinner = (Spinner) findViewById(R.id.readingBooksGenreSpinner);
        ArrayList<String> genres = SpinnerUtils.getGenres();
        ArrayAdapter<String> genresAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, genres);
        readingBooksGenreSpinner.setAdapter(genresAdapter);

        readingBooksGenreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Utils.fetchBooks(BookType.ReadingBooks, Genre.valueOf(TextUtils.join("_", genres.get(position).toUpperCase().split(" "))));
                readingBooksRecViewAdapter.setBookList(Utils.getBookList(BookType.ReadingBooks));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        readingBooksRecView = findViewById(R.id.readingBooksRecView);
        readingBooksRecViewAdapter = new BooksRecViewAdapter(this, ActivityType.ReadingBooksActivity);
        readingBooksRecView.setAdapter(readingBooksRecViewAdapter);
        readingBooksRecView.setLayoutManager(new LinearLayoutManager(this));

        Utils.fetchBooks(BookType.ReadingBooks, Genre.ALL);
        readingBooksRecViewAdapter.setBookList(Utils.getBookList(BookType.ReadingBooks));
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
        Utils.fetchBooks(BookType.ReadingBooks, Genre.ALL);
        readingBooksRecViewAdapter.setBookList(Utils.getBookList(BookType.ReadingBooks));
        readingBooksGenreSpinner.setSelection(0);
    }
}