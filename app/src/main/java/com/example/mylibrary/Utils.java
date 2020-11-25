package com.example.mylibrary;

import android.content.Context;
import android.database.Cursor;

import com.example.mylibrary.constants.BookType;

import java.util.ArrayList;

public class Utils {

    private static ArrayList<Book> allBooks;
    private static ArrayList<Book> readingBooks;
    private static ArrayList<Book> finishedBooks;
    private static ArrayList<Book> favoriteBooks;
    private static ArrayList<Book> wishListBooks;

    private static Utils instance;

    private static MyDatabaseHelper myDB;

    private Utils(Context context) {
        initBooks(context);
    }

    private void initBooks(Context context) {
        myDB = new MyDatabaseHelper(context);
        allBooks = new ArrayList<Book>();
        readingBooks = new ArrayList<Book>();
        finishedBooks = new ArrayList<Book>();
        favoriteBooks = new ArrayList<Book>();
        wishListBooks = new ArrayList<Book>();
//        addMockBooks();

    }

    public static void getInstance(Context context) {
        if (instance == null) {
            instance = new Utils(context);
        }
    }

    public static Book getBookById(int bookId) {
        ArrayList<Book> books = getBookList(BookType.AllBooks);
        if (books != null) {
            for (Book book : books) {
                if (book.getId() == bookId) {
                    return book;
                }
            }
        }
        return null;
    }

    public static void fetchBooks(BookType bookType) {
        Cursor cursor = myDB.getData(BookType.AllBooks);
        resetBookList(bookType);
        if (bookType == BookType.AllBooks) {
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    int id = Integer.parseInt(cursor.getString(0));
                    String title = cursor.getString(1);
                    String author = cursor.getString(2);
                    int pages = Integer.parseInt(cursor.getString(3));
                    String imageUrl = cursor.getString(4);
                    String shortDesc = cursor.getString(5);
                    String longDesc = cursor.getString(6);
                    Book book = new Book(id, title, author, pages, imageUrl, shortDesc, longDesc);
                    addToBookList(book, BookType.AllBooks);
                }
            }
        } else {
            // TODO Check how to fetch data from cursor in this case
            // cursor.getColumnIndex()
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    int id = Integer.parseInt(cursor.getString(0));
                    String title = cursor.getString(1);
                    String author = cursor.getString(2);
                    int pages = Integer.parseInt(cursor.getString(3));
                    String imageUrl = cursor.getString(4);
                    String shortDesc = cursor.getString(5);
                    String longDesc = cursor.getString(6);
                    Book book = new Book(id, title, author, pages, imageUrl, shortDesc, longDesc);
                    addToBookList(book, bookType);
                }
            }
        }
    }

    public static void addMockBooks() {
        Cursor cursor = myDB.getData(BookType.AllBooks);
        if (cursor.getCount() == 0) {
            myDB.addData(new Book("1Q84", "Murakami Haruki", 1350, "https://images-na.ssl-images-amazon.com/images/I/41ffQULRlxL._SX298_BO1,204,203,200_.jpg",
                    "A work of maddening brilliance", "The very thought of Aomame's situation will pain anyone stuck in the quicksand of “1Q84.” You, sucker, will wade through nearly 1,000 uneventful pages while discovering a Tokyo that has two moons and is controlled by creatures that emerge from the mouth of a dead goat."));
            myDB.addData(new Book("Chess endgame study", "A.J.Roy Croft", 250, "https://kbimages1-a.akamaihd.net/6630cf6b-d534-45d9-a27b-ae022b6b3bdc/1200/1200/False/the-chess-endgame-study-1.jpg",
                    "A must-read book for any chess players", "The endgame study, like the chess problem, eliminates over-the-board competition to concentrate on an invented endgame position carefully plotted to fox, bewilder, challenge, and otherwise stimulate the solver."));
            myDB.addData(new Book("Moby Dick", "Herman MelVille", 927, "https://kbimages1-a.akamaihd.net/ae25aaf3-7841-4b90-a175-8e55ca639064/1200/1200/False/moby-dick-222.jpg",
                    "The book of this century", "Moby-Dick is about everything, a bible written in scrimshaw, an adventure spun in allegory, a taxonomy tripping on acid. It seems to exist outside its own time, much like Don Quixote and Tristram Shandy, the poetry of Emily Dickinson. It is so broad and so deep as to accept any interpretation while also staring back and mocking this man-made desire toward interpretation."));
            myDB.addData(new Book("To kill a mockingbird", "Harper Lee", 281, "https://images-na.ssl-images-amazon.com/images/I/81gepf1eMqL.jpg",
                    "A classic of modern American literature", "Compassionate, dramatic, and deeply moving, To Kill A Mockingbird takes readers to the roots of human behavior - to innocence and experience, kindness and cruelty, love and hatred, humor and pathos."));
        }
    }

    public static boolean addToBookList(Book book, BookType bookType) {
        switch (bookType) {
            case AllBooks:
                return allBooks.add(book);
            case ReadingBooks:
                return readingBooks.add(book);
            case FavoriteBooks:
                return favoriteBooks.add(book);
            case FinishedBooks:
                return finishedBooks.add(book);
            case WishListBooks:
                return wishListBooks.add(book);
            default:
                return false;
        }
    }

    public static boolean removeFromBookList(Book book, BookType bookType) {
        switch (bookType) {
            case AllBooks:
                return allBooks.remove(book);
            case ReadingBooks:
                return readingBooks.remove(book);
            case FavoriteBooks:
                return favoriteBooks.remove(book);
            case FinishedBooks:
                return finishedBooks.remove(book);
            case WishListBooks:
                return wishListBooks.remove(book);
            default:
                return false;
        }
    }

    public static void resetBookList(BookType bookType) {
        switch (bookType) {
            case AllBooks:
                allBooks.clear();
                break;
            case ReadingBooks:
                readingBooks.clear();
                break;
            case FavoriteBooks:
                favoriteBooks.clear();
                break;
            case FinishedBooks:
                finishedBooks.clear();
                break;
            case WishListBooks:
                wishListBooks.clear();
                break;
            default:
                break;
        }
    }

    public static ArrayList<Book> getBookList(BookType bookType) {
        switch (bookType) {
            case AllBooks:
                return allBooks;
            case ReadingBooks:
                return readingBooks;
            case FavoriteBooks:
                return favoriteBooks;
            case FinishedBooks:
                return finishedBooks;
            case WishListBooks:
                return wishListBooks;
            default:
                return new ArrayList<>();
        }
    }
}
