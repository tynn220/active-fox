package rb.project.activefox.exception;


import rb.project.activefox.models.enums.ApiStatus;
import rb.project.activefox.models.enums.ErrorCode;

public class GlobalException extends BaseException {
    public GlobalException(ErrorCode errorCode) {
        super(ApiStatus.BUSINESS_ERROR, errorCode);
    }
}
