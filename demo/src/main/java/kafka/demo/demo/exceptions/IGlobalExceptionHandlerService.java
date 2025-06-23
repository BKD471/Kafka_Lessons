package kafka.demo.demo.exceptions;

import kafka.demo.demo.dto.ErrorDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

public interface IGlobalExceptionHandlerService {

    /**
     *
     * @param exception - MethodArgumentNotValidException is being thrown
     * @return ResponseEntity<Map<String, String>> - map containing error details
     * */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Map<String, String>> handleMethodArgumentNotValid(final MethodArgumentNotValidException exception);

    /**
     *
     * @param exception - Exception is being thrown
     * @param webRequest - web request
     * @return ResponseEntity<Map<String, String>> - map containing error details
     * */
    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorDetails> handleGenericException(final Exception exception, final WebRequest webRequest);
}
