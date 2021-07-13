package com.unnivp.thingworx.connectors.kinesis.client;

import java.util.UUID;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.thingworx.communications.client.ClientConfigurator;
import com.thingworx.communications.client.ConnectedThingClient;
import com.thingworx.communications.client.things.VirtualThing;
import com.thingworx.relationships.RelationshipTypes.ThingworxEntityTypes;
import com.thingworx.types.InfoTable;
import com.thingworx.types.collections.ValueCollection;
import com.thingworx.types.primitives.JSONPrimitive;
import com.thingworx.types.primitives.StringPrimitive;
import com.unnivp.thingworx.connectors.kinesis.config.ThingworxPropertyConfig;

/**
 * Connector which will send telemetry messages to ThingWorx.
 * 
 * @author unnivp
 *
 */
@Component
public class WSTelemetryClient extends ConnectedThingClient implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(WSTelemetryClient.class);

	@Autowired
	protected ThingworxPropertyConfig propertyConfig;

	protected String connectorName;

	public WSTelemetryClient(ClientConfigurator clientConfigurator) throws Exception {

		super(clientConfigurator);
	}

	public String getConnectorName() {
		return this.connectorName;
	}

	/**
	 * Invoke the ThingWorx telmetry service to process the telemetry payload.
	 * 
	 * @param message
	 *            telemetry message
	 * @return infotable object containing the service execution result
	 */
	@Async("TelemetryMessageExecutor")
	public InfoTable invokeTelemetryService(String message) {

		InfoTable result = null;
		if (this.isConnected()) {
			try {
				// A ValueCollection is used to specify a service's parameters.
				ValueCollection parameters = new ValueCollection();
				JSONObject telemetryJSON = new JSONObject(message);
				parameters.put("payload", new JSONPrimitive(telemetryJSON));

				// Invoke the ThingWorx service to post the telemetry payload over websocket
				// channel.
				result = this.invokeService(ThingworxEntityTypes.Things, propertyConfig.getTelemetryThing(),
						propertyConfig.getTelemetryService(), parameters, propertyConfig.getTimeout());

				logger.info("Completed service call : " + propertyConfig.getTelemetryThing() + "."
						+ propertyConfig.getTelemetryService() + " > " + result.getReturnValue());

			} catch (Exception eProcessing) {
				logger.error("Error invoking Telemetry service : ", eProcessing);
			}
		} else {
			logger.info("Client is not connected while invoking service : " + propertyConfig.getTelemetryThing() + "."
					+ propertyConfig.getTelemetryService());
		}
		return result;
	}
	
	/**
	 * Invoke the ThingWorx health service to perform health check.
	 */
	public void healthCheck() {

		if (this.isConnected()) {
			try {
				ValueCollection parameters = new ValueCollection();
				parameters.put("connectorName", new StringPrimitive(this.connectorName));
				logger.debug("Performing Health Check : " + propertyConfig.getHealthThing() + "."
						+ propertyConfig.getHealthService());
				this.invokeService(ThingworxEntityTypes.Things, propertyConfig.getHealthThing(),
						propertyConfig.getHealthService(), parameters, propertyConfig.getTimeout());
				logger.info("Health check completed succesfully for " + this.connectorName);
			} catch (Exception eProcessing) {
				logger.error("Error invoking Health Check : ", eProcessing);
			}
		} else {
			logger.info("Client is not connected while invoking service : " + propertyConfig.getHealthThing() + "."
					+ propertyConfig.getHealthService());
		}
	}

	/**
	 * Long-running thread for the WebSocket connector. Performs period health
	 * checks to avoid termination due to inactivity.
	 */
	@Override
	public void run() {

		try {
			// Bind the Web socket connector to a ThingWorx Remote Thing.
			connectorName = "WSConnector-" + UUID.randomUUID().toString().replace("-", "");
			this.bindThing(new TelemetryThing(connectorName, this));
			this.start();
			logger.info("WS Connector started succesfully : " + connectorName + ":" + this.isConnected());

		} catch (Exception eStart) {
			logger.error("WS Connector Initial Start Failed : ", eStart);
		}

		while (!this.isShutdown()) {

			try {
				Thread.sleep(propertyConfig.getHealthFrequency());
			} catch (InterruptedException e) {
				logger.error("Unexpected exception", e);
			}

			// Perform health check at regular intervals.
			if (this.isConnected() && propertyConfig.getHealthService() != null) {
				this.healthCheck();
			}
		}
	}

	/**
	 * The Remote Thing for the WebSocket connector. This connected thing will
	 * appear under the Remote Things > Unbound section in the Monitoring > Status
	 * pane of the ThingWorx Composer.
	 * 
	 * @author unnivp
	 *
	 */
	public class TelemetryThing extends VirtualThing {

		private static final long serialVersionUID = -2393731561648993129L;

		public TelemetryThing(String name, ConnectedThingClient client) throws Exception {

			super(name, "WS Connector Thing", null, client);
			this.initialize();
		}

	}

}
