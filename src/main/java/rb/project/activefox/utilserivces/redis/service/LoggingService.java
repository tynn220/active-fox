package rb.project.activefox.utilserivces.redis.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;

public interface LoggingService {
    void logRequest(HttpServletRequest request, Object body);
    void logResponse(HttpServletRequest request, HttpServletResponse response, Method method, Object body);
}
