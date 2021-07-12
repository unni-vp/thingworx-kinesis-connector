package com.unnivp.thingworx.connectors.kinesis.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import static com.unnivp.thingworx.connectors.kinesis.constants.KinesisConnectorConstants.*;

/**
 * Class to listen messages on the configured Kinesis Data Stream.
 * 
 * @author unnivp
 *
 */
@Component
@EnableBinding(Sink.class)
public class KinesisMessageSink {

	private static final Logger logger = LoggerFactory.getLogger(KinesisMessageSink.class);

	@Autowired
	private JmsTemplate jmsTemplate;

	/**
	 * Consume the message received on the Kinesis data stream and send it to an
	 * internal JMS queue for further processing using ThingWorx websocket
	 * connectors.
	 * 
	 * @param message
	 *            the message received on the Kinesis stream
	 */
	@StreamListener(Sink.INPUT)
	public void consume(String message) {

		// FIXME: change implementation to use functional programming constructs.
		try {

			logger.info("KinesisMessageSink sending record to JMS queue: " + message);
			jmsTemplate.convertAndSend(TELEMETRY_QUEUE, message);

		} catch (Exception e) {
			logger.error("KinesisMessageSink - Error processing telemetry data from Kinesis : ", e);
		}
	}
}
