package SplitWise.model;

public record User(String id, String name) {
    public User {
        if (id == null || id.isBlank()
                || name == null || name.isBlank()) {
            throw new IllegalArgumentException("Invalid user");
        }
    }
}
