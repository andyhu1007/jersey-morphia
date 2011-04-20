package com.thoughtworks.consultant.filter;

import com.sun.jersey.api.container.ContainerException;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.core.util.ReaderWriter;
import com.sun.jersey.spi.container.*;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Copy from: http://java.net/projects/jersey/sources/svn/content/trunk/jersey/jersey-server/src/main/java/com/sun/jersey/api/container/filter/LoggingFilter.java?rev=4863
 *
 * Never used, just an try to fix encoding issue Default Jersey LoggingFilter (web.xml).
 */
public class MyLoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    public static final String FEATURE_LOGGING_DISABLE_ENTITY
            = "com.sun.jersey.config.feature.logging.DisableEntitylogging";

    private static final Logger LOGGER = Logger.getLogger(MyLoggingFilter.class.getName());

    private static final String NOTIFICATION_PREFIX = "* ";

    private static final String REQUEST_PREFIX = "> ";

    private static final String RESPONSE_PREFIX = "< ";

    private final Logger logger;

    private
    @Context
    HttpContext hc;

    private
    @Context
    ResourceConfig rc;

    private long id = 0;

    public MyLoggingFilter() {
        this(LOGGER);
    }

    public MyLoggingFilter(Logger logger) {
        this.logger = logger;
    }

    private synchronized void setId() {
        if (hc.getProperties().get("request-id") == null) {
            hc.getProperties().put("request-id", Long.toString(++id));
        }
    }

    private StringBuilder prefixId(StringBuilder b) {
        b.append(hc.getProperties().get("request-id").toString()).
                append(" ");
        return b;
    }

    public ContainerRequest filter(ContainerRequest request) {
        setId();

        final StringBuilder b = new StringBuilder();
        printRequestLine(b, request);
        printRequestHeaders(b, request.getRequestHeaders());

        if (rc.getFeature(FEATURE_LOGGING_DISABLE_ENTITY)) {
            logger.info(b.toString());
            return request;
        } else {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = request.getEntityInputStream();
            try {
                if (in.available() > 0) {
                    ReaderWriter.writeTo(in, out);

                    byte[] requestEntity = out.toByteArray();
                    printEntity(b, requestEntity);

                    request.setEntityInputStream(new ByteArrayInputStream(requestEntity));
                }
                return request;
            } catch (IOException ex) {
                throw new ContainerException(ex);
            } finally {
                logger.info(b.toString());
            }
        }
    }

    private void printRequestLine(StringBuilder b, ContainerRequest request) {
        prefixId(b).append(NOTIFICATION_PREFIX).append("Server in-bound request").append('\n');
        prefixId(b).append(REQUEST_PREFIX).append(request.getMethod()).append(" ").
                append(request.getRequestUri().toASCIIString()).append('\n');
    }

    private void printRequestHeaders(StringBuilder b, MultivaluedMap<String, String> headers) {
        for (Map.Entry<String, List<String>> e : headers.entrySet()) {
            String header = e.getKey();
            for (String value : e.getValue()) {
                prefixId(b).append(REQUEST_PREFIX).append(header).append(": ").
                        append(value).append('\n');
            }
        }
        prefixId(b).append(REQUEST_PREFIX).append('\n');
    }

    private void printEntity(StringBuilder b, byte[] entity) throws IOException {
        if (entity.length == 0)
            return;
        // new String(byte[]) use Charset.defaultCharset() as encoding charset, which is JVM's default charset.
        // We can specify a different charset if necessary.
        b.append(new String(entity)).append("\n");
    }

    private final class Adapter implements ContainerResponseWriter {
        private final ContainerResponseWriter crw;

        private final boolean disableEntity;

        private long contentLength;

        private ContainerResponse response;

        private ByteArrayOutputStream baos;

        private StringBuilder b = new StringBuilder();

        Adapter(ContainerResponseWriter crw) {
            this.crw = crw;
            this.disableEntity = rc.getFeature(FEATURE_LOGGING_DISABLE_ENTITY);
        }

        public OutputStream writeStatusAndHeaders(long contentLength, ContainerResponse response) throws IOException {
            printResponseLine(b, response);
            printResponseHeaders(b, response.getHttpHeaders());

            if (disableEntity) {
                logger.info(b.toString());
                return crw.writeStatusAndHeaders(contentLength, response);
            } else {
                this.contentLength = contentLength;
                this.response = response;
                return this.baos = new ByteArrayOutputStream();
            }
        }

        public void finish() throws IOException {
            if (!disableEntity) {
                byte[] entity = baos.toByteArray();
                printEntity(b, entity);

                logger.info(b.toString());

                OutputStream out = crw.writeStatusAndHeaders(contentLength, response);
                out.write(entity);
            }
            crw.finish();
        }
    }

    public ContainerResponse filter(ContainerRequest request, ContainerResponse response) {
        setId();
        response.setContainerResponseWriter(
                new Adapter(response.getContainerResponseWriter()));
        return response;
    }

    private void printResponseLine(StringBuilder b, ContainerResponse response) {
        prefixId(b).append(NOTIFICATION_PREFIX).
                append("Server out-bound response").append('\n');
        prefixId(b).append(RESPONSE_PREFIX).append(Integer.toString(response.getStatus())).append('\n');
    }

    private void printResponseHeaders(StringBuilder b, MultivaluedMap<String, Object> headers) {
        for (Map.Entry<String, List<Object>> e : headers.entrySet()) {
            String header = e.getKey();
            for (Object value : e.getValue()) {
                prefixId(b).append(RESPONSE_PREFIX).append(header).append(": ").
                        append(ContainerResponse.getHeaderValue(value)).append('\n');
            }
        }
        prefixId(b).append(RESPONSE_PREFIX).append('\n');
    }
}











