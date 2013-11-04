package org.moskito.control.connectors;

import java.io.IOException;
import java.util.List;

import org.moskito.control.connectors.httputils.HttpHelper;
import org.moskito.control.connectors.response.ConnectorAccumulatorResponse;
import org.moskito.control.connectors.response.ConnectorStatusResponse;
import org.moskito.control.connectors.response.ConnectorThresholdsResponse;
import org.moskito.control.core.HealthColor;
import org.moskito.control.core.status.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A basic implementation used as plain health-check monitor to check the status of one page/server that does not have a moskito agent
 * 
 * @author akersten
 * @since 04.11.13
 */
public class HttpIsAliveConnector implements Connector {

    /**
     * Target applications url.
     */
    private String location;

    /**
     * Logger.
     */
    private static Logger log = LoggerFactory.getLogger(HttpIsAliveConnector.class);

    @Override
    public void configure(final String location) {
        this.location = location;
    }

    @Override
    public ConnectorStatusResponse getNewStatus() {
        try {
            String targetUrl = location;
            if (!targetUrl.startsWith("http")) {
                targetUrl = "http://" + targetUrl;
            }

            log.debug("URL to Call " + targetUrl);
            final long start = System.currentTimeMillis();
            final int statusCode = HttpHelper.getStatusCode(targetUrl);
            final long end = System.currentTimeMillis();
            if (statusCode == 200) {
                final long delta = end - start;
                if (delta > 2000) {
                    return new ConnectorStatusResponse(new Status(HealthColor.ORANGE, "OK but response took was " + delta + " ms."));
                } else {
                    return new ConnectorStatusResponse(new Status(HealthColor.GREEN, "OK"));
                }
            } else {
                return new ConnectorStatusResponse(new Status(HealthColor.GREEN, "NOT OK -- Status: " + statusCode));
            }

        } catch (final IOException e) {
            return new ConnectorStatusResponse(new Status(HealthColor.PURPLE, "Connection Error: " + e.getMessage()));
        }
    }

    @Override
    public ConnectorThresholdsResponse getThresholds() {
        return new ConnectorThresholdsResponse();
    }

    @Override
    public ConnectorAccumulatorResponse getAccumulators(final List<String> accumulatorNames) {
        return new ConnectorAccumulatorResponse();
    }

}
