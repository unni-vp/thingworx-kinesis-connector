package com.unnivp.thingworx.connectors.kinesis.client;

import static com.unnivp.thingworx.connectors.kinesis.constants.KinesisConnectorConstants.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.unnivp.thingworx.connectors.kinesis.config.ThingworxPropertyConfig;

/**
 * Factory class to return specific implementation of WSTelemetryConnector
 * 
 * @author unnivp
 *
 */
@Component
public class WSTelemetryConnectorFactory {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private WSClientConfigurator wsConnectorConfig;

	@Autowired
	private ThingworxPropertyConfig propertyConfig;
	
	private static final Logger logger = LoggerFactory.getLogger(WSTelemetryConnectorFactory.class);

	public WSTelemetryConnector getWSTelemetryConnector() {

		// Create web socket client configuration.
		wsConnectorConfig.initClientConfigurator(propertyConfig.getHost(), propertyConfig.getPort(),
				propertyConfig.getAppKey(), propertyConfig.getHealthFrequency(), propertyConfig.isSecureConnection());

		return applicationContext.getBean(WSTelemetryConnector.class, wsConnectorConfig);
	}
	
	@PostConstruct
	private void init() throws InterruptedException, ExecutionException {

		ExecutorService executor = Executors.newFixedThreadPool(propertyConfig.getConnectorCount());
		try {
			// Create consumers to process the telemetry message.
			for (int i = 0; i < propertyConfig.getConnectorCount(); i++) {
				executor.execute(this.getWSTelemetryConnector());
				logger.info("Created WS Telemtry Kinesis Connector - Consumer " + (i + 1));
			}
		} catch (Exception wsConnectorException) {
			logger.error("Exception on initializing the wsTelemtryConnector : ", wsConnectorException);
		}

		// giving time for WS Telemetry client to bind to Thingworx and start
		Thread.sleep(THINGWORX_BINDING_WAIT_TIME);
	}

}
