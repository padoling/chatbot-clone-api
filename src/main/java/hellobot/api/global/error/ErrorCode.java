package hellobot.api.global.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    BAD_REQUEST(400, "Bad Request", "Request type or content is not valid."),
    NOT_FOUND(404, "Not Found", "Requested resource is not found"),
    CONFLICT(409, "Conflict", "Requested resource conflicts with existing resource."),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error", "Internal Server has an error.");

    private int status;
    private String error;
    private String message;

    ErrorCode(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }
}
