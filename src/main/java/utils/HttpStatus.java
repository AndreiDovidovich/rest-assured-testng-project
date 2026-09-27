package utils;

public enum HttpStatus {

    OK(200),
    CREATED(201),
    FORBIDDEN(403),
    NOT_FOUND(404);

    private final int code;

    HttpStatus(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }

    @Override
    public String toString() {
        return code + " " + name();
    }
}