package rb.project.activefox.utilserivces.redis.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rb.project.activefox.utilserivces.annotation.TruncateResponseLog;
import rb.project.activefox.utilserivces.redis.service.LoggingService;


import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Enumeration;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoggingServiceImpl implements LoggingService {
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public void logRequest(HttpServletRequest request, Object body) {
        StringBuilder logRequest = new StringBuilder();
        logRequest.append("REQUEST ");
        logRequest.append("METHOD=[").append(request.getMethod()).append("] ");
        logRequest.append("URL=[").append(request.getRequestURI()).append("] "); //path??
        logRequest.append("Header=[").append(buildLogHeader(request)).append("] ");
        logRequest.append("Params=[").append(buildLogParameter(request)).append("] ");
        logRequest.append("Body=[").append(objectMapper.writeValueAsString(body)).append("]");
        log.info(logRequest.toString());
    }

    @SneakyThrows
    public void logResponse(HttpServletRequest request, HttpServletResponse response, Method method, Object body) {
        StringBuilder logResponse = new StringBuilder();
        logResponse.append("RESPONSE: ");
        logResponse.append("METHOD=[").append(request.getMethod()).append("] ");
        logResponse.append("URL=[").append(request.getRequestURI()).append("] "); //path??
        logResponse.append("Header=[").append(buildLogHeader(response)).append("] ");
        String data = objectMapper.writeValueAsString(body);

        if (method.getDeclaredAnnotation(TruncateResponseLog.class) != null) {
            int length = method.getDeclaredAnnotation(TruncateResponseLog.class).maxLength();
            if (data.length() > length) {
                data = data.substring(0, length);
            }
        }
        logResponse.append(data);
        log.info(logResponse.toString());

    }
    public String buildLogHeader(HttpServletRequest request) {
        StringBuilder logHeader = new StringBuilder();
        Enumeration<String> header = request.getHeaderNames();

        while (header.hasMoreElements()) {
            logHeader.append(header.nextElement());
        }
        return logHeader.toString();
    }

    public String buildLogHeader(HttpServletResponse response) {
        StringBuilder logHeader = new StringBuilder();
        Collection<String> header = response.getHeaderNames();

        header.forEach(logHeader::append);
        return logHeader.toString();
    }

    public String buildLogParameter(HttpServletRequest request) {
        StringBuilder logParameter = new StringBuilder();
        Enumeration<String> parameter = request.getParameterNames();
        while (parameter.hasMoreElements()) {
            logParameter.append(parameter.nextElement());
        }
        return logParameter.toString();

    }


}
