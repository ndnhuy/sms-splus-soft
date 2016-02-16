package vn.com.splussoftware.sms.config.security;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

import vn.com.splussoftware.sms.config.CustomHttpServletRequest;
import vn.com.splussoftware.sms.config.OAuth2UnauthorizedExceptionHandler;
import vn.com.splussoftware.sms.ui.common.OAuthTokenHelper;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true, securedEnabled=true)
public class OAuth2ServerConfiguration {

	private static final String RESOURCE_ID = "restservice";
	
	
	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfiguration extends
			ResourceServerConfigurerAdapter {

		private CustomAuthenticatorInvalidTokenFilter invalidTokenFilter = new CustomAuthenticatorInvalidTokenFilter();
		
		@Override
		public void configure(ResourceServerSecurityConfigurer resources) {
			// @formatter:off
			resources
				.resourceId(RESOURCE_ID)
				.authenticationManager(invalidTokenFilter);
			// @formatter:on
		}

		
		@Override
		public void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				.authorizeRequests()
					.antMatchers("/login", "/logout").permitAll()
					.antMatchers(HttpMethod.PUT, "/secure/**").authenticated()
					.antMatchers(HttpMethod.PATCH, "/secure/**").authenticated()
					.antMatchers(HttpMethod.DELETE, "/secure/**").authenticated()
					.antMatchers(HttpMethod.POST, "/secure/**").authenticated()
					.antMatchers(HttpMethod.GET, "/secure/**").authenticated()
					.anyRequest().permitAll()
				.and()
					.exceptionHandling()
						.authenticationEntryPoint(new OAuth2UnauthorizedExceptionHandler())
				.and()
					.addFilterAfter(invalidTokenFilter, AbstractPreAuthenticatedProcessingFilter.class)
					.formLogin().disable().logout().disable();
					
			// @formatter:on
		}
		
		

	}

	@Configuration
	@EnableAuthorizationServer
	protected static class AuthorizationServerConfiguration extends
			AuthorizationServerConfigurerAdapter {

		private TokenStore tokenStore = new InMemoryTokenStore();

		@Autowired
		@Qualifier("authenticationManagerBean")
		private AuthenticationManager authenticationManager;

		@Autowired
		private CustomUserDetailsService userDetailsService;
		
		@Autowired
		private OAuth2Interceptor oauth2Interceptor;

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints)
				throws Exception {
			endpoints
				.tokenStore(this.tokenStore)
				.authenticationManager(this.authenticationManager)
				.userDetailsService(userDetailsService)
				.addInterceptor(oauth2Interceptor);
		}

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			// @formatter:off
			clients
				.inMemory()
					.withClient("sms_web")
						.authorizedGrantTypes("authorization_code", "password", "refresh_token")
						.accessTokenValiditySeconds(86400)
						.authorities("USER")
						.scopes("read", "write")
						.resourceIds(RESOURCE_ID)
						.secret("123");
			// @formatter:on
		}
		
		


		@Bean
		@Primary
		public DefaultTokenServices tokenServices() {
			DefaultTokenServices tokenServices = new DefaultTokenServices();
			tokenServices.setSupportRefreshToken(true);
			tokenServices.setTokenStore(this.tokenStore);
			return tokenServices;
		}
	}
	
	protected static class CustomAuthenticatorInvalidTokenFilter extends OAuth2AuthenticationManager implements Filter {

		private static Logger logger = LoggerFactory.getLogger(CustomAuthenticatorInvalidTokenFilter.class);

		@Override
		public Authentication authenticate(Authentication authentication) throws AuthenticationException {
			try {
				return super.authenticate(authentication);
			}
			catch (Exception e) {
				return new CustomAuthentication(authentication.getPrincipal(), authentication.getCredentials());
			}
		}

		@Override
		public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
				throws ServletException, IOException {
			
			Enumeration<String> headers = ((HttpServletRequest)request).getHeaders("Accept");
			while (headers.hasMoreElements()) {
				String value = headers.nextElement();
				if (value.contains("text/html")) {
					Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
					if (authentication instanceof CustomAuthentication) {
						// Handle invalid token here
						HttpServletRequest httpRequest = (HttpServletRequest) request;
						HttpServletResponse httpResponse = (HttpServletResponse) response;
						Cookie[] cookies = httpRequest.getCookies();
						for (Cookie cookie : cookies) {
							if (cookie.getName().equals("refresh_token")) {
								// Get refresh_token from cookie
									// IF there's no refresh_token THEN redirect to login page.
								// Use this refresh_token to get valid access-token from oauth/token
								Map map = OAuthTokenHelper.getValidAccessTokenFromGivenRefreshToken(cookie.getValue(), new ResponseErrorHandler() {

									@Override
									public boolean hasError(ClientHttpResponse response) throws IOException {
										return new DefaultResponseErrorHandler().hasError(response);
									}

									@Override
									public void handleError(ClientHttpResponse response) throws IOException {
										Cookie[] cookies = httpRequest.getCookies();
										for (Cookie cookie : cookies) {
											cookie.setMaxAge(0);
											httpResponse.addCookie(cookie);
										}
										httpResponse.sendRedirect("http://localhost:8182/login");
									}
									
								});
									// IF the refresh_token is invalid THEN throw UnauthorizedClientException 
									// and redirect to login page
								// Update old access-token with the new one
								CustomHttpServletRequest customRequest = new CustomHttpServletRequest(httpRequest);
								customRequest.addHeader("access_token", (String)map.get("access_token"));
								customRequest.addHeader("refresh_token", (String)map.get("refresh_token"));
								
								//TODO Refresh-Token function
								String url = customRequest.getRequestURL().toString();
								String queryString = customRequest.getQueryString();
								
								String completeUrl = url + queryString == null? "" : queryString;
								
								
								httpResponse.addCookie(new Cookie("access_token", (String)map.get("access_token")));
								httpResponse.addCookie(new Cookie("refresh_token", (String)map.get("refresh_token")));
								httpResponse.sendRedirect(url);
								
								//filterChain.doFilter(customRequest, response);
								return;
							}
						}
					}
				}
			}
			
			
			
			logger.debug("None found 'refresh_token' in cookies. Redirect to login page.");
			filterChain.doFilter(request, response);
//			((HttpServletResponse)response).sendRedirect("http://localhost:8182/login");
		}

		@Override
		public void destroy() {
		}

		@Override
		public void init(FilterConfig arg0) throws ServletException {
		}

		@SuppressWarnings("serial")
		protected static class CustomAuthentication extends PreAuthenticatedAuthenticationToken {

			public CustomAuthentication(Object principal, Object credentials) {
				super(principal, credentials);
			}

		}

	}
}
