/*
package com.rew3.common.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.rew3.user.UserQueryHandler;
import com.rew3.user.model.User;
import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class APISecurityInterceptor implements Interceptor {

	private static final long serialVersionUID = 1L;

	public String intercept(ActionInvocation invocation) throws Exception {

		System.out.println("INTERCEPTED");
		HttpServletRequest request = ServletActionContext.getRequest();

		String externalId = request.getHeader("REW3-UserId");
		request.setAttribute("userId", 1);
		System.out.print("REW3-UserId: ");
		System.out.println(externalId);
		*/
/*
		User u = (new UserQueryHandler()).getByExternalId(externalId);

		if (externalId == null || u == null) {
			return "InvalidUser";
		}

		request.setAttribute("user", u);*//*








	*/
/*	String request = "http://104.236.30.5:9999/security/v1/login";
		URL url = new URL(request);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setInstanceFollowRedirects(false);
		conn.setRequestMethod("POST");
		// conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		// conn.setUseCaches(false);
		conn.setRequestProperty("Content-Type", "application/json");

		String urlParameters = "grant_type=password & username=admin & password=admin & client_id=MyOauth2Client & client_secret=ZMwBETlfkvZTB1MbOx3DZwQ^UCN]MN";


		String postJsonData = "{\"grant_type\":\"password\",\"username\":\"admin\",\"password\":\"admin\",\"client_id\":\"MyOauth2Client\",\"client_secret\":\"ZMwBETlfkvZTB1MbOx3DZwQ^UCN]MN\"}";

		// Send post request
		conn.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.writeBytes(postJsonData);
		wr.flush();
		wr.close();

		int code = conn.getResponseCode();
		System.out.println(code);


		BufferedReader in = new BufferedReader(
				new InputStreamReader(conn.getInputStream()));
		String output;
		StringBuffer response = new StringBuffer();

		while ((output = in.readLine()) != null) {
			response.append(output);
		}
		in.close();

		System.out.println(response.toString());

*//*


		return invocation.invoke();
	}

	public boolean validateRequest(HttpServletRequest request) {
		boolean validated = true;
		try {
			String h = request.getHeader("HASH");
			Map<String, Object> mapObject = new HashMap<String, Object>();
			mapObject.put("request", request);
			JSONObject json = new JSONObject(mapObject);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (h != null && h.equals("KoderLabs")) {
				throw new Exception("Invalid Request Hash");
			}
		} catch (Exception e) {
			e.printStackTrace();
			validated = false;

		}
		return validated;
	}

	public void destroy() {
		System.out.println("Destroying MyLoggingInterceptor...");
	}

	public void init() {
		System.out.println("Initializing MyLoggingInterceptor...");
	}
}*/
