/**
 * 
 */
package wipo.patinformed.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author jyoti.dewani
 *
 */
public final class LoadProperties {

	private static LoadProperties instance = null;
	static Properties prop = null;
	static InputStream in = null;
	public static String tmLocation = null;

	// private constructor so no other class can instanciate this
	private LoadProperties() {

		try {
			in = this.getClass().getResourceAsStream("/config.properties");
			prop = new Properties();
			prop.load(in);
		}

		catch (Exception e) {
			System.out.println(e);
		}
	}

	// provide instance if no other instance exist
	public static LoadProperties getInstance() throws IOException {
		if (instance == null) {
			instance = new LoadProperties();
		}
		return instance;
	}

	public String getProperty(String key) {
		return prop.getProperty(key);
	}

}
