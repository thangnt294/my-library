package com.example.mylibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mylibrary.constants.ActivityType;
import com.example.mylibrary.constants.BookType;
import com.example.mylibrary.constants.Genre;
import com.example.mylibrary.utils.SpinnerUtils;

import java.util.ArrayList;
import java.util.Objects;

public class EditBookActivity extends AppCompatActivity {

    public static final String ACTIVITY_TYPE = "Activity Type";
    public static final String TITLE = "title";
    public static final String AUTHOR = "author";
    public static final String PAGES = "pages";
    public static final String IMAGE_URL = "imageUrl";
    public static final String SHORT_DESC = "shortDesc";
    public static final String LONG_DESC = "longDesc";

    private EditText title, author, pages, imageUrl, shortDesc, longDesc;
    private ActivityType activityType;
    private Spinner genreSpinner;
    private Button btnConfirm, btnCancel;
    private int bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        title = findViewById(R.id.txt_book_title);
        author = findViewById(R.id.txt_book_author);
        pages = findViewById(R.id.txt_book_pages);
        imageUrl = findViewById(R.id.txt_book_image_url);
        shortDesc = findViewById(R.id.txt_book_short_desc);
        longDesc = findViewById(R.id.txt_book_long_desc);
        genreSpinner = (Spinner) findViewById(R.id.genre_spinner);
        TextView editBookTitle = findViewById(R.id.txt_edit_book_title);

        btnConfirm = (Button) findViewById(R.id.btn_confirm);
        btnCancel = (Button) findViewById(R.id.btn_cancel);

        // Enable the back button on top of the screen
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Set spinner values
        ArrayList<String> genres = SpinnerUtils.getGenres();
        genres.remove(Genre.ALL.getValue());
        ArrayAdapter<String> genresAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, genres);
        genreSpinner.setAdapter(genresAdapter);


        Intent intent = getIntent();
        if (intent != null) {
            activityType = (ActivityType) intent.getSerializableExtra(ACTIVITY_TYPE);
            if (activityType == ActivityType.UpdateBookActivity) {
                editBookTitle.setText(R.string.edit_book);
                bookId = intent.getIntExtra(BookActivity.BOOK_ID_KEY, -1);
                if (bookId != -1) {
                    Book book = Utils.getBookById(bookId);
                    if (book != null) {
                        setBookData(book);
                    }
                }
            } else {
                activityType = ActivityType.AddBookActivity;
            }
        } else {
            activityType = ActivityType.AddBookActivity; // Default to add a book
        }

        handleButton();

    }

    public void setBookData(Book book) {
        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        pages.setText(Integer.toString(book.getPages()));
        imageUrl.setText(book.getImageUrl());
        shortDesc.setText(book.getShortDesc());
        longDesc.setText(book.getLongDesc());
        genreSpinner.setSelection(this.getSpinnerPosition(book.getGenre()));
    }

    public int getSpinnerPosition(String genre) {
        if (genre.equals(Genre.ADVENTURE.getValue())) {
            return 1;
        } else if (genre.equals(Genre.GUIDE.getValue())) {
            return 2;
        } else if (genre.equals(Genre.ART.getValue())) {
            return 3;
        } else if (genre.equals(Genre.CHILDREN.getValue())) {
            return 4;
        } else if (genre.equals(Genre.CONTEMPORARY.getValue())) {
            return 5;
        } else if (genre.equals(Genre.COOKING.getValue())) {
            return 6;
        } else if (genre.equals(Genre.HISTORICAL_FICTION.getValue())) {
            return 7;
        } else if (genre.equals(Genre.ROMANCE.getValue())) {
            return 8;
        } else if (genre.equals(Genre.SCIENCE_FICTION.getValue())) {
            return 9;
        } else if (genre.equals(Genre.HEALTH.getValue())) {
            return 10;
        } else if (genre.equals(Genre.HISTORY.getValue())) {
            return 11;
        } else if (genre.equals(Genre.HORROR.getValue())) {
            return 12;
        } else if (genre.equals(Genre.HUMOR.getValue())) {
            return 13;
        } else if (genre.equals(Genre.SELF_HELP.getValue())) {
            return 14;
        } else if (genre.equals(Genre.DEVELOPMENT.getValue())) {
            return 15;
        } else if (genre.equals(Genre.FANTASY.getValue())) {
            return 16;
        } else if (genre.equals(Genre.MOTIVATIONAL.getValue())) {
            return 17;
        } else if (genre.equals(Genre.PARANORMAL.getValue())) {
            return 18;
        } else if (genre.equals(Genre.TRAVEL.getValue())) {
            return 19;
        } else if (genre.equals(Genre.MEMOIR.getValue())) {
            return 20;
        } else if (genre.equals(Genre.MYSTERY.getValue())) {
            return 21;
        } else if (genre.equals(Genre.THRILLER.getValue())) {
            return 22;
        } else {
            return 0;
        }
    }

    public void handleButton() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(EditBookActivity.this);
                String newTitle = title.getText().toString().trim();
                String newAuthor = author.getText().toString().trim();
                int newPages = Integer.parseInt(pages.getText().toString().trim());
                String newImageUrl = imageUrl.getText().toString().trim();
                String newShortDesc = shortDesc.getText().toString().trim();
                String newLongDesc = longDesc.getText().toString().trim();
                String newGenre = genreSpinner.getSelectedItem().toString();
                Intent resultIntent = new Intent();
                long result;
                if (activityType == ActivityType.AddBookActivity) {
                    result = myDB.addData(new Book(newTitle, newAuthor, newPages, newImageUrl, newShortDesc, newLongDesc, newGenre));
                } else {
                    result = myDB.updateData(new Book(bookId, newTitle, newAuthor, newPages, newImageUrl, newShortDesc, newLongDesc, newGenre));
                    resultIntent.putExtra(BookActivity.BOOK_ID_KEY, bookId);
                }

                if (result == -1) {
                    setResult(2, resultIntent);
                } else {
                    Utils.fetchBooks(BookType.AllBooks, Genre.ALL);
                    setResult(Activity.RESULT_OK, resultIntent);
                }
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
        super.onBackPressed();
    }
}