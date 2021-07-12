package com.unnivp.thingworx.connectors.kinesis.constants;

public class KinesisConnectorConstants {

	public static final String TELEMETRY_QUEUE = "TelemetryKinesisQueue";

	// Time in milliseconds to wait to let all ThingWorx connectors complete their
	// binding with the ThingWorx platform.
	public static final int THINGWORX_BINDING_WAIT_TIME = 5000;
	
	public static final String THINGWORX_URL = "/Thingworx/WS";
	
	public static final String PLAIN_WEBSOCKET_PREFIX = "ws://";
	
	public static final String SECURE_WEBSOCKET_PREFIX = "wss://";
	
	public static final String DELIMITER_COLON = ":";

}
