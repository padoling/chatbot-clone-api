package hellobot.api.global.error;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private String error;
    private String message;

    public ErrorResponse(final ErrorCode errorCode) {
        this.error = errorCode.getError();
        this.message = errorCode.getMessage();
    }

    private ErrorResponse(final ErrorCode errorCode, String message) {
        this.error = errorCode.getError();
        this.message = message;
    }

    public static ErrorResponse of(final ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }

    public static ErrorResponse of(final ErrorCode errorCode, String message) {
        return new ErrorResponse(errorCode, message);
    }
}
