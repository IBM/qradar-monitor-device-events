/****************************************************** 
 *  Copyright 2018 IBM Corporation 
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 *  you may not use this file except in compliance with the License. 
 *  You may obtain a copy of the License at 
 *  http://www.apache.org/licenses/LICENSE-2.0 
 *  Unless required by applicable law or agreed to in writing, software 
 *  distributed under the License is distributed on an "AS IS" BASIS, 
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 *  See the License for the specific language governing permissions and 
 *  limitations under the License.
 */ 

package org.example.handlers;

import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.example.config.QRadarConfig;

import com.cloudbees.syslog.Facility;
import com.cloudbees.syslog.MessageFormat;
import com.cloudbees.syslog.Severity;
import com.cloudbees.syslog.sender.UdpSyslogMessageSender;
import com.ibm.iotf.client.app.Command;
import com.ibm.iotf.client.app.Event;
import com.ibm.iotf.client.app.EventCallback;

public class IoTEventCallback implements EventCallback {
	
	/**
	 * @author bkadambi
	 * 
	 */
	@Override
	public void processCommand(Command arg0) {
	   Logger.getLogger(IoTEventCallback.class.getName()).log(Level.INFO, "Received command:"+arg0.getCommand());
	}

	@Override
	public void processEvent(Event arg0) {
		System.out.println("Received event on the event call back!" +arg0.getPayload());
		Logger.getLogger(IoTEventCallback.class.getName()).log(Level.INFO, "Received event:"+arg0.getEvent());
		
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
				messageSender.sendMessage(arg0.getPayload());
				Logger.getLogger(IoTEventCallback.class.getName()).log(Level.INFO, "Sent event to QRadar.");
				System.out.println("Sent event to QRadar");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
    }
}
