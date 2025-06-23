package kafka.demo.demo.exceptions;

import kafka.demo.demo.controllers.ControllerImpl;
import kafka.demo.demo.dto.ErrorDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandlerServiceImpl implements IGlobalExceptionHandlerService {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandlerServiceImpl.class);

    /**
     * This method handles any invalidation in request dto fields
     *
     * @param exception - MethodArgumentNotValidException is being thrown
     * @return ResponseEntity<Map<String, String>> - map containing error details
     * */
    @Override
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValid(final MethodArgumentNotValidException exception) {
        final Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);

        });
        logger.error(String.format("<================ %s >", exception.getMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * This method handles any generic exception that's being thrown from codebase.
     *
     * @param exception - Exception is being thrown
     * @param webRequest - web request
     * @return ResponseEntity<Map<String, String>> - map containing error details
     * */
    @Override
    public ResponseEntity<ErrorDetails> handleGenericException(final Exception exception, final WebRequest webRequest) {
        final ErrorDetails error =
                new ErrorDetails(LocalTime.now(), exception.getMessage(), webRequest.getDescription(false));
        logger.error(String.format("<================ %s >", exception.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
