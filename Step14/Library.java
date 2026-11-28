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

    public void addItem(LibraryItem item) {
        items.put(item.getId(), item);
    }

    public void addMember(Member member) {
        members.put(member.getId(), member);
    }

    public int borrow(String memberId, String itemId) throws ItemNotFoundException, MemberNotFoundException,
            ItemNotAvailableException, BorrowLimitExceededException {

        if (!items.containsKey(itemId)) {
            throw new ItemNotFoundException("item " + itemId + " not found");
        }

        if (!members.containsKey(memberId)) {
            throw new MemberNotFoundException("member " + memberId + " not found");
        }

        LibraryItem item = items.get(itemId);
        Member member = members.get(memberId);

        if (!(item instanceof Borrowable)) {
            throw new ItemNotAvailableException(itemId + " cannot be borrowed");
        }

        if (loans.containsKey(itemId)) {
            throw new ItemNotAvailableException(itemId + " is on loan");
        }

        int count = 0;

        for (Loan loan : loans.values()) {
            if (loan.getMember().getId().equals(memberId)) {
                count++;
            }
        }

        if (count >= member.getMaxItems()) {
            throw new BorrowLimitExceededException(memberId + " has reached the limit of " + member.getMaxItems());
        }

        int dueDay = today + member.getLoanPeriod();

        Loan loan = new Loan(item, member, today, dueDay);
        loans.put(itemId, loan);

        return dueDay;
    }

    public String returnItem(String itemId) throws ItemNotOnLoanException {

        if (!loans.containsKey(itemId)) {
            throw new ItemNotOnLoanException(itemId + " is not on loan");
        }

        Loan loan = loans.get(itemId);
        int charge = loan.fine(today);
        loans.remove(itemId);

        LinkedList<String> waiting = reservations.get(itemId);

        if (waiting == null || waiting.isEmpty()) {
            return "OK fine " + charge;
        }

        Iterator<String> it = waiting.iterator();

        while (it.hasNext()) {
            String memberId = it.next();
            Member member = members.get(memberId);

            int count = 0;

            for (Loan x : loans.values()) {
                if (x.getMember().getId().equals(memberId)) {
                    count++;
                }
            }

            if (count >= member.getMaxItems()) {
                continue;
            }

            it.remove();

            int dueDay = today + member.getLoanPeriod();

            Loan newLoan = new Loan(items.get(itemId), member, today, dueDay);
            loans.put(itemId, newLoan);

            if (waiting.isEmpty()) {
                reservations.remove(itemId);
            }

            return "OK fine " + charge + ", issued to " + memberId + " due on day " + dueDay;
        }
        return "OK fine " + charge;
    }

    public int advance(int days) {
        today += days;
        return today;
    }

    public List<String> inventory() {
        List<String> ids = new ArrayList<>();

        for (String x : items.keySet()) {
            ids.add(x);
        }

        Collections.sort(ids);
        List<String> ans = new ArrayList<>();

        for (String id : ids) {
            LibraryItem item = items.get(id);
            Loan loan = loans.get(id);
            String status = "";

            if (!(item instanceof Borrowable)) {
                status = "NOT_BORROWABLE";
            }

            else if (loans.containsKey(id)) {
                status = "ON_LOAN|" + loan.getMember().getId();
            }

            else {
                status = "AVAILABLE";
            }

            ans.add(id + "|" + item.getType() + "|" + status);
        }
        return ans;
    }

    public List<LibraryItem> search(String text) {
        List<LibraryItem> ans = new ArrayList<>();

        for (LibraryItem x : items.values()) {
            if (x.matchesTitle(text)) {
                ans.add(x);
            }
        }

        ans.sort(Comparator.comparing(t -> t.getTitle().toLowerCase()));
        return ans;
    }

    public List<Loan> getLoans(String memberId) {
        List<Loan> ans = new ArrayList<>();

        for (Loan x : loans.values()) {
            if (memberId.equals(x.getMember().getId())) {
                ans.add(x);
            }
        }

        ans.sort(Comparator.comparing((Loan t) -> t.getDueDay()).thenComparing(t -> t.getItem().getId()));
        return ans;
    }

    public List<Loan> getOverdue() {
        List<Loan> ans = new ArrayList<>();

        for (Loan x : loans.values()) {
            if (x.daysLate(today) > 0) {
                ans.add(x);
            }
        }

        ans.sort(
                Comparator.comparing((Loan t) -> t.daysLate(today)).reversed().thenComparing(t -> t.getItem().getId()));
        return ans;
    }

    public void reserve(String memberId, String itemId) throws ItemNotFoundException, MemberNotFoundException,
            ItemNotAvailableException, ReservationNotAllowedException {

        if (!items.containsKey(itemId)) {
            throw new ItemNotFoundException("item " + itemId + " not found");
        }

        if (!members.containsKey(memberId)) {
            throw new MemberNotFoundException("member " + memberId + " not found");
        }

        LibraryItem item = items.get(itemId);

        if (!(item instanceof Borrowable)) {
            throw new ItemNotAvailableException(itemId + " cannot be borrowed");
        }

        if (!loans.containsKey(itemId)) {
            throw new ReservationNotAllowedException(itemId + " is available");
        }

        Loan loan = loans.get(itemId);

        if (loan.getMember().getId().equals(memberId)) {
            throw new ReservationNotAllowedException(memberId + " already has " + itemId);
        }

        LinkedList<String> queue = reservations.get(itemId);

        if (queue == null) {
            queue = new LinkedList<>();
            reservations.put(itemId, queue);
        }

        if (queue.contains(memberId)) {
            throw new ReservationNotAllowedException(memberId + " already reserved " + itemId);
        }

        queue.add(memberId);
    }
}
