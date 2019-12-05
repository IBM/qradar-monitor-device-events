package org.example.config;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;



public class IoTConfig {

	public static String apikey = "";

	public static String token = "";

	public static String org = "";

	public static Properties iotprops = new Properties();

	public static void buildIoTAppProps() {
		Logger.getLogger(IoTConfig.class.getName()).log(Level.INFO, "Building IoT app properties");
		iotprops.put("id", "sampleapp");
		iotprops.put("org", org);
		iotprops.put("Authentication-Method", "apikey");
		iotprops.put("API-Key", apikey);
		iotprops.put("Authentication-Token", token);
	}
}
