package ge.ssoft.chat.authentification;

import ge.ssoft.chat.core.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import static ge.ssoft.chat.init.ApplicationConfig.*;

@Service
public class TokenAuthenticationService {

    public static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";
    public static final long TEN_DAYS = 1000 * 60 * 60 * 24 * 10;

    private final TokenHandler tokenHandler;

    @Autowired
    public TokenAuthenticationService(@Value("${token.secret}") String secret) {
        secret=  getConfig("token.secret");
        tokenHandler = new TokenHandler(DatatypeConverter.parseBase64Binary(secret));
    }

    public void addAuthentication(HttpServletResponse response, UserAuthentication authentication) {
        final Users user = authentication.getDetails();
        user.setExpires(System.currentTimeMillis() + TEN_DAYS);
        String token = tokenHandler.createTokenForUser(user);

//		response.addHeader(AUTH_HEADER_NAME, token);
        Cookie c = new Cookie(AUTH_HEADER_NAME, token);
        c.setPath("/");
        response.addCookie(c);


    }

    public Authentication getAuthentication(HttpServletRequest request) {
//		final String token = request.getHeader(AUTH_HEADER_NAME);
        String token = null;
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(AUTH_HEADER_NAME)) {
                token = cookie.getValue();
            }
        }
        if (token != null) {
            final Users user = tokenHandler.parseUserFromToken(token);
            if (user != null) {
                return new UserAuthentication(user);
            }
        }
        return null;
    }

    public Users gerUserByToken(String token) {
        return tokenHandler.parseUserFromToken(token);


    }
}
