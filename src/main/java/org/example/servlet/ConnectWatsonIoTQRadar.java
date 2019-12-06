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

package org.example.servlet;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.example.callback.AppEventCallbackJson;
import org.example.callback.AppStatusCallback;
import org.example.config.IoTConfig;
import org.example.vo.Config;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator.Feature;
import com.ibm.wiotp.sdk.app.ApplicationClient;
import com.ibm.wiotp.sdk.codecs.JsonCodec;


/**
 * Servlet implementation class ConnectWatsonIoTQRadar
 */
@WebServlet("/ConnectWatsonIoTQRadar")
public class ConnectWatsonIoTQRadar extends HttpServlet {
	/**
	 * @author bkadambi
	 * 
	 */
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConnectWatsonIoTQRadar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setContentType("application/json");
			setAccessControlHeaders(response);
			StringBuilder sb = new StringBuilder();
			String s;
			while ((s = request.getReader().readLine()) != null) {
				sb.append(s);
			}
			Logger.getLogger(getServletName()).log(Level.INFO,"Connecting Watson IoT to QRadar with - " + sb.toString());
			JSONObject req = new JSONObject(sb.toString());
			//{"deviceId":"","deviceType":""}
			String deviceId = req.getString("deviceId");
			String deviceType = req.getString("deviceType");
			
			ObjectMapper mapper = new ObjectMapper(new YAMLFactory().disable(Feature.WRITE_DOC_START_MARKER));
			mapper.findAndRegisterModules();
			Config config = mapper.readValue(new File("appconfig.yaml"), Config.class);
			config.getAuth().setKey(IoTConfig.apikey);
			config.getAuth().setToken(IoTConfig.token);
			
			mapper.writeValue(new File("appconfig.yaml"), config);

			ApplicationClient client = new ApplicationClient("appconfig.yaml");
			client.connect();
			AppEventCallbackJson evtCallback = new AppEventCallbackJson();
			AppStatusCallback statusCallback = new AppStatusCallback();
			client.registerCodec(new JsonCodec());
			client.registerEventCallback(evtCallback);
			client.setStatusCallback(statusCallback);
			
			client.subscribeToDeviceEvents(deviceType, deviceId, "security", "json", 1);
			client.subscribeToDeviceStatus(deviceType, deviceId);

			Logger.getLogger(getServletName()).log(Level.INFO,"Subscribing to device events! " + sb.toString());
			JSONObject res = new JSONObject();
	        res.put("response", "Successfully connected Watson IoT to QRadar!" + sb.toString());
			response.getWriter().append(res.toString());
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void setAccessControlHeaders(HttpServletResponse resp) {
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
	}
}
