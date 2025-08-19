package rb.project.activefox.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    TOO_MANY_REQUESTS("9", "too.many-requests"),
    TOKEN_INVALID("8", "request.token.invalid"),
    BAD_REQUEST("10", "request.bad"),
    ;

    private final String errorCode;

    private final String message;
}
