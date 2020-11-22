package com.example.mylibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "MyLibrary";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_library";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "book_title";
    private static final String COLUMN_AUTHOR = "book_author";
    private static final String COLUMN_PAGES = "book_pages";
    private static final String COLUMN_IMAGE_URL = "book_image_url";
    private static final String COLUMN_SHORT_DESC = "book_short_desc";
    private static final String COLUMN_LONG_DESC = "book_long_desc";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_AUTHOR + " TEXT, " +
                COLUMN_PAGES + " INTEGER, " +
                COLUMN_IMAGE_URL + " TEXT, " +
                COLUMN_SHORT_DESC + " TEXT, " +
                COLUMN_LONG_DESC + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, book.getTitle());
        cv.put(COLUMN_AUTHOR, book.getAuthor());
        cv.put(COLUMN_PAGES, book.getPages());
        cv.put(COLUMN_IMAGE_URL, book.getImageUrl());
        cv.put(COLUMN_SHORT_DESC, book.getShortDesc());
        cv.put(COLUMN_LONG_DESC, book.getLongDesc());

        return db.insert(TABLE_NAME, null, cv);
    }

    public long updateBook(Book book) {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, book.getTitle());
        cv.put(COLUMN_AUTHOR, book.getAuthor());
        cv.put(COLUMN_PAGES, book.getPages());
        cv.put(COLUMN_IMAGE_URL, book.getImageUrl());
        cv.put(COLUMN_SHORT_DESC, book.getShortDesc());
        cv.put(COLUMN_LONG_DESC, book.getLongDesc());

        return db.update(TABLE_NAME, cv, "id=?", new String[]{Integer.toString(book.getId())});
    }

    Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}