package ge.ssoft.chat.core.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by zviad on 6/14/17.
 * Simple message Entity
 */
@Entity
@Table(name = "message")
@NamedQueries({
        @NamedQuery(name = "Message.getMessageByTimes",query = "select t from Message t where t.messageTime >?1 and t.messageTime<?2 ")
})
public class Message implements Serializable {

    @Id
    @Column(name = "id")
    private Integer  id;

    @Basic
    @Column(name = "message")
    private String message;

    @Transient
    private String xAuthToken;

    @Basic
    @Column(name ="username")
    private String username;

    @Basic
    @Column(name = "user_id")
    private Integer userId;

    @Basic
    @Column(name = "message_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date messageTime;

    public Message() {
    }

    public Message(int id, String message,String username) {
        this.id = id;
        this.message = message;
        this.username=username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getxAuthToken() {
        return xAuthToken;
    }

    public void setxAuthToken(String xAuthToken) {
        this.xAuthToken = xAuthToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Date messageTime) {
        this.messageTime = messageTime;
    }

    @Override
    public String toString() {
        return "{"+message+","+id+","+username+"}";
    }

}
