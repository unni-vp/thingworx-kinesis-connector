package com.unnivp.thingworx.connectors.kinesis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * ThingWorx property configuration, loaded from property file. Used for
 * connecting to ThingWorx platform via websocket channel.
 * 
 * @author unnivp
 *
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "thingworx")
public class ThingworxPropertyConfig {

	// Host name of thingworx 
	private String host;

	private int port;

	private String appKey;

	private int timeout;

	private String telemetryThing;

	private String telemetryService;

	private int telemetryFrequency;

	private String healthThing;

	private String healthService;

	private int healthFrequency;

	private int connectorCount;
	
	private boolean secureConnection;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getTelemetryThing() {
		return telemetryThing;
	}

	public void setTelemetryThing(String telemetryThing) {
		this.telemetryThing = telemetryThing;
	}

	public String getTelemetryService() {
		return telemetryService;
	}

	public void setTelemetryService(String telemetryService) {
		this.telemetryService = telemetryService;
	}

	public int getTelemetryFrequency() {
		return telemetryFrequency;
	}

	public void setTelemetryFrequency(int telemetryFrequency) {
		this.telemetryFrequency = telemetryFrequency;
	}

	public String getHealthThing() {
		return healthThing;
	}

	public void setHealthThing(String healthThing) {
		this.healthThing = healthThing;
	}

	public String getHealthService() {
		return healthService;
	}

	public void setHealthService(String healthService) {
		this.healthService = healthService;
	}

	public int getHealthFrequency() {
		return healthFrequency;
	}

	public void setHealthFrequency(int healthFrequency) {
		this.healthFrequency = healthFrequency;
	}

	public int getConnectorCount() {
		return connectorCount;
	}

	public void setConnectorCount(int connectorCount) {
		this.connectorCount = connectorCount;
	}

	public boolean isSecureConnection() {
		return secureConnection;
	}

	public void setSecureConnection(boolean secureConnection) {
		this.secureConnection = secureConnection;
	}

	@Override
	public String toString() {
		return this.host + ":" + this.port;
	}
}