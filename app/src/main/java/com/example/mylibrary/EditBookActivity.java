package com.example.mylibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mylibrary.constants.ActivityType;

public class EditBookActivity extends AppCompatActivity {

    private EditText title, author, pages, imageUrl, shortDesc, longDesc;
    private TextView editBookTitle;
    private static final String ACTIVITY_TYPE = "Activity Type";
    private ActivityType activityType;
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

        Button btnConfirm = (Button) findViewById(R.id.btn_confirm);
        Button btnCancel = (Button) findViewById(R.id.btn_cancel);

        Intent intent = getIntent();
        if (intent != null) {
            activityType = (ActivityType) intent.getSerializableExtra(ACTIVITY_TYPE);
            if (activityType == ActivityType.UpdateBookActivity) {
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
                if (activityType == ActivityType.AddBookActivity) {
                    myDB.addBook(newTitle, newAuthor, newPages, newImageUrl, newShortDesc, newLongDesc);
                } else {
                    myDB.updateBook(Integer.toString(bookId), newTitle, newAuthor, newPages, newImageUrl, newShortDesc, newLongDesc);
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void setBookData(Book book) {
        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        pages.setText(book.getPages());
        imageUrl.setText(book.getImageUrl());
        shortDesc.setText(book.getShortDesc());
        longDesc.setText(book.getLongDesc());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}