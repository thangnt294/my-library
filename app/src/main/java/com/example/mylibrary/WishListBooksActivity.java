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

public class WishListBooksActivity extends AppCompatActivity {

    private RecyclerView wishListBooksRecView;
    private Spinner wishListBooksGenreSpinner;
    private BooksRecViewAdapter wishListBooksRecViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list_books);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        wishListBooksGenreSpinner = (Spinner) findViewById(R.id.wishListBooksGenreSpinner);
        ArrayList<String> genres = SpinnerUtils.getGenres();
        ArrayAdapter<String> genresAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, genres);
        wishListBooksGenreSpinner.setAdapter(genresAdapter);

        wishListBooksGenreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Utils.fetchBooks(BookType.WishListBooks, Genre.valueOf(TextUtils.join("_", genres.get(position).toUpperCase().split(" "))));
                wishListBooksRecViewAdapter.setBookList(Utils.getBookList(BookType.WishListBooks));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        wishListBooksRecView = findViewById(R.id.wishListBooksRecView);
        wishListBooksRecViewAdapter = new BooksRecViewAdapter(this, ActivityType.WishListBooksActivity);
        wishListBooksRecView.setAdapter(wishListBooksRecViewAdapter);
        wishListBooksRecView.setLayoutManager(new LinearLayoutManager(this));

        Utils.fetchBooks(BookType.WishListBooks, Genre.ALL);
        wishListBooksRecViewAdapter.setBookList(Utils.getBookList(BookType.WishListBooks));
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
        Utils.fetchBooks(BookType.WishListBooks, Genre.ALL);
        wishListBooksRecViewAdapter.setBookList(Utils.getBookList(BookType.WishListBooks));
        wishListBooksGenreSpinner.setSelection(0);
    }
}