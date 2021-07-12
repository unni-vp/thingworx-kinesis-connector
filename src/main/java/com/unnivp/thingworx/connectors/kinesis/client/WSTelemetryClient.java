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
	
	public void recieveMessage(String requestString) {
		// Override this method to specify the implementation
	}

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

	@Async("TelemetryMessageExecutor")
	public InfoTable invokeTelemetryService(String message) {

		InfoTable result = null;
		if (this.isConnected()) {
			try {
				logger.info("Calling service : " + propertyConfig.getTelemetryThing() + "."
						+ propertyConfig.getTelemetryService() + " > " + message);
				// A ValueCollection is used to specify a service's parameters
				ValueCollection parameters = new ValueCollection();
				JSONObject telemetryJSON = new JSONObject(message);
				parameters.put("records", new JSONPrimitive(telemetryJSON));
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

	@Override
	public void run() {

		try {
			connectorName = "WSConnector-" 
					//+ ((this.getEndpoint().getName() != null) ? (this.getEndpoint().getName() + "-") : "");
					+ UUID.randomUUID().toString().replace("-", "");
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

			if (this.isConnected() && propertyConfig.getHealthService() != null) {
				this.healthCheck();
			}
		}
	}
	
	public class TelemetryThing extends VirtualThing {

		private static final long serialVersionUID = -2393731561648993129L;
		
		public TelemetryThing(String name, ConnectedThingClient client) throws Exception {

			super(name, "WS Connector Thing", null, client);
			this.initialize();
		}
		
	}

}
