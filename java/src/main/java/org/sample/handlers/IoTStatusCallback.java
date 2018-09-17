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

package org.sample.handlers;

import com.ibm.iotf.client.app.ApplicationStatus;
import com.ibm.iotf.client.app.DeviceStatus;
import com.ibm.iotf.client.app.StatusCallback;

public class IoTStatusCallback implements StatusCallback {
	/**
	 * @author bkadambi
	 * 
	 */

	@Override
	public void processApplicationStatus(ApplicationStatus status) {
		System.out.println("Application Status = " + status.getPayload());

	}

	@Override
	public void processDeviceStatus(DeviceStatus status) {
		if (status.getAction() == "Disconnect") {
			System.out.println("device: " + status.getDeviceId() + "  time: " + status.getTime() + "  action: "
					+ status.getAction() + "  reason: " + status.getReason());
		} else {
			System.out.println("device: " + status.getDeviceId() + "  time: " + status.getTime() + "  action: "
					+ status.getAction());
		}
	}

}
