package com.github.iweinzierl.moviedatabase.backend.config.logging;

import com.github.isrsal.logging.RequestWrapper;
import com.github.isrsal.logging.ResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class LoggingFilter extends OncePerRequestFilter {

    protected static final Logger LOG = LoggerFactory.getLogger(LoggingFilter.class);

    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String SPACES = "    ";

    private Map<Long, Long> requestTimes = new ConcurrentHashMap<>();
    private AtomicLong id = new AtomicLong(1);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (LOG.isDebugEnabled()) {
            long requestId = id.incrementAndGet();
            requestTimes.put(requestId, System.currentTimeMillis());
            request = new RequestWrapper(requestId, request);
            response = new ResponseWrapper(requestId, response);
        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            if (LOG.isDebugEnabled()) {
                logRequest(request);
                logResponse(response);
            }
        }
    }

    private void logRequest(HttpServletRequest req) {
        StringBuilder builder = new StringBuilder();
        builder.append("\n--> ");

        if (req instanceof RequestWrapper) {
            builder.append("ID ").append(((RequestWrapper) req).getId()).append(": ");
        }

        builder.append(req.getMethod()).append(" ").append(req.getRequestURI()).append("\n");

        Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            builder
                    .append(SPACES)
                    .append(headerName)
                    .append(": ")
                    .append(req.getHeader(headerName))
                    .append("\n");
        }

        if (req instanceof RequestWrapper) {
            RequestWrapper reqWrapper = (RequestWrapper) req;
            String charEncoding = req.getCharacterEncoding() != null ? req.getCharacterEncoding() : DEFAULT_CHARSET;

            try {
                byte[] body = reqWrapper.toByteArray();
                builder.append(SPACES).append("Body Length: ").append(body.length).append(" bytes").append("\n");
                builder.append(SPACES).append("Body: ").append(new String(body, charEncoding)).append("\n");
            } catch (UnsupportedEncodingException e) {
            }
        }

        builder.append("--> ");
        builder.append("END ").append(req.getMethod());

        LOG.debug(builder.toString());
    }

    private void logResponse(HttpServletResponse resp) {
        StringBuilder builder = new StringBuilder();
        builder.append("\n<-- ");

        if (resp instanceof ResponseWrapper) {
            ResponseWrapper responseWrapper = (ResponseWrapper) resp;

            long duration = System.currentTimeMillis() - requestTimes.get(responseWrapper.getId());
            requestTimes.remove(responseWrapper.getId());
            builder
                    .append("ID ")
                    .append(responseWrapper.getId())
                    .append(": ")
                    .append(resp.getStatus())
                    .append(" ")
                    .append(HttpStatus.valueOf(resp.getStatus()).getReasonPhrase())
                    .append(" (")
                    .append(duration)
                    .append(" ms")
                    .append(")");
        }

        builder.append("\n");

        Collection<String> headerNames = resp.getHeaderNames();
        for (String headerName : headerNames) {
            builder
                    .append(SPACES)
                    .append(headerName)
                    .append(": ")
                    .append(resp.getHeader(headerName))
                    .append("\n");
        }

        if (resp instanceof ResponseWrapper) {
            ResponseWrapper respWrapper = ((ResponseWrapper) resp);
            String charEncoding = resp.getCharacterEncoding() != null ? resp.getCharacterEncoding() : DEFAULT_CHARSET;

            try {
                byte[] body = respWrapper.toByteArray();
                builder.append(SPACES).append("Body Length: ").append(body.length).append(" bytes").append("\n");
                builder.append(SPACES).append("Body: ").append(new String(body, charEncoding)).append("\n");
            } catch (UnsupportedEncodingException e) {
            }
        }

        builder.append("<-- ");
        builder.append("END ");

        LOG.debug(builder.toString());
    }
}
