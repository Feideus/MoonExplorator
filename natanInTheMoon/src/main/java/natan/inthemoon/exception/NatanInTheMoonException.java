package natan.inthemoon.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import natan.inthemoon.enums.ExceptionLabel;
import org.springframework.http.HttpStatus;

/**
 *
 * Custom Exception.
 * Used for handling diffent error cases
 *
 * @Author Erwan Ulrich
 */
@AllArgsConstructor
@Getter
public class NatanInTheMoonException extends RuntimeException{

    private final HttpStatus httpStatus;
    private final ExceptionLabel customLabel;

}
