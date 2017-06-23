package ge.ssoft.chat.mvc;

import ge.ssoft.chat.core.model.Message;
import ge.ssoft.chat.core.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by zviad on 6/9/17.
 * test Controller class
 */
@RestController
@RequestMapping(value = "message/hist")
public class MessageHistController {

    MessagesService messagesService;

    @Autowired
    public void setMessagesService(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Message> getLastHours(@RequestParam(name = "hour") Integer hours) {
        Date endDate= Calendar.getInstance().getTime();
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.HOUR,-hours);
        Date staartDate=cal.getTime();
        return messagesService.getMessageByTimes(staartDate,endDate);
    }


}
