import java.util.*;

class Library {
    private int today;
    private Map<String, LibraryItem> items;
    private Map<String, Member> members;
    private Map<String, Loan> loans;
    private Map<String, LinkedList<String>> reservations;

    public Library() {
        today = 0;
        items = new HashMap<>();
        members = new HashMap<>();
        loans = new HashMap<>();
        reservations = new HashMap<>();
    }

    public int getToday() {
        return today;
    }
}
