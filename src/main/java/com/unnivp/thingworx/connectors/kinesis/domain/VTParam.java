package com.unnivp.thingworx.connectors.kinesis.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The class to hold individual telemetry parameters in VT (Value-Time) format.
 * 
 * @author unnivp
 * 
 */
public class VTParam implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The timestamp. */
	@JsonProperty("timestamp")
	private Date timestamp;

	/** The parameter. */
	@JsonProperty("parameter")
	private String parameter;

	/** The value. */
	@JsonProperty("value")
	private Object value;

	/**
	 * Gets the timestamp.
	 *
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the timestamp.
	 *
	 * @param timestamp
	 *            the new timestamp
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Gets the parameter.
	 *
	 * @return the parameter
	 */
	public String getParameter() {
		return parameter;
	}

	/**
	 * Sets the parameter.
	 *
	 * @param parameter
	 *            the new parameter
	 */
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value
	 *            the new value
	 */
	public void setValue(Object value) {
		this.value = value;
	}

}
