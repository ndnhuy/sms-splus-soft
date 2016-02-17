package vn.com.splussoftware.sms.ui.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedClientException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.splussoftware.sms.utils.constant.AuthenticationConstant;

public class OAuthTokenHelper {
	private static final Logger logger = LoggerFactory.getLogger(OAuthTokenHelper.class);
	
	//TODO need refactor
	public static Map getValidAccessToken(String username, String password) throws JsonParseException, JsonMappingException, IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		headers.set("Authorization", "Basic c21zX3dlYjoxMjM=");
		
		
		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.add("username", username);
		form.add("password", password);
		form.add("scope", "read write");
		form.add("grant_type", "password");
		form.add("client_id", "sms_web");
		
		HttpEntity<?> entity = new HttpEntity<>(form, headers);
		
		
		RestTemplate restTemplate = new RestTemplate();
		
		restTemplate.setErrorHandler(new ResponseErrorHandler() {

			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				return new DefaultResponseErrorHandler().hasError(response);
			}

			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
				
				String responseBody = IOUtils.toString(response.getBody());
				logger.error("Authorization problem: " + responseBody);
				
				throw new UnauthorizedClientException("Wrong username or password");
			}
			
		});
		
		HttpEntity<String> response = restTemplate.exchange(AuthenticationConstant.SERVER_URL + "/oauth/token", HttpMethod.POST, entity, String.class);
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		map = mapper.readValue(response.getBody(), new TypeReference<Map<String, String>>() {});
		
		return map;
	}
	
	public static Map getValidAccessTokenFromGivenRefreshToken(String refreshToken, ResponseErrorHandler responseHandlerError) throws JsonParseException, JsonMappingException, IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		headers.set("Authorization", "Basic c21zX3dlYjoxMjM=");
		
		
		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();

		form.add("grant_type", "refresh_token");
		form.add("refresh_token", refreshToken);
		
		HttpEntity<?> entity = new HttpEntity<>(form, headers);
		
		
		RestTemplate restTemplate = new RestTemplate();
		
		restTemplate.setErrorHandler(responseHandlerError);
		
		HttpEntity<String> response = restTemplate.exchange(AuthenticationConstant.SERVER_URL + "/oauth/token", HttpMethod.POST, entity, String.class);
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		map = mapper.readValue(response.getBody(), new TypeReference<Map<String, String>>() {});
		
		return map;
	}
}
