abstract class LibraryItem {
    private String id;
    private String title;

    public LibraryItem(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean matchesTitle(String text) {
        return this.getTitle().toLowerCase().contains(text.toLowerCase());
    }

    public abstract String getType();
}


interface Borrowable {
    public int getFinePerDay();
}


class Book extends LibraryItem implements Borrowable {
    private String author;

    public Book(String id, String title, String author) {
        super(id, title);
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public String getType() {
        return "BOOK";
    }

    public int getFinePerDay() {
        return 2;
    }
}


class Magazine extends LibraryItem implements Borrowable {
    private String issue;

    public Magazine(String id, String title, String issue) {
        super(id, title);
        this.issue = issue;
    }

    public String getIssue() {
        return issue;
    }

    public String getType() {
        return "MAGAZINE";
    }

    public int getFinePerDay() {
        return 1;
    }
}


class ReferenceBook extends LibraryItem {
    private String author;

    public ReferenceBook(String id, String title, String author) {
        super(id, title);
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public String getType() {
        return "REFERENCE";
    }
}