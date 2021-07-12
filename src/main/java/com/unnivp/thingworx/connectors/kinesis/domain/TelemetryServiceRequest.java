package com.unnivp.thingworx.connectors.kinesis.domain;

import java.io.Serializable;

/**
 * The wrapper class for the telemetry request.
 * 
 * @author unnivp
 * 
 */
public class TelemetryServiceRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The telemetry payload. */
	private TelemetryPayload payload;

	/**
	 * @return the payload
	 */
	public TelemetryPayload getPayload() {
		return payload;
	}

	/**
	 * @param payload
	 *            the payload to set
	 */
	public void setPayload(TelemetryPayload payload) {
		this.payload = payload;
	}

}
