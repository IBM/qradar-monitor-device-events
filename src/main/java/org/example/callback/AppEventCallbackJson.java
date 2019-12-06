package org.example.callback;



import java.io.IOException;
import java.util.ArrayList;

import org.example.config.QRadarConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudbees.syslog.Facility;
import com.cloudbees.syslog.MessageFormat;
import com.cloudbees.syslog.Severity;
import com.cloudbees.syslog.sender.UdpSyslogMessageSender;
import com.google.gson.JsonObject;
import com.ibm.wiotp.sdk.app.callbacks.EventCallback;
import com.ibm.wiotp.sdk.app.messages.Event;

public class AppEventCallbackJson implements EventCallback<JsonObject> {
	
	private static final Logger LOG = LoggerFactory.getLogger(AppEventCallbackJson.class);

	Event<JsonObject> event = null;
	ArrayList<Event<JsonObject>> allEvents = new ArrayList<Event<JsonObject>>();
	
	@Override
	public void processEvent(Event<JsonObject> evt) {
		event = evt;
		allEvents.add(event);
		LOG.info("Received event, name = "+evt.getEventId() + ", format = " + evt.getFormat() + ", Payload = "+evt.getData().toString());
		
		String eventFormat = QRadarConfig.MSG_FORMAT;
		if (eventFormat.equals(MessageFormat.RFC_3164.toString()) || eventFormat.equals(MessageFormat.RFC_5424.toString()))
		{
			UdpSyslogMessageSender messageSender = new UdpSyslogMessageSender();
			messageSender.setDefaultMessageHostname("myhostname"); // some syslog cloud services may use this field to transmit a secret key
			messageSender.setDefaultAppName("myapp");
			messageSender.setDefaultFacility(Facility.USER);
			messageSender.setDefaultSeverity(Severity.INFORMATIONAL);
			messageSender.setSyslogServerHostname(QRadarConfig.IP_ADDRESS);
			messageSender.setSyslogServerPort(514);
			if (eventFormat.equals(MessageFormat.RFC_3164.toString()))
			      messageSender.setMessageFormat(MessageFormat.RFC_3164); // optional, default is RFC 3164

			if (eventFormat.equals(MessageFormat.RFC_5424.toString()))
			      messageSender.setMessageFormat(MessageFormat.RFC_5424); // optional, default is RFC 3164

			// send a Syslog message
			try {
				messageSender.sendMessage(evt.getData().toString());
				LOG.info("Sent event to QRadar.");
				System.out.println("Sent event to QRadar");
			} catch (IOException e) {
	
			// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void clear() {
		event = null;
		allEvents = null;
	}
	
	public Event<JsonObject> getEvent() { return event; }
	public ArrayList<Event<JsonObject>> getAllEvents() { return allEvents; }

	@Override
	public Class<JsonObject> getMessageClass() {
		
     return JsonObject.class;
	}


}
