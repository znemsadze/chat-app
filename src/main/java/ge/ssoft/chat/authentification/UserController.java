package ge.ssoft.chat.authentification;
import ge.ssoft.chat.core.model.Roles;
import ge.ssoft.chat.core.model.UserAuthority;
import ge.ssoft.chat.core.model.Users;
import ge.ssoft.chat.core.repositories.RolesRepo;
import ge.ssoft.chat.core.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping(value = "rest")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RolesRepo rolesRepo;



    @RequestMapping(value = "api/users/current", method = RequestMethod.GET)
    public Users getCurrent() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof UserAuthentication) {
            return ((UserAuthentication) authentication).getDetails();
        }
        return new Users(authentication.getName()); //anonymous user support
    }


    @RequestMapping(value = "api/logout", method = RequestMethod.POST)
    public Users logOut(HttpServletRequest request, HttpServletResponse response) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return new Users(authentication.getName());
        }

        Cookie coo = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(TokenAuthenticationService.AUTH_HEADER_NAME)) {
                coo = cookie;
            }
        }
        if (coo != null) {
            coo.setMaxAge(0);
            Cookie empty = new Cookie(TokenAuthenticationService.AUTH_HEADER_NAME, "");
            empty.setPath("/");
            empty.setMaxAge(100);
            response.addCookie(empty);
        }
        SecurityContextHolder.clearContext();
        return new Users("anonymousUser"); //anonymous user support
    }

    @RequestMapping(value = "/api/users/current/{fakeId}", method = RequestMethod.GET)
    public Users getCurrentFakeId(@PathVariable String fakeId) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UserAuthentication) {
            return ((UserAuthentication) authentication).getDetails();
        }
        return new Users(authentication.getName()); //anonymous user support
    }


    @RequestMapping(value = "admin/api/users/byid/{userId}", method = RequestMethod.GET)
    public Users getUserbyId(@PathVariable String userId) {
        return userRepository.findOne(Integer.parseInt(userId));
    }


    @RequestMapping(value = "api/users/current", method = RequestMethod.PATCH)
    public ResponseEntity<String> changePassword(@RequestBody final Users user) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Users currentUser = userRepository.findByUsername(authentication.getName());

        if (user.getNewPassword() == null || user.getNewPassword().length() < 4) {
            return new ResponseEntity<String>("new password to short", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        final BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
        if (!pwEncoder.matches(user.getPassword(), currentUser.getPassword())) {
            return new ResponseEntity<String>("old password mismatch", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        currentUser.setPassword(pwEncoder.encode(user.getNewPassword()));
        userRepository.saveAndFlush(currentUser);
        return new ResponseEntity<String>("password changed", HttpStatus.OK);
    }

    @RequestMapping(value = "admin/api/users/{user}/grant/role/{role}", method = RequestMethod.POST)
    public ResponseEntity<String> grantRole(@PathVariable Users user, @PathVariable UserRole role) {
        if (user == null) {
            return new ResponseEntity<String>("invalid user id", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        user.grantRole(role);
        userRepository.saveAndFlush(user);
        return new ResponseEntity<String>("role granted", HttpStatus.OK);
    }

    @RequestMapping(value = "admin/api/users/{user}/revoke/role/{role}", method = RequestMethod.POST)
    public ResponseEntity<String> revokeRole(@PathVariable Users user, @PathVariable UserRole role) {
        if (user == null) {
            return new ResponseEntity<String>("invalid user id", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        user.revokeRole(role);
        userRepository.saveAndFlush(user);
        return new ResponseEntity<String>("role revoked", HttpStatus.OK);
    }

    @RequestMapping(value = "admin/api/users", method = RequestMethod.GET)
    public List<Users> list() {
        return userRepository.findAll();
    }




    @RequestMapping(value = "admin/api/users/byid", method = RequestMethod.POST)
    public Users saveUser(@RequestBody Users user) {
        if (user.getUserId() != null) {
            Users currentUser = userRepository.findOne(user.getUserId());
            currentUser.setFirstName(user.getFirstName());
            currentUser.setLastName(user.getLastName());
            currentUser.setOccupation(user.getOccupation());
            currentUser.setSubtituteUserId(user.getSubtituteUserId());
            currentUser.getAuthorities().removeAll(currentUser.getAuthorities());
            if (user.getRoleId() != null) {
                Roles roles = rolesRepo.findOne(user.getRoleId().intValue());
                UserAuthority userAuthority = new UserAuthority();
                userAuthority.setUsersByUserId(currentUser);
                userAuthority.setRoleId(roles.getId());
                userAuthority.setAuthority(roles.getCode());
                currentUser.getAuthorities().add(userAuthority);
            }

            if (user.getPassword() == null) {

            } else {
                currentUser.setPassword(new BCryptPasswordEncoder().
                        encode(user.getPassword()));
            }
            userRepository.saveAndFlush(currentUser);

            return currentUser;
        } else {

            if (user.getPassword() == null) {
                user.setPassword(new BCryptPasswordEncoder().encode("123456"));
            } else {
                user.setPassword(new BCryptPasswordEncoder().
                        encode(user.getPassword()));
            }
            if (user.getRoleId() != null) {
                Roles roles = rolesRepo.findOne(user.getRoleId().intValue());
                user.setAuthorities(new HashSet<UserAuthority>());
                UserAuthority userAuthority = new UserAuthority();
                userAuthority.setUsersByUserId(user);
                userAuthority.setRoleId(roles.getId());
                userAuthority.setAuthority(roles.getCode());
                user.getAuthorities().add(userAuthority);

            }

            userRepository.save(user);

            return user;
        }
    }


}
