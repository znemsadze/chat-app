package ge.ssoft.chat.core.service.impl;

import ge.ssoft.chat.core.model.Message;
import ge.ssoft.chat.core.repositories.MessagesRepo;
import ge.ssoft.chat.core.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zviad on 6/23/17.
 *
 */
@Service
public class MessageServiceImpl implements MessagesService {

    MessagesRepo messagesRepo;

    @Autowired
    public void setMessagesRepol(MessagesRepo messagesRepol) {
        this.messagesRepo = messagesRepol;
    }

    @Override
    public List<Message> getMessageByTimes(Date startDate, Date endDate) {
        return messagesRepo.getMessageByTimes(startDate,endDate);
    }

    @Override
    public Message saveMessage(Message message) {
        return messagesRepo.save(message);
    }
}
