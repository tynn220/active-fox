package rb.project.activefox.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import rb.project.activefox.models.enums.ApiStatus;
import rb.project.activefox.models.enums.ErrorCode;


@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiBaseResponse<T> {
    private String status;
    private String message;
    private String errorCode;
    private T data;

    public static<T> ApiBaseResponse success(T data) {
        return new ApiBaseResponse<T>("SUCCESS","Success",null,data);
    }

    public static<T> ApiBaseResponse<T> response(ApiStatus status, ErrorCode errorCode, String message, T data) {
        return new ApiBaseResponse<>(status.name(),message,errorCode.getErrorCode(), data);
    }
}
