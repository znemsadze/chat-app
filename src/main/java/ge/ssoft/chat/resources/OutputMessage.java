package ge.ssoft.chat.resources;

import ge.ssoft.chat.core.model.Message;

import java.util.Date;
import static ge.ssoft.chat.utils.myutils.*;

/**
 * Created by zviad on 6/14/17.
 * simple outpiut message
 */
public class OutputMessage extends Message {

    private Date time;

    public OutputMessage(Message message, Date time ) {
        super(message.getId(),message.getMessage(),message.getUsername());
        this.time = time;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "{"+ dateToStrTm(time)+","+super.toString()+"}";
    }
}
