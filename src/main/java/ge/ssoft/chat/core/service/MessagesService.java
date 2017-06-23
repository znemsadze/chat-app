package ge.ssoft.chat.core.service;

import ge.ssoft.chat.core.model.Message;

import java.util.Date;
import java.util.List;

/**
 * Created by zviad on 6/23/17.
 *
 */
public interface MessagesService {


      List<Message> getMessageByTimes(Date stareDate,Date endDate);


     Message saveMessage(Message message);

}
