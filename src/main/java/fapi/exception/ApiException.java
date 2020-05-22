package fapi.exception;

import lombok.Getter;
import lombok.Setter;

import org.springframework.http.HttpStatus;
import java.time.ZonedDateTime;

@Getter
@Setter
public class ApiException {
    private final String message;
    private final HttpStatus status;
    private final ZonedDateTime timestamp;

    public ApiException(String message,
                        HttpStatus status,
                        ZonedDateTime timestamp) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
    }
}
