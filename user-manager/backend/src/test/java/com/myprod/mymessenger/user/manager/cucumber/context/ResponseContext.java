package com.myprod.mymessenger.user.manager.cucumber.context;

import com.myprod.mymessenger.user.manager.configuration.SecurityConstants;
import com.myprod.mymessenger.user.manager.model.Json;
import com.myprod.mymessenger.user.manager.util.regex.PatternTemplate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ResponseContext {
  private final Logger logger = LoggerFactory.getLogger(ResponseContext.class);

  protected RestTemplate restTemplate = new RestTemplate();

  private boolean hasAuthorizationCookie;

  private String authorizationCookie;

  private ResponseEntity<String> response;

  private RestClientException exception;

  private int statusCode;

  private String body;

  protected void setBodyFromResponse() {
    var body = response.getBody();

    this.body = null == body || body.isEmpty() ? null : body;
  }

  protected void setStatusCodeFromResponse() {
    var responseStatusCode = response.getStatusCode();

    statusCode = responseStatusCode.value();
  }

  protected void setAuthorizationCookieFromResponse() {
    List<String> cookies = response.getHeaders().get(HttpHeaders.SET_COOKIE);

    logger.info("get cookie = " + cookies);

    if (null == cookies) {
      return;
    }

    for (String cookie : cookies) {
      Pattern pattern =
          Pattern.compile(
              SecurityConstants.TOKEN_COOKIE
                  + "=\""
                  + SecurityConstants.TOKEN_PREFIX
                  + PatternTemplate.JWT_TOKEN
                  + "\";");

      logger.info("pattern for found authorization cookie in cookies = " + pattern);

      Matcher matcher = pattern.matcher(cookie);

      var hasAuthorizationCookie = matcher.find();

      logger.info("has authorization cookie in cookie = " + hasAuthorizationCookie);

      authorizationCookie =
          hasAuthorizationCookie ? cookie.substring(matcher.start(), matcher.end()) : null;

      this.hasAuthorizationCookie = hasAuthorizationCookie;

      logger.info("auth token is " + authorizationCookie);
    }
  }

  protected HttpHeaders getJsonHeaders() {
    var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    return headers;
  }

  protected HttpEntity<?> getHttpEntity(String body) {
    return new HttpEntity<>(body, getJsonHeaders());
  }

  protected HttpEntity<?> getHttpEntityWithAuthCookie(String body) {
    var headers = getJsonHeaders();
    headers.add("Cookie", getAuthorizationCookie());

    return new HttpEntity<>(body, headers);
  }

  protected HttpEntity<?> getHttpEntityWithAuthCookie() {
    var headers = getJsonHeaders();
    headers.add("Cookie", getAuthorizationCookie());

    return new HttpEntity<>(null, headers);
  }

  protected void setResponse(ResponseEntity<String> response) {
    this.response = response;

    logger.info("response is " + this.response);

    setAuthorizationCookieFromResponse();
    setStatusCodeFromResponse();
    setBodyFromResponse();
  }

  public String getBody() {
    return body;
  }

  public void exec(String url, HttpMethod httpMethod, HttpEntity<?> httpEntity) {
    try {
      logger.info("request on url - " + url);
      logger.info("request with http method - " + httpMethod);
      logger.info("request with data -  " + httpEntity);

      setResponse(restTemplate.exchange(url, httpMethod, httpEntity, String.class));
    } catch (RestClientResponseException exception) {
      exceptionHandle(exception);
    }
  }

  public void exec(String url, HttpMethod httpMethod) {
    exec(url, httpMethod, null);
  }

  public void exceptionHandle(RestClientResponseException exception) {
    response = null;
    this.exception = exception;
    statusCode = exception.getRawStatusCode();
    var body = exception.getResponseBodyAsString();

    this.body = null == body || body.isEmpty() ? null : body;

    logger.info("exception is ", exception);
  }

  public boolean hasAuthToken() {
    return hasAuthorizationCookie;
  }

  public String getAuthorizationCookie() {
    return authorizationCookie;
  }

  public ResponseEntity<String> getResponse() {
    return response;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public void get(String url) {
    exec(url, HttpMethod.GET);
  }

  public void post(String url, Json json) {
    exec(url, HttpMethod.POST, getHttpEntity(json.getData()));
  }

  public void put(String url, Json json) {
    exec(url, HttpMethod.PUT, getHttpEntity(json.getData()));
  }

  public void patch(String url, Json json) {
    exec(url, HttpMethod.PATCH, getHttpEntity(json.getData()));
  }

  public void delete(String url) {
    exec(url, HttpMethod.DELETE);
  }

  public void getWithAuth(String url) {
    exec(url, HttpMethod.GET, getHttpEntityWithAuthCookie());
  }

  public void postWithAuth(String url, Json json) {
    exec(url, HttpMethod.POST, getHttpEntityWithAuthCookie(json.getData()));
  }

  public void putWithAuth(String url, Json json) {
    exec(url, HttpMethod.PUT, getHttpEntityWithAuthCookie(json.getData()));
  }

  public void patchWithAuth(String url, Json json) {
    exec(url, HttpMethod.PATCH, getHttpEntityWithAuthCookie(json.getData()));
  }

  public void deleteWithAuth(String url) {
    exec(url, HttpMethod.DELETE, getHttpEntityWithAuthCookie());
  }
}
