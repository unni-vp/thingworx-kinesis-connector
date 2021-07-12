package com.unnivp.thingworx.connectors.kinesis.domain;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The class for the sample telemetry payload.
 * 
 * @author unnivp
 * 
 */
public class TelemetryPayload implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The device id. */
	@JsonProperty("deviceId")
	private String deviceId;

	/** The device type. */
	@JsonProperty("deviceType")
	private String deviceType;

	/** The telemetry parameters. */
	@JsonProperty("parameters")
	private List<VTParam> telemetryParameters;

	/**
	 * Gets the device id.
	 *
	 * @return the device id
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * Sets the device id.
	 *
	 * @param deviceId
	 *            the new device id
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * Gets the device type.
	 *
	 * @return the device type
	 */
	public String getDeviceType() {
		return deviceType;
	}

	/**
	 * Sets the device type.
	 *
	 * @param deviceType
	 *            the new device type
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * @return the telemetryParameters
	 */
	public List<VTParam> getTelemetryParameters() {
		return telemetryParameters;
	}

	/**
	 * @param telemetryParameters
	 *            the telemetryParameters to set
	 */
	public void setTelemetryParameters(List<VTParam> telemetryParameters) {
		this.telemetryParameters = telemetryParameters;
	}

}
