package com.unnivp.thingworx.connectors.kinesis.client;

import static com.unnivp.thingworx.connectors.kinesis.constants.KinesisConnectorConstants.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.thingworx.communications.client.ClientConfigurator;

/**
 * Connector which will send telemetry messages to ThingWorx.
 * 
 * @author unnivp
 *
 */
@Component
@Scope("prototype")
public class WSTelemetryConnector extends WSTelemetryClient {

	private static final Logger logger = LoggerFactory.getLogger(WSTelemetryConnector.class);

	public WSTelemetryConnector(ClientConfigurator clientConfigurator) throws Exception {

		super(clientConfigurator);
	}

	@JmsListener(destination = TELEMETRY_QUEUE)
	public void recieveMessage(String message) {

		try {
			logger.info(connectorName + " received : " + message);
			invokeTelemetryService(message);
		} catch (Exception e) {
			logger.error("Unexpected exception from message listener : " + connectorName + ": ", e);
		}
	}

}
