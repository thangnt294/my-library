package com.example.mylibrary;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.example.mylibrary.constants.ActivityType;
import com.example.mylibrary.constants.BookType;
import com.example.mylibrary.constants.Genre;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookActivity extends AppCompatActivity {

    public static final String BOOK_ID_KEY = "bookId";

    private TextView txtBookTitleInfo, txtAuthorNameInfo, txtPagesInfo, txtGenreInfo, txtLongDescInfo;
    private Button btnAddToWishListBooks, btnAddToReadingBooks, btnAddToFinishedBooks, btnAddToFavoriteBooks, btnEdit, btnDelete;
    private ConstraintLayout bookLayout;
    private RelativeLayout emptyLayout;
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
     * @param bookId The id of the current book
     */
    private void handleFinishedBooks(final int bookId) {
        MyDatabaseHelper myDB = new MyDatabaseHelper(BookActivity.this);

        Utils.fetchBooks(BookType.FinishedBooks, Genre.ALL);

        ArrayList<Book> finishedBooks = Utils.getBookList(BookType.FinishedBooks);

        AtomicBoolean existInFinishedBooks = new AtomicBoolean(false);

        for (Book finishedBook : finishedBooks) {
            if (finishedBook.getId() == bookId) {
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
                    long result = myDB.addToBookTypes(bookId, BookType.FinishedBooks);
                    if (result != -1) {
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
     * @param bookId The id of the current book
     */
    private void handleWishListBooks(final int bookId) {
        MyDatabaseHelper myDB = new MyDatabaseHelper(BookActivity.this);

        Utils.fetchBooks(BookType.WishListBooks, Genre.ALL);

        ArrayList<Book> wishListBooks = Utils.getBookList(BookType.WishListBooks);

        AtomicBoolean existInWishListBooks = new AtomicBoolean(false);

        for (Book wishListBook : wishListBooks) {
            if (wishListBook.getId() == bookId) {
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
                    long result = myDB.addToBookTypes(bookId, BookType.WishListBooks);
                    if (result != -1) {
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
     * @param bookId The id of the current book
     */
    private void handleReadingBooks(final int bookId) {
        MyDatabaseHelper myDB = new MyDatabaseHelper(BookActivity.this);

        Utils.fetchBooks(BookType.ReadingBooks, Genre.ALL);

        ArrayList<Book> readingBooks = Utils.getBookList(BookType.ReadingBooks);

        AtomicBoolean existInReadingBooks = new AtomicBoolean(false);

        for (Book readingBook : readingBooks) {
            if (readingBook.getId() == bookId) {
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
                    long result = myDB.addToBookTypes(bookId, BookType.ReadingBooks);
                    if (result != -1) {
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
     * @param bookId The id of the current book
     */
    private void handleFavoriteBooks(final int bookId) {
        MyDatabaseHelper myDB = new MyDatabaseHelper(BookActivity.this);

        Utils.fetchBooks(BookType.FavoriteBooks, Genre.ALL);

        ArrayList<Book> favoriteBooks = Utils.getBookList(BookType.FavoriteBooks);

        AtomicBoolean existInFavoriteBooks = new AtomicBoolean(false);

        for (Book favoriteBook : favoriteBooks) {
            if (favoriteBook.getId() == bookId) {
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
                    long result = myDB.addToBookTypes(bookId, BookType.FavoriteBooks);
                    if (result != -1) {
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

    private void handleDeleteBook(int bookId) {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BookActivity.this);
                builder.setMessage("Are you sure you want to delete this book?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MyDatabaseHelper myDB = new MyDatabaseHelper(BookActivity.this);
                                long result = myDB.deleteData(bookId);

                                if (result != -1) {
                                    Toast.makeText(BookActivity.this, R.string.delete_success, Toast.LENGTH_SHORT).show();
                                    bookLayout.setVisibility(View.GONE);
                                    emptyLayout.setVisibility(View.VISIBLE);
                                } else {
                                    Toast.makeText(BookActivity.this, R.string.general_error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Auto-dismiss the dialog
                    }
                }).create().show();
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
        txtGenreInfo.setText(book.getGenre());
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
        txtGenreInfo = findViewById(R.id.txtGenreInfo);

        bookLayout = findViewById(R.id.book_layout);
        emptyLayout = findViewById(R.id.empty_layout);

        btnAddToWishListBooks = findViewById(R.id.btnAddToWishListBooks);
        btnAddToReadingBooks = findViewById(R.id.btnAddToReadingBooks);
        btnAddToFinishedBooks = findViewById(R.id.btnAddToFinishedBooks);
        btnAddToFavoriteBooks = findViewById(R.id.btnAddToFavoriteBooks);

        btnEdit = findViewById(R.id.btn_edit);
        btnDelete = findViewById(R.id.btn_delete);

        imgBookInfo = findViewById(R.id.imgBookInfo);
    }

    private void reloadDataByBookId(int bookId) {
        if (bookId != -1) {
            Book book = Utils.getBookById(bookId);
            handleFinishedBooks(bookId);
            handleWishListBooks(bookId);
            handleReadingBooks(bookId);
            handleFavoriteBooks(bookId);
            handleEditBook(bookId);
            handleDeleteBook(bookId);
            if (book != null) {
                setData(book);
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