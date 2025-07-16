package uz.onlineshop.config.payload.base;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.io.Serializable;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResult<T> implements Serializable {

    private final Boolean success;

    private String message;

    private T data;

    private ApiResult(Boolean success) {
        this.success = success;
    }

    private ApiResult(T data, Boolean success) {
        this.data = data;
        this.success = success;
    }

    private ApiResult(T data, Boolean success, String message) {
        this.data = data;
        this.success = success;
        this.message = message;
    }

    private ApiResult(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }


    public static <E> ApiResult<E> successResponse(E data) {
        return new ApiResult<>(data, Boolean.TRUE);
    }

    public static <E> ApiResult<E> successResponse(E data, String message) {
        return new ApiResult<>(data, Boolean.TRUE, message);
    }

    public static <E> ApiResult<E> successResponse() {
        return new ApiResult<>(Boolean.TRUE);
    }

    public static ApiResult<String> successResponse(String message) {
        return new ApiResult<>(message, Boolean.TRUE);
    }

    public static ApiResult<ErrorResponse> errorResponse(String errorMsg) {
        return new ApiResult<>(errorMsg, Boolean.FALSE);
    }

    public static ApiResult<ErrorResponse> errorResponse(String errorMsg, Integer errorCode) {
        return new ApiResult<>(new ErrorResponse(errorMsg, errorCode), Boolean.FALSE);
    }

    public static ApiResult<ErrorResponse> errorResponse(ErrorResponse data) {
        return new ApiResult<>(data, Boolean.FALSE);
    }


    public static <T> ApiResult<T> error() {
        return new ApiResult<>(null, false, "Internal server error");
    }

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(data, true, "Success");
    }

    public static <T> ApiResult<T> success(String message) {
        return new ApiResult<>(null, true, message);
    }

    public static <T> ApiResult<T> error(String errorMessage) {
        return new ApiResult<>(null, false, errorMessage);
    }

    public static <T> ApiResult<T> notFound() {
        return new ApiResult<>(null, false, "Not Found");
    }

    public static <T> ApiResult<T> forbidden(String message) {
        return new ApiResult<>(null, false, message);
    }



}
