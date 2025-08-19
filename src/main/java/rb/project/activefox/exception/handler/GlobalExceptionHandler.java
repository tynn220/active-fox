package rb.project.activefox.exception.handler;

import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import rb.project.activefox.exception.AuthorizeException;
import rb.project.activefox.exception.GlobalException;
import rb.project.activefox.exception.RateLimitException;
import rb.project.activefox.models.ApiBaseResponse;
import rb.project.activefox.models.enums.ApiStatus;
import rb.project.activefox.models.enums.ErrorCode;
import rb.project.activefox.utilserivces.message.Language;
import rb.project.activefox.utilserivces.message.MessageUtil;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<Object> handleBaseException(GlobalException e) {
        String message = MessageUtil.getMessage(e.getErrorCode().getMessage(), Language.en, messageSource);
        ApiBaseResponse response = ApiBaseResponse.response(ApiStatus.BUSINESS_ERROR, e.getErrorCode(), message, null);
        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler({RateLimitException.class})
    public ResponseEntity<Object> handleRateLimit(RateLimitException e) {
        String message = MessageUtil.getMessage(e.getErrorCode().getMessage(), Language.en, messageSource);
        ApiBaseResponse response = ApiBaseResponse.response(ApiStatus.BUSINESS_ERROR, e.getErrorCode(), message, null);
        return ResponseEntity.status(429).body(response);
    }

    @ExceptionHandler({ExpiredJwtException.class, ClaimJwtException.class})
    public ResponseEntity<Object> handleJwtException(ExpiredJwtException e) {
        String message = MessageUtil.getMessage(ErrorCode.TOKEN_INVALID.getMessage(), Language.en, messageSource);
        ApiBaseResponse response = ApiBaseResponse.response(ApiStatus.BUSINESS_ERROR, ErrorCode.TOKEN_INVALID, message, null);
        return ResponseEntity.status(401).body(response);
    }

    @ExceptionHandler({AuthorizeException.class})
    public ResponseEntity<Object> handleValidationException(AuthorizeException e) {
        String message = MessageUtil.getMessage(e.getErrorCode().getMessage(), Language.en, messageSource);
        ApiBaseResponse response = ApiBaseResponse.response(ApiStatus.BUSINESS_ERROR, e.getErrorCode(), message, null);
        return ResponseEntity.status(401).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        String message = MessageUtil.getMessage(ErrorCode.BAD_REQUEST.getMessage(), Language.en, messageSource);
        ApiBaseResponse response = ApiBaseResponse.response(ApiStatus.BUSINESS_ERROR, ErrorCode.BAD_REQUEST, message, errors);
        return ResponseEntity.status(400).body(response);
    }
}
