package com.rvbb.b2b.rsocket.client.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
public class InOutLogInterceptor implements ClientHttpRequestInterceptor {

    private String type;
    public final static String API_3RD="API_3RD";
    public final static String API_INTERNAL="API_INTERNAL";

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        logResponse(response);
        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) throws IOException {
        if (log.isDebugEnabled()) {
            log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>equest begin>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            log.debug("URI         : {}", request.getURI());
            log.debug("Method      : {}", request.getMethod());
            log.debug("Headers     : {}", request.getHeaders());
            log.debug("Request_Body: {}", new String(body, StandardCharsets.UTF_8));
            log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>request end>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        if (log.isDebugEnabled()) {
            log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<esponse begin<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            log.debug("Status_Code  : {}", response.getStatusCode());
            log.debug("Status_Text  : {}", response.getStatusText());
            log.debug("Headers      : {}", response.getHeaders());
            log.debug("Response_Body: {}", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
            log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<response end<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        }
    }
}