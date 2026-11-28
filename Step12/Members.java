abstract class Member {
    private String id;
    private String name;

    public Member(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract int getMaxItems();
    public abstract int getLoanPeriod();
}


class Student extends Member {

    public Student(String id, String name) {
        super(id, name);
    }

    public int getMaxItems() {
        return 3;
    }

    public int getLoanPeriod() {
        return 14;
    }
}


class Staff extends Member {

    public Staff(String id, String name) {
        super(id, name);
    }

    public int getMaxItems() {
        return 4;
    }

    public int getLoanPeriod() {
        return 21;
    }
}


class Faculty extends Member {

    public Faculty(String id, String name) {
        super(id, name);
    }

    public int getMaxItems() {
        return 5;
    }

    public int getLoanPeriod() {
        return 30;
    }
}