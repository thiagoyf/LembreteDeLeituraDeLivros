package model;

/**
 * Created by thiagoyf on 12/10/16.
 */

public class Book {

    private String title;
    private int totalPages;

    /**
     * Create a object book with title and totalPages representing respectively the book's name and
     * total pages.
     *
     * @param title the book's name
     * @param totalPages the total pages of the book
     */
    public Book(String title, int totalPages){
        this.setTitle(title);
        this.setTotalPages(totalPages);
    }

    /**
     * Returns the book's name
     *
     * @return the book's name
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the title of the book
     *
     * @param title a representing the book's name
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the total page of the book
     *
     * @return the total page of the book
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Set the total pages
     *
     * @param totalPages a integer number representing the total pages
     */
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

}
