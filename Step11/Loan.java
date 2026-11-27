class Loan {
    private LibraryItem item;
    private Member member;
    private int borrowedDay;
    private int dueDay;

    public Loan(LibraryItem item, Member member, int borrowedDay, int dueDay) {
        this.item = item;
        this.member = member;
        this.borrowedDay = borrowedDay;
        this.dueDay = dueDay;
    }

    public LibraryItem getItem() {
        return item;
    }

    public Member getMember() {
        return member;
    }

    public int getBorrowedDay() {
        return borrowedDay;
    }

    public int getDueDay() {
        return dueDay;
    }

    public int daysLate(int today) {
        return today - dueDay > 0 ? today - dueDay : 0;
    }

    public int fine(int today) {
        if(item instanceof Borrowable x) {
            return x.getFinePerDay() * daysLate(today);
        }

        return 0;
    }
}