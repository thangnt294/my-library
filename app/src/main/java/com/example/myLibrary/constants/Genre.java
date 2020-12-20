package com.example.myLibrary.constants;

public enum Genre {
    FANTASY("Fantasy"),
    ADVENTURE("Adventure"),
    ROMANCE("Romance"),
    CONTEMPORARY("Contemporary"),
    MYSTERY("Mystery"),
    HORROR("Horror"),
    THRILLER("Thriller"),
    PARANORMAL("Paranormal"),
    HISTORICAL_FICTION("Historical fiction"),
    SCIENCE_FICTION("Science fiction"),
    MEMOIR("Memoir"),
    COOKING("Cooking"),
    ART("Art"),
    SELF_HELP("Self help"),
    DEVELOPMENT("Development"),
    MOTIVATIONAL("Motivational"),
    HEALTH("Health"),
    HISTORY("History"),
    TRAVEL("Travel"),
    GUIDE("Guide"),
    HUMOR("Humor"),
    CHILDREN("Children"),
    ALL("All");

    private String value;

    Genre(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
