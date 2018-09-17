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

package org.sample.servlet;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.ibm.iotf.client.device.DeviceClient;

/**
 * Servlet implementation class SimulateEvent
 */
@WebServlet("/SimulateEvent")
public class SimulateEvent extends HttpServlet {
	/**
	 * @author bkadambi
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SimulateEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			response.setContentType("application/json");
			setAccessControlHeaders(response);
			StringBuilder sb = new StringBuilder();
			String s;
			while ((s = request.getReader().readLine()) != null) {
				sb.append(s);
			}
			Logger.getLogger(getServletName()).log(Level.INFO,
					"Publishing event to Watson IoT platform - " + sb.toString());
			JSONObject req = new JSONObject(sb.toString());
			System.out.println(sb.toString());

			String org = req.getString("org");
			String deviceType = req.getString("deviceType");
			String id = req.getString("deviceId");
			String auth_method = req.getString("authmethod");
			String auth_token = req.getString("authtoken");

			Properties options = new Properties();
			
			options.setProperty("org", org);
			options.setProperty("type", deviceType);
			options.setProperty("id", id);
			options.setProperty("auth-method", auth_method);
			options.setProperty("auth-token", auth_token);
			

			DeviceClient client = new DeviceClient(options);

			JSONObject event = req.getJSONObject("event");
			System.out.println(event);

			client.connect();
			System.out.println("Client connected");

			client.publishEvent("security", event, 1);

			//client.disconnect();

			JSONObject res = new JSONObject();
			res.put("response", "Event publish success! - " + event);
			client.disconnect();
			response.getWriter().append(res.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void setAccessControlHeaders(HttpServletResponse resp) {
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
	}

}
