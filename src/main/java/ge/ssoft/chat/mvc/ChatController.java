package ge.ssoft.chat.mvc;

import ge.ssoft.chat.authentification.TokenHandler;
import ge.ssoft.chat.core.model.Users;
import ge.ssoft.chat.resources.Message;
import ge.ssoft.chat.resources.OutputMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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


    @MessageMapping("/chat")
    @SendTo("/topic/message")
    public OutputMessage sendMessage(Message message) {
       Users users= tokenHandler.parseUserFromToken(message.getxAuthToken());
        message.setUsername(users.getFirstName());
        System.out.println(message);
        return new OutputMessage(message, new Date());
    }
}


