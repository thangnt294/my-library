package com.example.mylibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.mylibrary.constants.ActivityType;

import java.util.Objects;

public class FavoriteBooksActivity extends AppCompatActivity {

    private RecyclerView favoriteBooksRecView;
    private BooksRecViewAdapter favoriteBooksRecViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_books);

        // Enable the back button on top of the screen
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        favoriteBooksRecView = findViewById(R.id.favoriteBooksRecView);
        favoriteBooksRecViewAdapter = new BooksRecViewAdapter(this, ActivityType.FavoriteBooksActivity);
        favoriteBooksRecView.setAdapter(favoriteBooksRecViewAdapter);
        favoriteBooksRecView.setLayoutManager(new LinearLayoutManager(this));

        favoriteBooksRecViewAdapter.setBookList(Utils.getInstance(this).getFavoriteBooks());
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