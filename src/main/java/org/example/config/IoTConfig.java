package org.example.config;

import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.example.handlers.IoTEventCallback;
import org.example.handlers.IoTStatusCallback;

import com.ibm.iotf.client.app.ApplicationClient;

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

	public static void main(String[] args) {
		apikey = "a-9r60uo-frpscbeqja";
		token = "@k6FZPC3veVQr_5tyN";
		org = "9r60uo";
		buildIoTAppProps();
		try {
			ApplicationClient client = new ApplicationClient(iotprops);
			IoTEventCallback evtBack = new IoTEventCallback();
			client.setEventCallback(evtBack);
			client.setStatusCallback(new IoTStatusCallback());
			client.connect();
			client.subscribeToDeviceEvents("Vehicle", "Truck_7265", "security", "json", 1);
			client.subscribeToDeviceStatus();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
