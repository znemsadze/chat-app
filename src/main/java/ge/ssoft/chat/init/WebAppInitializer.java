package ge.ssoft.chat.init;

import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.*;
import java.nio.charset.StandardCharsets;
import java.util.EnumSet;

/**
 *
 */
@Order(value = 1)
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer   {

	private static final String DISPATCHER_SERVLET_NAME = "spring-mvc";
	private static final String DISPATCHER_SERVLET_MAPPING = "/rest/*";

//	@Override
//	public void onStartup(ServletContext servletContext) throws ServletException {
//
//		CharacterEncodingFilter encoder = new CharacterEncodingFilter();
//		encoder.setEncoding("UTF-8");
//		encoder.setForceEncoding(true);
//		EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);
//
//		FilterRegistration.Dynamic characterEncoding = servletContext.addFilter("characterEncoding", encoder);
//		characterEncoding.addMappingForUrlPatterns(dispatcherTypes, true, "/*");
//
////		FilterRegistration.Dynamic security = servletContext.addFilter("springSecurityFilterChain", new SecurityFilter());
////		security.addMappingForUrlPatterns(dispatcherTypes, true, "/*");
//
//		AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
//		appContext.register(RootConfig.class);
//		ServletRegistration.Dynamic springMvc = servletContext.addServlet(DISPATCHER_SERVLET_NAME, new DispatcherServlet(appContext));
//		springMvc.setLoadOnStartup(1);
//		springMvc.addMapping(DISPATCHER_SERVLET_MAPPING);
//
//		servletContext.addListener(new ContextLoaderListener(appContext));
//	}

	@Override
	protected void customizeRegistration(ServletRegistration.Dynamic registration) {

		CharacterEncodingFilter encoder = new CharacterEncodingFilter();
		encoder.setEncoding("UTF-8");
		encoder.setForceEncoding(true);
		EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);


		registration.setInitParameter("dispatchOptionsRequest", "true");


		registration.setAsyncSupported(true);
	}


	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class< ?>[] { RootConfig.class, WebSocketConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class< ?>[] { WebMvcConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding(StandardCharsets.UTF_8.name());
		return new Filter[] { characterEncodingFilter };
	}
}