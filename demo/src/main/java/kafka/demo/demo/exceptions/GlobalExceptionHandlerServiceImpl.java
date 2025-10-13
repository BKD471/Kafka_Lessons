package kafka.demo.demo.exceptions;

import kafka.demo.demo.dto.ErrorDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandlerServiceImpl implements IGlobalExceptionHandlerService {

    private static final Logger logger =
            LoggerFactory.getLogger(GlobalExceptionHandlerServiceImpl.class);

    

    @Override
    public Map<String, String> handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException exception
    ) {
        final Map<String, String> errorDetailsMap = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            final String fieldName = ((FieldError) error).getField();
            final String errorMessage = error.getDefaultMessage();
            errorDetailsMap.put(fieldName, errorMessage);
        });
        logger.error(String.format("<================ %s >", exception.getMessage()));
        return errorDetailsMap;
    }

    

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
