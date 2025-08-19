package rb.project.activefox.exception;

import lombok.Getter;
import lombok.Setter;
import rb.project.activefox.models.enums.ApiStatus;
import rb.project.activefox.models.enums.ErrorCode;


@Getter
@Setter
public class BaseException extends RuntimeException {
    private ApiStatus status;
    private String message;
    private ErrorCode errorCode;

    public BaseException(ApiStatus status, String message, ErrorCode errorCode) {
        super(message);
        this.status = status;
        this.message = message;
        this.errorCode = errorCode;
    }
    public BaseException(ApiStatus status, ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = status;
        this.errorCode = errorCode;
    }
}
