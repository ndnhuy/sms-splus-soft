package vn.com.splussoftware.sms.config;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class CustomHttpServletRequest extends HttpServletRequestWrapper {

	private Map customHeaderMap = null;
	
	public CustomHttpServletRequest(HttpServletRequest request) {
		super(request);
		customHeaderMap = new HashMap<String, String>();
	}
	
	public void addHeader(String name, String value) {
		customHeaderMap.put(name, value);
	}
	
	@Override
	public String getParameter(String name) {
		String paramValue = super.getParameter(name);
		if (paramValue == null) {
			paramValue = (String) customHeaderMap.get(name);
		}
		
		return paramValue;
	}
	
	@Override
	public Enumeration<String> getHeaders(String name) {
		Enumeration<String> headers = super.getHeaders(name);
		if (!headers.hasMoreElements()) {
			Vector<String> v = new Vector<String>();
			v.add((String) customHeaderMap.get(name));
			headers = v.elements();
		}
		
		return headers;
	}

}
