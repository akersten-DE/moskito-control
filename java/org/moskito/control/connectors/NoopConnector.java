package org.moskito.control.connectors;

import org.moskito.control.connectors.response.ConnectorAccumulatorResponse;
import org.moskito.control.connectors.response.ConnectorStatusResponse;
import org.moskito.control.connectors.response.ConnectorThresholdsResponse;
import org.moskito.control.core.HealthColor;
import org.moskito.control.core.status.Status;

import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 28.05.13 21:53
 */
public class NoopConnector implements Connector {

    @Override
	public void configure(String location) {
		//DO NOTHING.
	}

	@Override
	public ConnectorStatusResponse getNewStatus() {
		return new ConnectorStatusResponse(new Status(HealthColor.GREEN, "NoCheckByNoop"));
	}

    @Override
    public ConnectorThresholdsResponse getThresholds() {
        return new ConnectorThresholdsResponse();
    }

    @Override
	public ConnectorAccumulatorResponse getAccumulators(List<String> accumulatorNames) {
		return new ConnectorAccumulatorResponse();
	}
}
