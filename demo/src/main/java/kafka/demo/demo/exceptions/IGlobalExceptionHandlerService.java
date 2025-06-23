package kafka.demo.demo.exceptions;

import kafka.demo.demo.dto.ErrorDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

public interface IGlobalExceptionHandlerService {

    /**
     * This method handles any invalidation in request dto fields
     *
     * @param exception - catches MethodArgumentNotValidException
     * @return ResponseEntity<Map<String, String>> - map containing error details
     * */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException exception
    );

    /**
     * This method handles any generic exception that's being thrown from codebase.
     *
     * @param exception - catches any generic Exception
     * @param webRequest - web request
     * @return ResponseEntity<ErrorDetails> - error details object with status code
     * */
    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorDetails> handleGenericException(
            final Exception exception,
            final WebRequest webRequest
    );
}
