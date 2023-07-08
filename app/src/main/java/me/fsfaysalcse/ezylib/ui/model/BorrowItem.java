package me.fsfaysalcse.ezylib.ui.model;

public class BorrowItem {
    private String title;
    private String author;
    private boolean isAvailable;

    public BorrowItem(String title, String author, boolean isAvailable) {
        this.title = title;
        this.author = author;
        this.isAvailable = isAvailable;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}

