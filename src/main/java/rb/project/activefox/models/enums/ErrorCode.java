package rb.project.activefox.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    TOO_MANY_REQUESTS("9", "too.many-requests"),
    BAD_REQUEST("10", "request.bad"),
    ;

    private final String errorCode;

    private final String message;
}
