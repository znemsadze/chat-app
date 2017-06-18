package ge.ssoft.chat.init;import org.springframework.context.annotation.ComponentScan;import org.springframework.context.annotation.Configuration;import org.springframework.messaging.simp.SimpMessageType;import org.springframework.messaging.simp.config.MessageBrokerRegistry;import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;import org.springframework.web.socket.config.annotation.StompEndpointRegistry;/** * Created by zviad on 6/14/17. * Confiruration class for web sockets */@Configuration@EnableWebSocketMessageBroker@ComponentScan(basePackages = "ge")public class WebSocketConfig  extends AbstractWebSocketMessageBrokerConfigurer{//    @Override//    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {//        messages//                .nullDestMatcher().authenticated()//                .simpSubscribeDestMatchers("/user/queue/errors").permitAll()//                .simpDestMatchers("/app/**").hasRole("USER")//                .simpSubscribeDestMatchers("/user/**", "/topic/friends/*").hasRole("USER")//                .simpTypeMatchers(SimpMessageType.MESSAGE, SimpMessageType.SUBSCRIBE).denyAll()//                .anyMessage().denyAll();////    }    @Override    public void configureMessageBroker(MessageBrokerRegistry config) {        config.enableSimpleBroker("/topic");        config.setApplicationDestinationPrefixes("/app");    }    @Override    public void registerStompEndpoints(StompEndpointRegistry registry) {        registry.addEndpoint("/chat").withSockJS();    }}