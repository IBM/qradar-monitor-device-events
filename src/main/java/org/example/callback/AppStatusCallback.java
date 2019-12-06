package org.example.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.wiotp.sdk.app.callbacks.StatusCallback;
import com.ibm.wiotp.sdk.app.messages.ApplicationStatus;
import com.ibm.wiotp.sdk.app.messages.DeviceStatus;

public class AppStatusCallback implements StatusCallback {

	private ApplicationStatus appStatus = null;
	private DeviceStatus devStatus = null;
	
	private static final Logger LOG = LoggerFactory.getLogger(AppStatusCallback.class);

	@Override
	public void processApplicationStatus(ApplicationStatus status) {
		this.appStatus = status;
	}

	@Override
	public void processDeviceStatus(DeviceStatus status) {
		this.devStatus = status;
		LOG.info("Device status: " +status);
	}
	
	public ApplicationStatus getAppStatus() { return this.appStatus; }
	public DeviceStatus getDeviceStatus() { return this.devStatus; }
	

	public void clear() {
		appStatus = null;
		devStatus = null;
	}

}
