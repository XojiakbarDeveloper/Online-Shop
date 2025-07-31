package uz.onlineshop.authservice.config.application.exceptions;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import uz.onlineshop.authservice.config.payload.base.ApiResult;
import uz.onlineshop.authservice.config.payload.base.ErrorResponse;
import uz.onlineshop.authservice.enums.ErrorTypeEnum;


import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MIN_VALUE)
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {RestException.class})
    public ResponseEntity<ApiResult<ErrorResponse>> handleException(RestException ex) {
        ErrorTypeEnum errorTypeEnum = ex.getErrorTypeEnum();
        ErrorResponse errorData = new ErrorResponse(errorTypeEnum.getMessage(), errorTypeEnum.getStatus().value());
        return new ResponseEntity<>(ApiResult.errorResponse(errorData), ex.getStatus());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleException(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException -> {}", ex.getMessage());

        FieldError fieldError = ex.getBindingResult().getFieldErrors().stream().findFirst().orElse(null);
        String errorMessage = "";

        if (fieldError != null) {
            errorMessage = fieldError.getDefaultMessage();
        }
        return new ResponseEntity<>(ApiResult.errorResponse(errorMessage, 400), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {TypeMismatchException.class})
    public ResponseEntity<?> handleException(TypeMismatchException ex) {
        log.error("TypeMismatchException -> {}", ex.getMessage());
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> unauthorizedException(AuthenticationException ex) {
        log.error("UnAuthorizedException -> {}", ex.getMessage());
        return new ResponseEntity<>(
                ApiResult.errorResponse("Unauthorized", 401),
                UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> accessDeniedException(AccessDeniedException ex) {
        log.error("AccessDeniedException -> {}", ex.getMessage());
        return new ResponseEntity<>(
                ApiResult.errorResponse("Forbidden", 403),
                FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        log.error("InternalServerError -> {}", ex.getMessage());
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 500),
                INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<?> handleException(HttpMessageNotReadableException ex) {
        log.error("HttpMessageNotReadableException -> {}", ex.getMessage());
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseEntity<?> handleException(MissingServletRequestParameterException ex) {
        log.error("MissingServletRequestParameterException -> {}", ex.getMessage());
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ServletRequestBindingException.class})
    public ResponseEntity<?> handleException(ServletRequestBindingException ex) {
        log.error("ServletRequestBindingException -> {}", ex.getMessage());
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MissingServletRequestPartException.class})
    public ResponseEntity<?> handleException(MissingServletRequestPartException ex) {
        log.error("MissingServletRequestPartException -> {}", ex.getMessage());
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MultipartException.class})
    public ResponseEntity<?> handleException(MultipartException ex) {
        log.error("MultipartException -> {}", ex.getMessage());
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {BindException.class})
    public ResponseEntity<?> handleException(BindException ex) {
        log.error("BindException -> {}", ex.getMessage());
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MissingPathVariableException.class})
    public ResponseEntity<?> handleException(MissingPathVariableException ex) {
        log.error("MissingPathVariableException -> {}", ex.getMessage());
        return new ResponseEntity<>(
                ApiResult.errorResponse(ErrorTypeEnum.PAGE_NOT_FOUND.getMessage(), 404),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {NoHandlerFoundException.class})
    public ResponseEntity<?> handleException(NoHandlerFoundException ex) {
        log.error("NoHandlerFoundException -> {}", ex.getMessage());
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 404),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<?> handleException(HttpRequestMethodNotSupportedException ex) {
        log.error("HttpRequestMethodNotSupportedException -> {}", ex.getMessage());
        return new ResponseEntity<>(
                ApiResult.errorResponse(ErrorTypeEnum.METHOD_ERROR.getMessage(), 405),
                HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = {HttpMediaTypeNotAcceptableException.class})
    public ResponseEntity<?> handleException(HttpMediaTypeNotAcceptableException ex) {
        log.error("HttpMediaTypeNotAcceptableException -> {}", ex.getMessage());
        return new ResponseEntity<>(
                ApiResult.errorResponse(ErrorTypeEnum.UNACCEPTABLE.getMessage(), 406),
                HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = {HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<?> handleException(HttpMediaTypeNotSupportedException ex) {
        log.error("HttpMediaTypeNotSupportedException -> {}", ex.getMessage());
        return new ResponseEntity<>(
                ApiResult.errorResponse(
                        ErrorTypeEnum.UNSUPPORTED_MEDIA_TYPE.getMessage(),
                        415
                ),
                HttpStatus.UNSUPPORTED_MEDIA_TYPE
        );
    }

    @ExceptionHandler(value = {ConversionNotSupportedException.class})
    public ResponseEntity<?> handleException(ConversionNotSupportedException ex) {
        log.error("ConversionNotSupportedException -> {}", ex.getMessage());
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 500),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {HttpMessageNotWritableException.class})
    public ResponseEntity<?> handleException(HttpMessageNotWritableException ex) {
        log.error("HttpMessageNotWritableException -> {}", ex.getMessage());
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 500),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {AsyncRequestTimeoutException.class})
    public ResponseEntity<?> handleException(AsyncRequestTimeoutException ex) {
        log.error("AsyncRequestTimeoutException -> {}", ex.getMessage());
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 503),
                HttpStatus.SERVICE_UNAVAILABLE);
    }

}
