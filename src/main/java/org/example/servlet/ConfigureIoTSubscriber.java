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

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.example.config.IoTConfig;
import org.json.JSONObject;

/**
 * Servlet implementation class ConfigureIoTSubscriber
 */
@WebServlet("/ConfigureIoTSubscriber")
public class ConfigureIoTSubscriber extends HttpServlet {
	/**
	 * @author bkadambi
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ConfigureIoTSubscriber() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		setAccessControlHeaders(response);
		try {
			StringBuilder sb = new StringBuilder();
			String s;
			while ((s = request.getReader().readLine()) != null) {
				sb.append(s);
			}
			Logger.getLogger(getServletName()).log(Level.INFO,"Received configuration - " + sb.toString());
			JSONObject req = new JSONObject(sb.toString());
			// {"apikey":"","apitoken":"","org":""}
			String apikey = req.getString("apikey");
			String token = req.getString("apitoken");
			String org = req.getString("org");
			IoTConfig.apikey = apikey;
			IoTConfig.token = token;
			IoTConfig.org = org;
			IoTConfig.buildIoTAppProps();
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject res = new JSONObject();
		Logger.getLogger(getServletName()).log(Level.INFO,"Applied IoT configuration.");
		res.put("response", "Applied IoT Configuration Successfully");
		response.getWriter().append(res.toString());
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
