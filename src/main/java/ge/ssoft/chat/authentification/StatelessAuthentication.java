package ge.ssoft.chat.authentification;
import ge.ssoft.chat.core.model.Users;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.Filter;


@Configuration
@ComponentScan
public class StatelessAuthentication {

	@Bean
	public InitializingBean insertDefaultUsers() {
		return new InitializingBean() {
//			@Autowired
//			private UserRepository userRepository;

			@Override
			public void afterPropertiesSet() {
				addUser("admin", "admin");
				addUser("user", "user");
			}

			private void addUser(String username, String password) {
				Users user = new Users();
				user.setUsername(username);
				user.setPassword(new BCryptPasswordEncoder().encode(password));
				user.grantRole(username.equals("admin") ? UserRole.ADMIN : UserRole.USER);
				System.out.println("user persisted=========================");
//				userRepository.save(user);
			}
		};
	}

	@Bean
	public Filter characterEncodingFilter() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return characterEncodingFilter;
	}
}
