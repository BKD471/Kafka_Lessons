package kafka.demo.demo.exceptions;

import kafka.demo.demo.dto.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

public interface IGlobalExceptionHandlerService {

    /**
     * This method handles any invalidation in request dto fields
     *
     * @param exception - catches MethodArgumentNotValidException
     * @param webRequest - web request
     * @return Map<String, ErrorDetails> - map containing error details for every dto fields
     * */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<String, ErrorDetails> handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException exception,
            final WebRequest webRequest
    );

    /**
     * This method handles any generic exception that's being thrown from codebase.
     *
     * @param exception - catches any generic Exception
     * @param webRequest - web request
     * @return ErrorDetails - error details object
     * */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ErrorDetails handleGenericException(
            final Exception exception,
            final WebRequest webRequest
    );
}
