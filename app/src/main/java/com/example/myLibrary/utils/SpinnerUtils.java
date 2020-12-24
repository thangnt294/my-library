package com.example.myLibrary.utils;

import com.example.myLibrary.constants.Genre;

import java.util.ArrayList;

public class SpinnerUtils {
    private static ArrayList<String> genres;

    private static SpinnerUtils instance;

    private SpinnerUtils() {
        genres = new ArrayList<>();
        populateGenres();
    }

    public static void getInstance() {
        if (instance == null) {
            instance = new SpinnerUtils();
        }
    }

    private static void populateGenres() {
        genres.add(Genre.ALL.getValue());
        genres.add(Genre.ADVENTURE.getValue());
        genres.add(Genre.GUIDE.getValue());
        genres.add(Genre.ART.getValue());
        genres.add(Genre.CHILDREN.getValue());
        genres.add(Genre.CONTEMPORARY.getValue());
        genres.add(Genre.COOKING.getValue());
        genres.add(Genre.HISTORICAL_FICTION.getValue());
        genres.add(Genre.ROMANCE.getValue());
        genres.add(Genre.SCIENCE_FICTION.getValue());
        genres.add(Genre.HEALTH.getValue());
        genres.add(Genre.HISTORY.getValue());
        genres.add(Genre.HORROR.getValue());
        genres.add(Genre.HUMOR.getValue());
        genres.add(Genre.SELF_HELP.getValue());
        genres.add(Genre.DEVELOPMENT.getValue());
        genres.add(Genre.FANTASY.getValue());
        genres.add(Genre.MOTIVATIONAL.getValue());
        genres.add(Genre.PARANORMAL.getValue());
        genres.add(Genre.TRAVEL.getValue());
        genres.add(Genre.MEMOIR.getValue());
        genres.add(Genre.MYSTERY.getValue());
        genres.add(Genre.THRILLER.getValue());
    }

    public static ArrayList<String> getGenres() {
        if (genres != null) {
            genres.clear();
            populateGenres();
        }
        return genres;
    }
}
