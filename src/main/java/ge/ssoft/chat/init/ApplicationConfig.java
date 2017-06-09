package ge.ssoft.chat.init;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;


public class ApplicationConfig {

	private static final String APP_PROPERTIES_FILE = "application.properties";
	private static final String LOCAL_PROPERTIES_FILE = "local.properties";
	private static final Properties PROPERTIES = new Properties();
	private static final Boolean isDevMode;
	private static Boolean isLocal = false;
	private static final String[] SYSTEM_PROPERTY_NAMES = {"user.dir", "catalina.home", "user.home"};
	private static final Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);



	static {
		loadApplicationProperties();
		logProperties();
		isDevMode = Boolean.valueOf(getConfig("application.isDevMode"));
	}

	public static String getConfig(String key) {
		return (PROPERTIES.get(key) != null) ? PROPERTIES.get(key).toString() : null;
	}

	private static void loadApplicationProperties() {
		try {
			InputStream inputStream = ApplicationConfig.class.getClassLoader().getResourceAsStream(APP_PROPERTIES_FILE);
			logger.info("inputStream = " + inputStream);
			PROPERTIES.load(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void loadLocalProperties() {
		try {
			String localPropertiesPath = findLocalConfigFile();
			if (localPropertiesPath != null) {
				logger.info("using local config: " + localPropertiesPath);
				PROPERTIES.load(new FileInputStream(new File(localPropertiesPath)));
				isLocal = true;
			} else {
				logger.warn("local.properties file not found");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean isDevMode() {
		return isDevMode;
	}

	public static boolean isLocal() {
		return isLocal;
	}

	private static String findLocalConfigFile() {
		String filePath = null;
		File f;
		for (String systemProperty : SYSTEM_PROPERTY_NAMES) {
			String systemPropertyPath = System.getProperty(systemProperty);
			String path = systemPropertyPath + "/" + LOCAL_PROPERTIES_FILE;
//			logger.info(path);
			f = new File(path);

			if (f.exists()) {
				filePath = path;
				break;
			}
		}
		return filePath;
	}

	private static void logProperties() {
		logger.info("------------------PROPERTIES---------------------");
		Enumeration e = PROPERTIES.propertyNames();
		logger.info("{");
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			logger.info("\t" + key + " = " + PROPERTIES.getProperty(key));
		}
		logger.info("}");
		logger.info("-------------------------------------------------");
	}
}