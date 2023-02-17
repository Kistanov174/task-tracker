package model;

public enum Status {
    NEW ("NEW"),
    IN_PROGRESS ("IN_PROGRESS"),
    DONE ("DONE");

    private final String title;

    Status(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
