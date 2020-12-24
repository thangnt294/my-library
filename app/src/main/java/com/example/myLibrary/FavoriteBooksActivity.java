package com.example.myLibrary;

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

import com.example.myLibrary.constants.ActivityType;
import com.example.myLibrary.constants.BookType;
import com.example.myLibrary.constants.Genre;
import com.example.myLibrary.utils.SpinnerUtils;

import java.util.ArrayList;
import java.util.Objects;

public class FavoriteBooksActivity extends AppCompatActivity{

    private RecyclerView favoriteBooksRecView;
    private Spinner favoriteBooksGenreSpinner;
    private BooksRecViewAdapter favoriteBooksRecViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_books);

        // Enable the back button on top of the screen
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        favoriteBooksGenreSpinner = (Spinner) findViewById(R.id.favoriteBooksGenreSpinner);
        ArrayList<String> genres = SpinnerUtils.getGenres();
        ArrayAdapter<String> genresAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, genres);
        favoriteBooksGenreSpinner.setAdapter(genresAdapter);

        favoriteBooksGenreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Utils.fetchBooks(BookType.FavoriteBooks, Genre.valueOf(TextUtils.join("_", genres.get(position).toUpperCase().split(" "))));
                favoriteBooksRecViewAdapter.setBookList(Utils.getBookList(BookType.FavoriteBooks));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        favoriteBooksRecView = findViewById(R.id.favoriteBooksRecView);
        favoriteBooksRecViewAdapter = new BooksRecViewAdapter(this, ActivityType.FavoriteBooksActivity);
        favoriteBooksRecView.setAdapter(favoriteBooksRecViewAdapter);
        favoriteBooksRecView.setLayoutManager(new LinearLayoutManager(this));

        Utils.fetchBooks(BookType.FavoriteBooks, Genre.ALL);
        favoriteBooksRecViewAdapter.setBookList(Utils.getBookList(BookType.FavoriteBooks));

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
        Utils.fetchBooks(BookType.FavoriteBooks, Genre.ALL);
        favoriteBooksRecViewAdapter.setBookList(Utils.getBookList(BookType.FavoriteBooks));
        favoriteBooksGenreSpinner.setSelection(0);
    }
}