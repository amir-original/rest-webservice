package httpProvider;

public class ResponseStatus {
    private final int status;
    private final String message;

    public ResponseStatus(String message, int statusCode) {
        this.message = message;
        this.status = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
