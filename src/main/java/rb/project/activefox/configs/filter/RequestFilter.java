package rb.project.activefox.configs.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import rb.project.activefox.utils.FormatStringUtil;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Component
@Slf4j
public class RequestFilter extends OncePerRequestFilter {
    private final String MDC_TRACE_ID = "traceId";

    private final Set<String> skipUrls = new HashSet<>(List.of(
            "/api/v1/v3/api-docs/**", "/api/v1/swagger-ui/**", "/api/v1/docs")); // api swagger

    @Autowired
    private AntPathMatcher antPathMatcher;


    @Qualifier("handlerExceptionResolver")
    @Autowired
    private HandlerExceptionResolver resolver;

    @Override
    @SneakyThrows
    protected boolean shouldNotFilter(HttpServletRequest request) {

        return skipUrls.stream().anyMatch(p -> antPathMatcher.match(p, request.getRequestURI()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            setMdcTraceId();

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            //Pass exception to exception handler
            resolver.resolveException(request, response, null, e);
        } finally {
            MDC.remove(MDC_TRACE_ID);
        }

    }

    private void setMdcTraceId() {
        String key = FormatStringUtil.genreateString(10, true, true);
        MDC.put(MDC_TRACE_ID, key);
    }


}
