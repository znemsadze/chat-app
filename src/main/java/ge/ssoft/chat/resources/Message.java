package ge.ssoft.chat.resources;

/**
 * Created by zviad on 6/14/17.
 * Simple message pojo
 */
public class Message {

    private int id;
    private String message;
    private String xAuthToken;
    private String username;


    public Message() {
    }

    public Message(int id, String message,String username) {
        this.id = id;
        this.message = message;
        this.username=username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public String toString() {
        return "{"+message+","+id+","+username+"}";
    }

}
