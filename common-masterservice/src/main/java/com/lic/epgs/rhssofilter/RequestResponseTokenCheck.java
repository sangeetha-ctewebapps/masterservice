package com.lic.epgs.rhssofilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lic.epgs.rhssomodel.ResponseModel;
import com.lic.epgs.rhssomodel.TokenStatus;

public class RequestResponseTokenCheck implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(RequestResponseTokenCheck.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		logger.info("Logging Request  {} : {} : {}", req.getMethod(), req.getRequestURI(),
				req.getHeader("Authorization"));
		/* check of access token active or not */
	final String uri = "https://d1stvrrpgda02.licindia.com:8443/auth/realms/epgs/protocol/openid-connect/token/introspect";
	//	final String uri = "https://d1prvhwelga01.licindia.com:8443/auth/realms/usermgmt_claim_portal/protocol/openid-connect/token/introspect";
//		final String uri = "https://5ffdea48-2447-4e6f-9522-a447dc21ca70.mock.pstmn.io/auth/realms/myrealm/protocol/openid-connect/token/introspect";
		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
		String[] arr = req.getHeader("Authorization").split(" ");
		requestBody.add("token", arr[1]);
		requestBody.add("client_id", "epgssit-v1-1");
		requestBody.add("client_secret", "eeef4aa4-c227-42b9-9a25-96dbc59275d7");	//SIT
	//	requestBody.add("client_secret", "c9a4303f-a82f-4751-a723-a9cf52b85acf");	// UAT
		HttpEntity formEntity = new HttpEntity<MultiValueMap<String, String>>(requestBody, headers);
		TokenStatus responseEntity = rest.postForObject(uri, formEntity, TokenStatus.class);
		if (responseEntity.isActive()) {
			chain.doFilter(request, response);
		} else {
			ResponseModel rm = new ResponseModel();
			rm.setDescription(
					"Access token been Expire, kindly generate accesstoken through refresh token or generate new accesstoken");
			rm.setMessage("Failure");
			rm.setStatus(1);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(rm);
			res.setStatus(HttpStatus.UNAUTHORIZED.value());
			res.addHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
			res.getOutputStream().write(json.getBytes(StandardCharsets.UTF_8));

		}
		logger.info("Logging Response :{}", res.getContentType());

	}

	// other methods

}
