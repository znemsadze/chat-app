package ge.ssoft.chat.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by zviad on 3/21/17.
 */
@ResponseStatus(value = HttpStatus.CONFLICT,reason = "conflict Exception")
public class
SendConflictException extends RuntimeException {
    public SendConflictException() {
    }

    private String message;

    private String statusText;

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SendConflictException(String s) {
        this.message=s;
        this.statusText=s;

    }

    public SendConflictException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public SendConflictException(Throwable throwable) {
        super(throwable);
    }

    public SendConflictException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
