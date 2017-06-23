package ge.ssoft.chat.core.repositories;

import ge.ssoft.chat.core.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by zviad on 6/23/17.
 * messages repository
 */
public interface MessagesRepo extends JpaRepository<Message,Integer> {

    List<Message> getMessageByTimes(Date startDate,Date endDate );



}
