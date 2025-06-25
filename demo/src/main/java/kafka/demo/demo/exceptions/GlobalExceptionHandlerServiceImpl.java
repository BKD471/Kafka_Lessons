package kafka.demo.demo.exceptions;

import kafka.demo.demo.dto.ErrorDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandlerServiceImpl implements IGlobalExceptionHandlerService {

    private static final Logger logger =
            LoggerFactory.getLogger(GlobalExceptionHandlerServiceImpl.class);

    /**
     * This method handles any invalidation in request dto fields
     *
     * @param exception - catches MethodArgumentNotValidException
     * @param webRequest - web request
     * @return Map<String, ErrorDetails> - map containing error details
     * */
    @Override
    public Map<String, ErrorDetails> handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException exception,
            final WebRequest webRequest
    ) {
        final ConcurrentHashMap<String, ErrorDetails> errorDetailsMap = new ConcurrentHashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            final String fieldName = ((FieldError) error).getField();
            final String errorMessage = error.getDefaultMessage();
            errorDetailsMap.put(
                    fieldName,
                    new ErrorDetails(
                            LocalTime.now(),
                            errorMessage,
                            webRequest.getDescription(false)
                    )
            );
        });
        logger.error(String.format("<================ %s >", exception.getMessage()));
        return errorDetailsMap;
    }

    /**
     * This method handles any generic exception that's being thrown from codebase.
     *
     * @param exception - catches any generic Exception
     * @param webRequest - web request
     * @return ErrorDetails - error details object
     * */
    @Override
    public ErrorDetails handleGenericException(
            final Exception exception,
            final WebRequest webRequest
    ) {
        final ErrorDetails errorDetails =
                new ErrorDetails
                        (
                                LocalTime.now(),
                                exception.getMessage(),
                                webRequest.getDescription(false)
                        );
        logger.error(String.format("<================ %s >", exception.getMessage()));
        return errorDetails;
    }
}
