package ge.ssoft.chat.mvc;

import ge.ssoft.chat.authentification.TokenHandler;
import ge.ssoft.chat.core.model.Users;
import ge.ssoft.chat.core.model.Message;
import ge.ssoft.chat.core.service.MessagesService;
import ge.ssoft.chat.resources.OutputMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;

import static ge.ssoft.chat.init.ApplicationConfig.getConfig;

/**
 * Created by zviad on 6/14/17.
 */
@Controller
public class ChatController {

    TokenHandler tokenHandler;

    @Autowired
    public  ChatController (@Value("secret") String secret){
          secret= getConfig("token.secret");
        tokenHandler = new TokenHandler(DatatypeConverter.parseBase64Binary(secret));
    }


    MessagesService messagesService;

    @Autowired
    public void setMessagesService(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/message")
    public Message sendMessage(Message message) {
        Users users= tokenHandler.parseUserFromToken(message.getxAuthToken());
        message.setUsername(users.getFirstName());
        message.setMessageTime(new Date());
        message.setUserId(users.getUserId());
        messagesService.saveMessage(message);
        System.out.println(message);
        return message;
    }
}


