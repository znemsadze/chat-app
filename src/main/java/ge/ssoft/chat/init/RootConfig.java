package ge.ssoft.chat.init;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;

/**
 * Created by zviad on 2/19/16.1
 */
@Configuration



@ComponentScan(basePackages = {"ge"}
,excludeFilters = {
        @ComponentScan.Filter(value = Controller.class, type = FilterType.ANNOTATION),
        @ComponentScan.Filter(value = Configuration.class, type = FilterType.ANNOTATION)
}
)
@EnableJpaRepositories(basePackages = "ge.ssoft.chat.core.repositories")
public class RootConfig {


}

