package ge.ssoft.chat.init;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by zviad on 2/19/16.1
 */
@Configuration
@ComponentScan(basePackages = {"ge"})
//@EntityScan(basePackages = "ge.ssoft.chat")
//@EnableJpaRepositories(basePackages = "ge.ssoft.chat.repositoris")
public class RootConfig {


}

