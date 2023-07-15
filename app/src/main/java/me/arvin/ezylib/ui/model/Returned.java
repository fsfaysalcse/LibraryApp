package me.arvin.ezylib.ui.model;

public class Returned {
    private String returnedId;
    private String bookId;
    private String borrowedId;
    private String bookTitle;
    private String studentId;
    private String returnTimestamp;

    public Returned() {
    }


    public Returned(String returnedId, String bookId, String borrowedId, String bookTitle, String studentId, String returnTimestamp) {
        this.returnedId = returnedId;
        this.bookId = bookId;
        this.borrowedId = borrowedId;
        this.bookTitle = bookTitle;
        this.studentId = studentId;
        this.returnTimestamp = returnTimestamp;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBorrowedId() {
        return borrowedId;
    }

    public void setBorrowedId(String borrowedId) {
        this.borrowedId = borrowedId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getReturnTimestamp() {
        return returnTimestamp;
    }

    public void setReturnTimestamp(String returnTimestamp) {
        this.returnTimestamp = returnTimestamp;
    }

    public String getReturnedId() {
        return returnedId;
    }

    public void setReturnedId(String returnedId) {
        this.returnedId = returnedId;
    }
}
