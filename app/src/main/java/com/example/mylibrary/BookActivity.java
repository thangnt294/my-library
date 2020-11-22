package com.example.mylibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mylibrary.constants.ActivityType;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookActivity extends AppCompatActivity {

    public static final String BOOK_ID_KEY = "bookId";

    private TextView txtBookTitleInfo, txtAuthorNameInfo, txtPagesInfo, txtLongDescInfo;
    private Button btnAddToWishListBooks, btnAddToReadingBooks, btnAddToFinishedBooks, btnAddToFavoriteBooks, btnEdit;
    private ImageView imgBookInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initViews();

        // Get the book from BooksRecViewAdapter
        int bookId = -1;
        Intent intent = getIntent();
        if (intent != null) {
            bookId = intent.getIntExtra(BOOK_ID_KEY, -1);
        }

        reloadDataByBookId(bookId);

    }

    /**
     * Disable the "Add to finished books" button if the book is already finished
     * Add the book to finished books if not
     *
     * @param book The current book
     */
    private void handleFinishedBooks(final Book book) {
        ArrayList<Book> finishedBooks = Utils.getFinishedBooks();

        AtomicBoolean existInFinishedBooks = new AtomicBoolean(false);

        for (Book finishedBook : finishedBooks) {
            if (finishedBook.getId() == book.getId()) {
                existInFinishedBooks.set(true);
                break;
            }
        }

        if (existInFinishedBooks.get()) {
            btnAddToFinishedBooks.setEnabled(false);
        } else {
            btnAddToFinishedBooks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.addToFinishedBooks(book)) {
                        Toast.makeText(BookActivity.this, "Added to finished books", Toast.LENGTH_SHORT).show();
                        btnAddToFinishedBooks.setEnabled(false);
                    } else {
                        Toast.makeText(BookActivity.this, "Something went wrong. Please try again later", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * Same logic as the handleFinishedBooks method
     *
     * @param book The current book
     */
    private void handleWishListBooks(final Book book) {
        ArrayList<Book> wishListBooks = Utils.getWishListBooks();

        AtomicBoolean existInWishListBooks = new AtomicBoolean(false);

        for (Book wishListBook : wishListBooks) {
            if (wishListBook.getId() == book.getId()) {
                existInWishListBooks.set(true);
                break;
            }
        }

        if (existInWishListBooks.get()) {
            btnAddToWishListBooks.setEnabled(false);
        } else {
            btnAddToWishListBooks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.addToWishListBooks(book)) {
                        Toast.makeText(BookActivity.this, "Added to wish list books", Toast.LENGTH_SHORT).show();
                        btnAddToWishListBooks.setEnabled(false);
                    } else {
                        Toast.makeText(BookActivity.this, "Something went wrong. Please try again later", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * Same logic as the handleFinishedBooks method
     *
     * @param book The current book
     */
    private void handleReadingBooks(final Book book) {
        ArrayList<Book> readingBooks = Utils.getReadingBooks();

        AtomicBoolean existInReadingBooks = new AtomicBoolean(false);

        for (Book readingBook : readingBooks) {
            if (readingBook.getId() == book.getId()) {
                existInReadingBooks.set(true);
                break;
            }
        }

        if (existInReadingBooks.get()) {
            btnAddToReadingBooks.setEnabled(false);
        } else {
            btnAddToReadingBooks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.addToReadingBooks(book)) {
                        Toast.makeText(BookActivity.this, "Added to reading books", Toast.LENGTH_SHORT).show();
                        btnAddToReadingBooks.setEnabled(false);
                    } else {
                        Toast.makeText(BookActivity.this, "Something went wrong. Please try again later", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * Same logic as the handleFinishedBooks method
     *
     * @param book The current book
     */
    private void handleFavoriteBooks(final Book book) {
        ArrayList<Book> favoriteBooks = Utils.getFavoriteBooks();

        AtomicBoolean existInFavoriteBooks = new AtomicBoolean(false);

        for (Book favoriteBook : favoriteBooks) {
            if (favoriteBook.getId() == book.getId()) {
                existInFavoriteBooks.set(true);
                break;
            }
        }

        if (existInFavoriteBooks.get()) {
            btnAddToFavoriteBooks.setEnabled(false);
        } else {
            btnAddToFavoriteBooks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.addToFavoriteBooks(book)) {
                        Toast.makeText(BookActivity.this, "Added to favorite", Toast.LENGTH_SHORT).show();
                        btnAddToFavoriteBooks.setEnabled(false);
                    } else {
                        Toast.makeText(BookActivity.this, "Something went wrong. Please try again later", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void handleEditBook(int bookId) {
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookActivity.this, EditBookActivity.class);
                intent.putExtra(EditBookActivity.ACTIVITY_TYPE, ActivityType.UpdateBookActivity);
                intent.putExtra(BOOK_ID_KEY, bookId);
                startActivityForResult(intent, 1);
            }
        });
    }

    /**
     * Set the book data to the views
     *
     * @param book The current book
     */
    private void setData(Book book) {
        txtBookTitleInfo.setText(book.getTitle());
        txtAuthorNameInfo.setText(book.getAuthor());
        txtPagesInfo.setText(String.valueOf(book.getPages()));
        txtLongDescInfo.setText(book.getLongDesc());
        Glide.with(this)
                .asBitmap()
                .load(book.getImageUrl())
                .error(R.drawable.default_image)
                .into(imgBookInfo);
    }

    private void initViews() {
        txtAuthorNameInfo = findViewById(R.id.txtAuthorNameInfo);
        txtBookTitleInfo = findViewById(R.id.txtBookTitleInfo);
        txtPagesInfo = findViewById(R.id.txtPagesInfo);
        txtLongDescInfo = findViewById(R.id.txtLongDescInfo);

        btnAddToWishListBooks = findViewById(R.id.btnAddToWishListBooks);
        btnAddToReadingBooks = findViewById(R.id.btnAddToReadingBooks);
        btnAddToFinishedBooks = findViewById(R.id.btnAddToFinishedBooks);
        btnAddToFavoriteBooks = findViewById(R.id.btnAddToFavoriteBooks);
        btnEdit = findViewById(R.id.btn_edit);


        imgBookInfo = findViewById(R.id.imgBookInfo);
    }

    private void reloadDataByBookId(int bookId) {
        if (bookId != -1) {
            Book book = Utils.getBookById(bookId);
            if (book != null) {
                setData(book);

                handleFinishedBooks(book);
                handleWishListBooks(book);
                handleReadingBooks(book);
                handleFavoriteBooks(book);
                handleEditBook(bookId);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                assert data != null;
                int updatedBookId = data.getIntExtra(BOOK_ID_KEY, -1);
                reloadDataByBookId(updatedBookId);
                Toast.makeText(this, R.string.update_success, Toast.LENGTH_SHORT).show();
            } else if (resultCode == 2) {
                Toast.makeText(this, R.string.general_error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}