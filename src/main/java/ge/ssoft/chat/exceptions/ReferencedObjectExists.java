package ge.ssoft.chat.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by zviad on 5/27/17.
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "conflict Exception")
public class ReferencedObjectExists extends RuntimeException {

    private String message;

    private String statusText;

    public ReferencedObjectExists(String s) {
        super(s);
        this.message = s;
        this.statusText = s;
    }

    public ReferencedObjectExists() {
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }
}
