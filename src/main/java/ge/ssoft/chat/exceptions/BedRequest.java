package ge.ssoft.chat.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by zviad on 3/21/17.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "Bed Request")
public class BedRequest extends RuntimeException {
    public BedRequest() {
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

    public BedRequest(String s) {
        this.message=s;
        this.statusText=s;

    }

    public BedRequest(String s, Throwable throwable) {
        super(s, throwable);
    }

    public BedRequest(Throwable throwable) {
        super(throwable);
    }

    public BedRequest(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
