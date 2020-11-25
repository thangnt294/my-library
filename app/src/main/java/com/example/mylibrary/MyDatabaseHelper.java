package com.example.mylibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.mylibrary.constants.BookType;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "MyLibrary";
    private static final int DATABASE_VERSION = 3;

    public static final String ALL_BOOKS = "all_books";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "book_title";
    public static final String COLUMN_AUTHOR = "book_author";
    public static final String COLUMN_PAGES = "book_pages";
    public static final String COLUMN_IMAGE_URL = "book_image_url";
    public static final String COLUMN_SHORT_DESC = "book_short_desc";
    public static final String COLUMN_LONG_DESC = "book_long_desc";

    public static final String FAVORITE_BOOKS = "favorite_books";
    public static final String READING_BOOKS = "reading_books";
    public static final String FINISHED_BOOKS = "finished_books";
    public static final String WISH_LIST_BOOKS = "wish_list_books";
    public static final String COLUMN_BOOK_ID = "book_id";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String allBooksQuery = "CREATE TABLE " + ALL_BOOKS +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_AUTHOR + " TEXT, " +
                COLUMN_PAGES + " INTEGER, " +
                COLUMN_IMAGE_URL + " TEXT, " +
                COLUMN_SHORT_DESC + " TEXT, " +
                COLUMN_LONG_DESC + " TEXT);";
        String favoriteBooksQuery = "CREATE TABLE " + FAVORITE_BOOKS +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BOOK_ID + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_BOOK_ID + ") REFERENCES all_books(" +
                COLUMN_ID + ")" + "ON DELETE CASCADE);";
        String readingBooksQuery = "CREATE TABLE " + READING_BOOKS +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BOOK_ID + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_BOOK_ID + ") REFERENCES all_books(" +
                COLUMN_ID + ")" + "ON DELETE CASCADE);";
        String finishedBooksQuery = "CREATE TABLE " + FINISHED_BOOKS +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BOOK_ID + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_BOOK_ID + ") REFERENCES all_books(" +
                COLUMN_ID + ")" + "ON DELETE CASCADE);";
        String wishListBooksQuery = "CREATE TABLE " + WISH_LIST_BOOKS +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BOOK_ID + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_BOOK_ID + ") REFERENCES all_books(" +
                COLUMN_ID + ")" + "ON DELETE CASCADE);";

        db.execSQL(allBooksQuery);
        db.execSQL(favoriteBooksQuery);
        db.execSQL(readingBooksQuery);
        db.execSQL(finishedBooksQuery);
        db.execSQL(wishListBooksQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "my_library");
        db.execSQL("DROP TABLE IF EXISTS " + ALL_BOOKS);
        db.execSQL("DROP TABLE IF EXISTS " + FAVORITE_BOOKS);
        db.execSQL("DROP TABLE IF EXISTS " + FINISHED_BOOKS);
        db.execSQL("DROP TABLE IF EXISTS " + READING_BOOKS);
        db.execSQL("DROP TABLE IF EXISTS " + WISH_LIST_BOOKS);
        onCreate(db);
    }

    public long addData(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, book.getTitle());
        cv.put(COLUMN_AUTHOR, book.getAuthor());
        cv.put(COLUMN_PAGES, book.getPages());
        cv.put(COLUMN_IMAGE_URL, book.getImageUrl());
        cv.put(COLUMN_SHORT_DESC, book.getShortDesc());
        cv.put(COLUMN_LONG_DESC, book.getLongDesc());

        return db.insert(ALL_BOOKS, null, cv);
    }

    public long addToBookTypes(int bookId, BookType bookType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_BOOK_ID, bookId);

        if (bookType == BookType.FavoriteBooks) {
            return db.insert(FAVORITE_BOOKS, null, cv);
        } else if (bookType == BookType.FinishedBooks) {
            return db.insert(FINISHED_BOOKS, null, cv);
        } else if (bookType == BookType.ReadingBooks) {
            return db.insert(READING_BOOKS, null, cv);
        } else {
            return db.insert(WISH_LIST_BOOKS, null, cv);
        }
    }

    public long updateData(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, book.getTitle());
        cv.put(COLUMN_AUTHOR, book.getAuthor());
        cv.put(COLUMN_PAGES, book.getPages());
        cv.put(COLUMN_IMAGE_URL, book.getImageUrl());
        cv.put(COLUMN_SHORT_DESC, book.getShortDesc());
        cv.put(COLUMN_LONG_DESC, book.getLongDesc());

        return db.update(ALL_BOOKS, cv, "id=?", new String[]{Integer.toString(book.getId())});
    }

    Cursor getData(BookType bookType) {
        String tableName, query;

        // Populate query
        if (bookType == BookType.AllBooks) {
            query = "SELECT * FROM " + ALL_BOOKS;
        } else {
            if (bookType == BookType.FavoriteBooks) {
                tableName = FAVORITE_BOOKS;
            } else if (bookType == BookType.FinishedBooks) {
                tableName = FINISHED_BOOKS;
            } else if (bookType == BookType.ReadingBooks) {
                tableName = READING_BOOKS;
            } else {
                tableName = WISH_LIST_BOOKS;
            }
            query = "SELECT * FROM " + tableName +
                    " INNER JOIN " + ALL_BOOKS + " ON " +
                    tableName + "." + COLUMN_BOOK_ID + " = " +
                    ALL_BOOKS + "." + COLUMN_ID;
        }

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public long deleteData(int bookId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(ALL_BOOKS, "id=?", new String[]{Integer.toString(bookId)});
    }
}